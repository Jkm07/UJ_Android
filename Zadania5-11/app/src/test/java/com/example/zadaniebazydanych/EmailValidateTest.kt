package com.example.zadaniebazydanych

import com.example.zadaniebazydanych.auth.UserInfo
import com.example.zadaniebazydanych.loginpage.SignUpPage
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
class EmailValidateTest {

    companion object {
        val signUp = mockk<SignUpPage>()
    }
    @Before
    fun setUp()
    {
        every { Companion.signUp.emailValidate(any()) } answers {callOriginal()}
    }

    @Test
    fun correctEmailTest() {
        assertEquals(true, Companion.signUp.emailValidate("email@emal"))
    }

    @Test
    fun emptyEmailTest() {
        assertEquals(false, Companion.signUp.emailValidate(""))
    }

    @Test
    fun whiteSpaceEmailTest() {
        assertEquals(false, Companion.signUp.emailValidate("email @asdad"))
    }

    @Test
    fun noATEmailTest() {
        assertEquals(false, Companion.signUp.emailValidate("emailasdad"))
    }
}