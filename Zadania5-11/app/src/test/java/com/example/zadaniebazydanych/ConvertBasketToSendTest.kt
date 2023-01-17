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
class ConvertBasketToSendTest {

    companion object {
        val makeOder = mockk<MakeOrder>()
    }

    @Before
    fun setUp()
    {
        every { makeOder.toOrderShort(any()) } answers {callOriginal()}
    }

    @Test
    fun basicTest() {
        val p1 = Product()
        p1.name = "P1"
        p1.price = 1
        p1._id = 1
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
        val arrayExpected = arrayOf (MakeOrder.BasketItemShort(1, 1), MakeOrder.BasketItemShort(1, 1), MakeOrder.BasketItemShort(1, 1), MakeOrder.BasketItemShort(1, 1))
        assertArrayEquals(arrayExpected, Companion.makeOder.toOrderShort(arrayOfItems))
    }

    @Test
    fun diffNumberOfItems() {
        val p1 = Product()
        p1.name = "P1"
        p1.price = 10
        p1._id = 1
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
        val arrayExpected = arrayOf (MakeOrder.BasketItemShort(1, 2), MakeOrder.BasketItemShort(1, 4), MakeOrder.BasketItemShort(1, 2), MakeOrder.BasketItemShort(1, 2))
        assertArrayEquals(arrayExpected, Companion.makeOder.toOrderShort(arrayOfItems))
    }

    @Test
    fun diffProductTest() {
        val p1 = Product()
        p1.name = "P1"
        p1.price = 10
        p1._id = 1
        val p2 = Product()
        p2.name = "P2"
        p2.price = 100
        p2._id = 2
        val p3 = Product()
        p3.name = "P3"
        p3.price = 1
        p3._id = 3
        val p4 = Product()
        p4.name = "P3"
        p4.price = 1000
        p4._id = 4
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
        val arrayExpected = arrayOf (MakeOrder.BasketItemShort(1, 3), MakeOrder.BasketItemShort(2, 1), MakeOrder.BasketItemShort(3, 7), MakeOrder.BasketItemShort(4, 2))
        assertArrayEquals(arrayExpected, Companion.makeOder.toOrderShort(arrayOfItems))
    }

    @Test
    fun emptyArrayTest() {

        val arrayOfItems = arrayOf<BasketItem>()
        val arrayExpected = arrayOf<MakeOrder.BasketItemShort>()
        assertArrayEquals(arrayExpected, Companion.makeOder.toOrderShort(arrayOfItems))
    }

    @Test
    fun noIdProductTest() {
        val p1 = Product()
        p1.name = "P1"
        p1.price = 1
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
        val arrayExpected = arrayOf (MakeOrder.BasketItemShort(-1, 1), MakeOrder.BasketItemShort(-1, 1), MakeOrder.BasketItemShort(-1, 1), MakeOrder.BasketItemShort(-1, 1))
        assertArrayEquals(arrayExpected, Companion.makeOder.toOrderShort(arrayOfItems))
    }
}