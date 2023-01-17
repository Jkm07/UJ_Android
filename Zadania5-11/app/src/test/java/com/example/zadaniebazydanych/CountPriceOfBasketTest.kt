package com.example.zadaniebazydanych

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
class CountPriceOfBasketTest {

    companion object {
        val makeOder = mockk<MakeOrder>()
    }

    @Before
    fun setUp()
    {
        every { makeOder.getPrice(any()) } answers {callOriginal()}
    }

    @Test
    fun basicTest() {
        val p1 = Product()
        p1.name = "P1"
        p1.price = 10
        val b1 = BasketItem()
        b1.count = 1;
        b1.Product = p1
        val b2 = BasketItem()
        b2.count = 1;
        b2.Product = p1
        val b3 = BasketItem()
        b3.count = 1;
        b3.Product = p1
        val b4 = BasketItem()
        b4.count = 1;
        b4.Product = p1

        val arrayOfItems = arrayOf(b1, b2, b3, b4)
        assertEquals("Message", 40, Companion.makeOder.getPrice(arrayOfItems))
    }

    @Test
    fun diffNumberOfItems() {
        val p1 = Product()
        p1.name = "P1"
        p1.price = 10
        val b1 = BasketItem()
        b1.count = 2;
        b1.Product = p1
        val b2 = BasketItem()
        b2.count = 4;
        b2.Product = p1
        val b3 = BasketItem()
        b3.count = 2;
        b3.Product = p1
        val b4 = BasketItem()
        b4.count = 2;
        b4.Product = p1

        val arrayOfItems = arrayOf(b1, b2, b3, b4)
        assertEquals("Message", 100, Companion.makeOder.getPrice(arrayOfItems))
    }

    @Test
    fun diffProductTest() {
        val p1 = Product()
        p1.name = "P1"
        p1.price = 10
        val p2 = Product()
        p2.name = "P2"
        p2.price = 100
        val p3 = Product()
        p3.name = "P3"
        p3.price = 1
        val p4 = Product()
        p4.name = "P3"
        p4.price = 1000
        val b1 = BasketItem()
        b1.count = 3;
        b1.Product = p1
        val b2 = BasketItem()
        b2.count = 1;
        b2.Product = p2
        val b3 = BasketItem()
        b3.count = 7;
        b3.Product = p3
        val b4 = BasketItem()
        b4.count = 2;
        b4.Product = p4

        val arrayOfItems = arrayOf(b1, b2, b3, b4)
        assertEquals("Message", 2137, Companion.makeOder.getPrice(arrayOfItems))
    }

    @Test
    fun emptyArrayTest() {

        val arrayOfItems = arrayOf<BasketItem>()
        assertEquals("Message", 0, Companion.makeOder.getPrice(arrayOfItems))
    }
}