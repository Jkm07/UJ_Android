package com.example.zadaniebazydanych.fragment.basket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zadaniebazydanych.Database
import com.example.zadaniebazydanych.R
import com.example.zadaniebazydanych.fragment.products.ProductListAdapter
import com.example.zadaniebazydanych.loginpage.SignUpPage

class BasketFragment : Fragment() {

    lateinit var recyclerView: RecyclerView;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerViewBasket)
        val basket = Database.getBasket();

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = BasketAdapter(basket)
        }
    }

    fun Notify() {
        if(this::recyclerView.isInitialized) {
            val basket = Database.getBasket()
            recyclerView.apply {
                adapter = BasketAdapter(basket)
            }
        }
    }
}