package com.example.zadaniebazydanych.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class BasketItem : RealmObject {
    @PrimaryKey
    var _id: RealmUUID = RealmUUID.random()
    var Product: Product? = null
    var isActive: Boolean = true
    var count: Int = 1;
}