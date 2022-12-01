package com.example.zadaniebazydanych.mainpage

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.zadaniebazydanych.Database
import com.example.zadaniebazydanych.R
import com.example.zadaniebazydanych.fragment.basket.BasketFragment
import com.example.zadaniebazydanych.fragment.products.ProductsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : FragmentActivity() {

    private lateinit var TabLayout: TabLayout;
    private lateinit var ViewPager: ViewPager2;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TabLayout = findViewById<TabLayout>(R.id.tablayout);
        ViewPager = findViewById<ViewPager2>(R.id.viewpager);

        val productsFragment = ProductsFragment();
        val basketFragment = BasketFragment();
        Database.basket = basketFragment;
        val vpAdapter = VPAdapter(this, productsFragment, basketFragment);

        ViewPager.adapter = vpAdapter;
        TabLayoutMediator(TabLayout, ViewPager) { tab, position -> tab.text = vpAdapter.getTabName(position)}.attach()
    }


}