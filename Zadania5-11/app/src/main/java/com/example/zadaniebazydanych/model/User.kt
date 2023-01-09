package com.example.zadaniebazydanych.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class User : RealmObject {
    @PrimaryKey
    var email: String = ""
    var password: String = ""
    var username: String = ""
    var token: String = ""
}