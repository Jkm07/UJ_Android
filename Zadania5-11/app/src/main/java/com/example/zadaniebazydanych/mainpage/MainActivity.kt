package com.example.zadaniebazydanych.mainpage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.zadaniebazydanych.Database
import com.example.zadaniebazydanych.R
import com.example.zadaniebazydanych.auth.UserInfo
import com.example.zadaniebazydanych.createproduct.CreateProduct
import com.example.zadaniebazydanych.fragment.basket.BasketFragment
import com.example.zadaniebazydanych.fragment.categories.CategoriesFragment
import com.example.zadaniebazydanych.fragment.orders.OrdersFragment
import com.example.zadaniebazydanych.fragment.products.ProductsFragment
import com.example.zadaniebazydanych.loginpage.SignUpPage
import com.example.zadaniebazydanych.makeorderpage.MakeOrder
import com.example.zadaniebazydanych.notifacation.Notification
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var TabLayout: TabLayout;
    private lateinit var ViewPager: ViewPager2;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(UserInfo.getUserInfo())
        TabLayout = findViewById<TabLayout>(R.id.tablayout);
        ViewPager = findViewById<ViewPager2>(R.id.viewpager);

        val productsFragment = ProductsFragment();
        val basketFragment = BasketFragment();
        val categoriesFragment = CategoriesFragment();
        val ordersFragment = OrdersFragment();
        Database.basket = basketFragment;
        Database.productList = productsFragment
        Database.categoryList = categoriesFragment
        Database.ordersList = ordersFragment
        Database.downloadDataFromServer()
        val vpAdapter = VPAdapter(this, productsFragment, basketFragment, categoriesFragment, ordersFragment);

        ViewPager.adapter = vpAdapter;
        TabLayoutMediator(TabLayout, ViewPager) { tab, position -> tab.text = vpAdapter.getTabName(position)}.attach()
        Notification.notifyUserDefault(this, "Nowe produkty", "Nowe produkty w naszym sklepie")
        Notification.notifyUserImportant(this, "Promocja", "Promocja na jakiś tam produkt do końca tygodnia")
    }

    fun OpenCreateProductForm(view: View) {
        val intent = Intent(this, CreateProduct::class.java)
        val categoryId = view.getTag().toString()
        intent.putExtra("id", categoryId)
        ContextCompat.startActivity(this, intent, null);
    }

    fun createOrder(view: View) {
        val intent = Intent(this, MakeOrder::class.java)
        ContextCompat.startActivity(this, intent, null);
    }
}