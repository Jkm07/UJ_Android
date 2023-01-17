package com.example.zadaniebazydanych.createproduct

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.zadaniebazydanych.Database
import com.example.zadaniebazydanych.NetworkAdapter
import com.example.zadaniebazydanych.R
import com.example.zadaniebazydanych.databinding.ActivityCreateProductBinding
import kotlinx.coroutines.runBlocking

class CreateProduct : AppCompatActivity() {

    var categoryId: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryId = intent.getStringExtra("id")?.toInt() ?: 0

        val activitDataBinding  = DataBindingUtil.setContentView<ActivityCreateProductBinding>(this,
            R.layout.activity_create_product
        )
        activitDataBinding.category = Database.getCategory(categoryId);
    }

    @SuppressLint("NotConstructor")
    fun CreateProduct(view: View) {
        val name = findViewById<EditText>(R.id.nameInput).text.toString()
        val price = findViewById<EditText>(R.id.priceInput).text.toString().toInt()
        val desc = findViewById<EditText>(R.id.descInput).text.toString()

        if(price <= 0 && name.isEmpty()) {
            return
        }

        runBlocking {
            NetworkAdapter.insertProduct(name, price,categoryId, desc)
        }
        Database.downloadDataFromServer()
        finish()
    }

}