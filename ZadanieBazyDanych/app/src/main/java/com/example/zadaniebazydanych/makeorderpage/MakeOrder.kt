package com.example.zadaniebazydanych.makeorderpage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.zadaniebazydanych.Database
import com.example.zadaniebazydanych.NetworkAdapter
import com.example.zadaniebazydanych.R
import com.example.zadaniebazydanych.auth.UserInfo
import com.example.zadaniebazydanych.databinding.ActivityMakeOrderBinding
import com.example.zadaniebazydanych.model.BasketItem
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class MakeOrder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val basket = Database.getBasket();
        val price = getPrice(basket)
        val basketString = getBasketString(basket)

        val order = Order(UserInfo.Email, UserInfo.Token, UserInfo.Username, "", getDate(), price, basketString);
        val activitDataBinding  = DataBindingUtil.setContentView<ActivityMakeOrderBinding>(this,
            R.layout.activity_make_order
        )
        activitDataBinding.order = order
    }

    fun getPrice(basket: Array<BasketItem>) : Int {
        var sum = 0;
        for (b in basket) {
            sum += b.count * (b.Product?.price ?: 0)
        }
        return sum;
    }

    fun getDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatter.format(date)
    }

    fun getBasketString(basket: Array<BasketItem>) : String {
        var result = ""
        var index = 0;
        for (b in basket) {
            ++index
            result += b.Product?.name ?: ""
            if(b.count > 1) {
                result += " x" + b.count
            }
            if(index != basket.size) {
                result += " | "
            }
        }
        return result
    }

    fun toOrderShort(basket: Array<BasketItem>) : Array<BasketItemShort> {
        return basket.map { BasketItemShort(it.Product?._id ?: -1, it.count) }.toTypedArray()
    }

    fun sendOrder(view: View) {
        val basket = toOrderShort(Database.getBasket());
        val realName = findViewById<EditText>(R.id.realName).text.toString()
        val address = findViewById<EditText>(R.id.address).text.toString()
        runBlocking {
            val result = NetworkAdapter.makeOrder(UserInfo.Email, UserInfo.Token, realName, address, basket)
            if(result) {
                Database.saveOrdersFromRemote()
                Database.deleteEntireBasket()
                Toast.makeText(applicationContext, "Operation done", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(applicationContext, "Operation failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

data class Order(
    val email: String,
    val token: String,
    val realName: String,
    val address: String,
    val date: String,
    val price: Int,
    val basketList: String)

data class BasketItemShort(
    val id: Int,
    val count: Int
)

