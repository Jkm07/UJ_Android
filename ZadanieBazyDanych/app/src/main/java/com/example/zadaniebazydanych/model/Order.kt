package com.example.zadaniebazydanych.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class Order : RealmObject {
    var email: String = ""
    var realName: String = ""
    var address: String = ""
    var date: String = ""
    var count: Int = 0
    var priceForOne: Int = 0
    var product: Product? = null
    var isSucceed: Boolean = false
}