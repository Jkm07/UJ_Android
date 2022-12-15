package com.example.zadaniebazydanych.fragment.categories

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
import com.example.zadaniebazydanych.fragment.products.ProductListAdapter

class CategoriesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerViewCategories)
        val categories = Database.getCategories();

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CategoryListAdapter(categories)
        }
    }

    fun Notify() {
        if(this::recyclerView.isInitialized) {
            val categories = Database.getCategories();
            recyclerView.apply {
                adapter = CategoryListAdapter(categories)
            }
        }
    }
}