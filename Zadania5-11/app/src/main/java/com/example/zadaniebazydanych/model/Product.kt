package com.example.zadaniebazydanych.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class Product : RealmObject {
    @PrimaryKey
    var _id: Int = -1
    var name: String = ""
    var price: Int = 0
    var desc: String = ""
    var category: Category? = null
}