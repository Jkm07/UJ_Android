package com.example.models

import org.jetbrains.exposed.sql.*

data class Product(val id: Int, val name: String, val price: Int, val category: Int, val desc: String)
data class ProductWithCategory(val id: Int, val name: String, val price: Int, val category: Category, val desc: String)

object Products : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val price = integer("price")
    val category = integer("category").references(Categories.id, onDelete = ReferenceOption.CASCADE)
    val desc = varchar("desc", 128)

    override val primaryKey = PrimaryKey(id)
}