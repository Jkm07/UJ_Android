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
}