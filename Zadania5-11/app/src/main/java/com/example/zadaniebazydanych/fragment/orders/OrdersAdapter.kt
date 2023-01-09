package com.example.zadaniebazydanych.fragment.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zadaniebazydanych.databinding.OrderItemBinding
import com.example.zadaniebazydanych.model.Order

class OrdersAdapter(private val list: Array<Order>) : RecyclerView.Adapter<OrdersAdapter.OrderItemViewHolder>() {

    class OrderItemViewHolder(var binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val itemBinding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: OrdersAdapter.OrderItemViewHolder, position: Int) {
        val orderItem: Order = list[position]
        holder.binding.order = orderItem
    }

    override fun getItemCount(): Int = list.size
}