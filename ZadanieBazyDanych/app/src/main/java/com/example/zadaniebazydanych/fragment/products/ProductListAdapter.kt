package com.example.zadaniebazydanych.fragment.products

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.zadaniebazydanych.Database
import com.example.zadaniebazydanych.R
import com.example.zadaniebazydanych.model.Product
import com.example.zadaniebazydanych.databinding.ProductItemBinding
import com.example.zadaniebazydanych.productpage.ProductPage

class ProductListAdapter(private val list: Array<Product>) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    class ProductViewHolder(var binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemBinding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val linearLayout = itemBinding.root.findViewById<LinearLayout>(R.id.containerProductItem)
        linearLayout.setOnClickListener(){
            val intent = Intent(parent.context, ProductPage::class.java)
            intent.putExtra("id", itemBinding?.product?._id?.toString())
            startActivity(parent.context, intent, null);
        }
        return ProductViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product: Product = list[position]
        holder.binding.product = product
        val addButton = holder.binding.root.findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener(){
            Database.insertItemToBasket(product);
        }
    }

    override fun getItemCount(): Int = list.size
}