package com.example.zadaniebazydanych.mainpage

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zadaniebazydanych.fragment.basket.BasketFragment
import com.example.zadaniebazydanych.fragment.products.ProductsFragment

class VPAdapter(activity: FragmentActivity, private val _productFragment: ProductsFragment, private val _basketFragment: BasketFragment) : FragmentStateAdapter(activity) {

    private val _tabsName = arrayOf("Products", "Basket")


    fun getTabName(position: Int): String {
        return _tabsName[position];
    }

    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return _productFragment;
            1 -> return _basketFragment;
            else -> return _basketFragment;
        }
    }

}