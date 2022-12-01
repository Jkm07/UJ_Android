package com.example.zadaniebazydanych.fragment.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zadaniebazydanych.databinding.BasketItemBinding
import com.example.zadaniebazydanych.model.BasketItem

class BasketAdapter(private val list: Array<BasketItem>) : RecyclerView.Adapter<BasketAdapter.BasketItemViewHolder>() {

    class BasketItemViewHolder(var binding: BasketItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemViewHolder {
        val itemBinding = BasketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BasketAdapter.BasketItemViewHolder, position: Int) {
        val basketItem: BasketItem = list[position]
        holder.binding.basket = basketItem
    }

    override fun getItemCount(): Int = list.size
}