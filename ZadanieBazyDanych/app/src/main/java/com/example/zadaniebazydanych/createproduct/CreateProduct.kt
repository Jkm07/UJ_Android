package com.example.zadaniebazydanych.createproduct

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.zadaniebazydanych.Database
import com.example.zadaniebazydanych.NetworkAdapter
import com.example.zadaniebazydanych.R
import com.example.zadaniebazydanych.databinding.ActivityCreateProductBinding
import com.example.zadaniebazydanych.databinding.ActivityProductPageBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

    fun CreateProduct(view: View) {
        val name = findViewById<EditText>(R.id.nameInput).text.toString()
        val price = findViewById<EditText>(R.id.priceInput).text.toString().toInt()
        val desc = findViewById<EditText>(R.id.descInput).text.toString()

        GlobalScope.launch {
            NetworkAdapter.insertProduct(name, price,categoryId, desc)
        }
    }

}