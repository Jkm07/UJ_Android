package com.example.zadaniebazydanych.auth

import com.example.zadaniebazydanych.NetworkAdapter

object UserInfo {
    var Username = "";
    var Email = "";
    var Token = ""
    var Type = ""

    fun assign(snd: NetworkAdapter.User) {
        Username = snd.user.username
        Email = snd.user.email
        Token = snd.user.myToken
    }

    fun getUserInfo() : String {
        if(Username.isEmpty()) {
            return "Nie zalogowano"
        }
        if(Token.isEmpty()) {
            return "Brak połączena"
        }
        if(Type.isEmpty() || Type == "basic") {
            return "Użytkownik: $Username"
        }
        return "Użytkownik: $Username - $Type";
    }
}