package com.example.models

import org.jetbrains.exposed.sql.*

data class User(val username: String, val email: String, val password: String, val myToken: String, val googleToken: String, val gitToken: String)
data class UserShort(val username: String, val email: String, val myToken: String)

object Users : Table() {
    val username = varchar("username", 128)
    val email = varchar("email", 128)
    val password = varchar("password", 128)
    val myToken = varchar("myToken", 128)
    val googleToken = varchar("googleToken", 128)
    val gitToken = varchar("gitToken", 128)

    override val primaryKey = PrimaryKey(email)
}