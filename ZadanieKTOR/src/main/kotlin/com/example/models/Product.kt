package com.example.models

import org.jetbrains.exposed.sql.*

data class Product(val id: Int, val name: String, val category: Int, val country: String)

object Products : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val category = integer("category").references(Categories.id, onDelete = ReferenceOption.CASCADE)
    val country = varchar("country", 128)

    override val primaryKey = PrimaryKey(id)
}