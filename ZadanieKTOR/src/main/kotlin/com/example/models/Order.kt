package com.example.models

import com.example.models.Products.references
import org.jetbrains.exposed.sql.*

data class Order(
    val email: String,
    val token: String,
    val realName: String,
    val address: String,
    val basket: Array<BasketItemShort>)

data class OrderSend(
    val email: String,
    val realName: String,
    val address: String,
    val date: String,
    val count: Int,
    val priceForOne: Int,
    val product: Int,
    val isSucceed: Boolean)

data class BasketItemShort(val id: Int, val count: Int)

object Orders : Table() {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 128)
    val realName = varchar("realName", 128)
    val address = varchar("address", 128)
    val date = varchar("date", 128)
    val count = integer("count")
    val priceForOne = integer("priceForOne")
    val product = integer("price").references(Products.id, onDelete = ReferenceOption.CASCADE)
    val isSucceed = bool("isSucceed")

    override val primaryKey = PrimaryKey(id)
}