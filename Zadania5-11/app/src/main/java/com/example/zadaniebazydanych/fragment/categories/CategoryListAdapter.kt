package com.example.zadaniebazydanych.fragment.categories

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zadaniebazydanych.databinding.CategoryItemBinding
import com.example.zadaniebazydanych.model.Category
import com.example.zadaniebazydanych.productpage.ProductPage

class CategoryListAdapter(private val list: Array<Category>) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(var binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category: Category = list[position]
        holder.binding.category = category
    }

    override fun getItemCount(): Int = list.size
}