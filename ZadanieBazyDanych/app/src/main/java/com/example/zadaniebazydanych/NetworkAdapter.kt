package com.example.zadaniebazydanych

import com.example.zadaniebazydanych.auth.UserInfo
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*


object NetworkAdapter {
    private val baseUrl: String = "https://bfff-185-234-91-175.eu.ngrok.io";
    private val gson = GsonBuilder().setLenient().create()
    private val instance = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
        ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson)).build().create(Api::class.java)

    data class Product(val id: Int, val name: String, val price: Int, val category: Category, val desc: String)
    data class ProductArray(val products: Array<Product>)
    data class Category(val id: Int, val name: String, val code: String, val desc: String)
    data class CategoryArray(val categories: Array<Category>)

    data class User(val user: userD) {
        data class userD(val username: String, val email: String, val myToken: String)
    }

    private interface Api {
        @GET("/products")
        suspend fun getProducts() : Response<ProductArray>

        @GET("/categories")
        suspend fun getCategories() : Response<CategoryArray>

        @FormUrlEncoded
        @POST("/addProduct")
        suspend fun insertProduct(@Field("name") name: String, @Field("price") price: Int, @Field("category") category: Int, @Field("desc") desc: String)

        @FormUrlEncoded
        @POST("/addUser")
        suspend fun insertUser(@Field("username") username: String, @Field("email") email: String, @Field("password") password: String) : Response<String>

        @FormUrlEncoded
        @POST("/authBasicUser")
        suspend fun authUser(@Field("email") email: String, @Field("password") password: String) : Response<User>

        @FormUrlEncoded
        @POST("/authGitUser")
        suspend fun authGitUser(@Field("email") email: String, @Field("username") username: String, @Field("token") token: String) : Response<User>

        @FormUrlEncoded
        @POST("/authGoogleUser")
        suspend fun authGoogleUser(@Field("email") email: String, @Field("username") username: String, @Field("token") token: String) : Response<User>
    }

    suspend fun insertProduct(name: String, price: Int, category: Int, desc: String) {
        instance.insertProduct(name, price, category, desc);
    }

    suspend fun getProducts(): Response<ProductArray>{
        return instance.getProducts()
    }

    suspend fun getCategories(): Response<CategoryArray>{
        return instance.getCategories()
    }

    suspend fun insertUser(username: String, email: String, password: String): String{
        val result = instance.insertUser(username, email, password)
        return result?.body() ?: "User already exists"
    }

    suspend fun authUser(email: String, password: String): Boolean {
        val result = instance.authUser(email, password)
        return if(result.body() == null)
            false;
        else {
            UserInfo.assign(result.body()!!)
            UserInfo.Type = "basic"
            true
        }
    }

    suspend fun authGoogleUser(email: String, username: String,  token: String): Boolean {
        val result = instance.authGoogleUser(email, username, token)
        return if(result.body() == null)
            false;
        else {
            UserInfo.assign(result.body()!!)
            UserInfo.Type = "google"
            true
        }
    }

    suspend fun authGitUser(email: String, username: String,  token: String): Boolean {
        val result = instance.authGitUser(email, username, token)
        return if(result.body() == null)
            false;
        else {
            UserInfo.assign(result.body()!!)
            UserInfo.Type = "git"
            true
        }
    }
}