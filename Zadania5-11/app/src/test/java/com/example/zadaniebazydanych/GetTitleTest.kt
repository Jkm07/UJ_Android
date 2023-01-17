package com.example.zadaniebazydanych

import com.example.zadaniebazydanych.auth.UserInfo
import com.example.zadaniebazydanych.makeorderpage.MakeOrder
import com.example.zadaniebazydanych.model.BasketItem
import com.example.zadaniebazydanych.model.Product
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GetTitleTest {

    @Before
    fun setUp()
    {
    }

    @Test
    fun basicTest() {
        UserInfo.Username = "Janek"
        UserInfo.Token = "token"
        UserInfo.Type = "google"

        assertEquals("Użytkownik: Janek - google", UserInfo.getUserInfo())
    }

    @Test
    fun noUserNameTest() {
        UserInfo.Username = ""
        UserInfo.Token = "token"
        UserInfo.Type = "google"

        assertEquals("Nie zalogowano", UserInfo.getUserInfo())
    }

    @Test
    fun noTokenTest() {
        UserInfo.Username = "Janek"
        UserInfo.Token = ""
        UserInfo.Type = "google"

        assertEquals("Brak połączena", UserInfo.getUserInfo())
    }

    @Test
    fun noTypeTest() {
        UserInfo.Username = "Janek"
        UserInfo.Token = "token"
        UserInfo.Type = ""

        assertEquals("Użytkownik: Janek", UserInfo.getUserInfo())
    }

    @Test
    fun basicTypeTest() {
        UserInfo.Username = "Janek"
        UserInfo.Token = "token"
        UserInfo.Type = "basic"

        assertEquals("Użytkownik: Janek", UserInfo.getUserInfo())
    }
}