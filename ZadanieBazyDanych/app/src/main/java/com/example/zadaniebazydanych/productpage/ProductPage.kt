package com.example.zadaniebazydanych.productpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.zadaniebazydanych.Database
import com.example.zadaniebazydanych.R
import com.example.zadaniebazydanych.databinding.ActivityProductPageBinding
import io.realm.kotlin.types.RealmUUID

class ProductPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productId = intent.getStringExtra("id")

        val activitDataBinding  = DataBindingUtil.setContentView<ActivityProductPageBinding>(this,
            R.layout.activity_product_page
        )
        activitDataBinding.product = Database.getProduct(RealmUUID.from(productId!!));
    }
}