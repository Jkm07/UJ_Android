package com.example.models

import org.jetbrains.exposed.sql.*

data class Category(val id: Int, val name: String, val code: String, val desc: String)

object Categories : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val code = varchar("category", 128)
    val desc = varchar("country", 128)

    override val primaryKey = PrimaryKey(id)
}