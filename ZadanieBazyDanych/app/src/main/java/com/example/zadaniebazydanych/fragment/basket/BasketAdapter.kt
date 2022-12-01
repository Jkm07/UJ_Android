package com.example.zadaniebazydanych.fragment.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.zadaniebazydanych.Database
import com.example.zadaniebazydanych.R
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
        val delButton = holder.binding.root.findViewById<Button>(R.id.delete_button)
        delButton.setOnClickListener(){
            Database.deleteItemFromBasket(basketItem);
        }
    }

    override fun getItemCount(): Int = list.size
}