package com.example.zadaniebazydanych.fragment.products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zadaniebazydanych.Database
import com.example.zadaniebazydanych.R

class ProductsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerView)
        Log.d("state", "otwarcie database")
        Database
        Log.d("state", "zakon database")
        Log.d("state", "wcztywanie produktow")
        val products = Database.getProducts();
        Log.d("state", "wczytanie")

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ProductListAdapter(products)
        }
    }
}