package com.example.dao

import com.example.models.*

interface DAOFacade {

    suspend fun allProducts(): List<Product>
    suspend fun allProductsWithCategory(): List<ProductWithCategory>
    suspend fun product(id: Int): Product?
    suspend fun addNewProduct(name: String, price: Int, category: Int, desc: String): Product?
    suspend fun editProduct(id: Int, name: String, price: Int, category: Int, desc: String): Boolean
    suspend fun deleteProduct(id: Int): Boolean

    suspend fun allCategories(): List<Category>
    suspend fun category(id: Int): Category?
    suspend fun addNewCategory(name: String, code: String, desc: String): Category?
    suspend fun editCategory(id: Int, name: String, code: String, desc: String): Boolean
    suspend fun deleteCategory(id: Int): Boolean

    suspend fun allUsers(): List<User>
    suspend fun addUser(username: String, email: String, password: String) : Boolean
    suspend fun getUserShort(email: String): UserShort?
    suspend fun getUser(email: String): User?
    suspend fun generateToken(email: String) : Int
    suspend fun saveGoogleToken(email: String, token: String) : Int
    suspend fun saveGitToken(email: String, token: String) : Int

    suspend fun getPriceOfOrder(order: Order) : Int
    suspend fun allOrders(): List<OrderSend>
    suspend fun addNewOrder(email: String, realName: String, address: String, count: Int, price: Int, product: Int, isSucceed: Boolean): OrderSend?
}