package com.example.zadaniebazydanych

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*


object NetworkAdapter {
    private val baseUrl: String = "https://86bb-195-150-224-31.eu.ngrok.io";
    private val gson = GsonBuilder().setLenient().create()
    private val instance = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson)).build().create(Api::class.java)

    data class Product(val id: Int, val name: String, val price: Int, val category: Category, val desc: String)
    data class ProductPost(
        @SerializedName("name") val name: String,
        @SerializedName("price") val price: Int,
        @SerializedName("category") val category: Int,
        @SerializedName("desc") val desc: String)
    data class ProductArray(val products: Array<Product>)
    data class Category(val id: Int, val name: String, val code: String, val desc: String)
    data class CategoryArray(val categories: Array<Category>)
    private interface Api {
        @GET("/products")
        suspend fun getProducts() : Response<ProductArray>
        @GET("/categories")
        suspend fun getCategories() : Response<CategoryArray>
        @POST("/addProduct")
        suspend fun insertProduct(@Field("name") name: String, @Field("price") price: Int, @Field("category") category: Int, @Field("desc") desc: String): Response<Product>
    }

    suspend fun insertProduct(name: String, price: Int, category: Int, desc: String) {
        instance.insertProduct("dupa", 23, 1, "asda");
    }

    suspend fun getProducts(): Response<ProductArray>{
        return instance.getProducts()
    }

    suspend fun getCategories(): Response<CategoryArray>{
        return instance.getCategories()
    }
}