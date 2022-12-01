package com.example.zadaniebazydanych.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class User : RealmObject {
    @PrimaryKey
    var _id: RealmUUID = RealmUUID.random()
    var login: String = "user"
    var password: String = "1234"
    var username: String = "user"
}