package com.example.zadaniebazydanych.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class Category : RealmObject {
    @PrimaryKey
    var _id: Int = -1
    var name: String = ""
    var desc: String = ""
    var isActive: Boolean = false
}