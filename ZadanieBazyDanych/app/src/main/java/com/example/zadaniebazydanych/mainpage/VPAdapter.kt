package com.example.zadaniebazydanych.mainpage

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zadaniebazydanych.fragment.basket.BasketFragment
import com.example.zadaniebazydanych.fragment.products.ProductsFragment
import io.realm.kotlin.internal.intToLong

class VPAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val _tabsName = arrayOf("Products", "Basket")

    fun getTabName(position: Int): String {
        return _tabsName[position];
    }

    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("STATE", "przlacz")
        when(position) {
            0 -> return ProductsFragment();
            1 -> return BasketFragment();
            else -> return BasketFragment();
        }
    }

}