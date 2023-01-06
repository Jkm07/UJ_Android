package com.example.zadaniebazydanych.fragment.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zadaniebazydanych.Database
import com.example.zadaniebazydanych.R

class OrdersFragment : Fragment() {

    lateinit var recyclerView: RecyclerView;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerViewOrders)
        val orders = Database.getOrders();

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = OrdersAdapter(orders)
        }
    }

    fun Notify() {
        if(this::recyclerView.isInitialized) {
            val order = Database.getOrders()
            recyclerView.apply {
                adapter = OrdersAdapter(order)
            }
        }
    }
}