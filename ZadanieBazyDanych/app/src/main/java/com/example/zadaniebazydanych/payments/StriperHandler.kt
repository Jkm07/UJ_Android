package com.example.zadaniebazydanych.payments


import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zadaniebazydanych.NetworkAdapter
import com.example.zadaniebazydanych.auth.UserInfo
import com.example.zadaniebazydanych.makeorderpage.MakeOrder
import com.example.zadaniebazydanych.model.BasketItem
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.runBlocking

object StriperHandler {
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String

    fun init(activity: AppCompatActivity, onPaymentSheetResult: (paymentSheetResult: PaymentSheetResult) -> Unit) {
        paymentSheet = PaymentSheet(activity, onPaymentSheetResult)
    }

    fun handleOperation(
        activity: AppCompatActivity,
        basket: Array<MakeOrder.BasketItemShort>,
        realName: String,
        address: String
    ) {
        sendOrder(activity, basket, realName, address)
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "Janek",
                customer = customerConfig
            )
        )
    }

    private fun sendOrder(
        activity: AppCompatActivity,
        basket: Array<MakeOrder.BasketItemShort>,
        realName: String,
        address: String
    ) {
        runBlocking {
            val setUpPay = NetworkAdapter.setUpPayment(
                UserInfo.Email,
                UserInfo.Token,
                realName,
                address,
                basket
            )!!
            if (setUpPay?.isSuccessful ?: false) {
                val responseBody = setUpPay.body() ?: return@runBlocking
                paymentIntentClientSecret = responseBody["paymentIntent"] ?: return@runBlocking
                customerConfig = PaymentSheet.CustomerConfiguration(
                    responseBody["customer"] ?: return@runBlocking,
                    responseBody["ephemeralKey"] ?: return@runBlocking
                )
                val publishableKey = responseBody["publishableKey"] ?: return@runBlocking
                PaymentConfiguration.init(activity, publishableKey)
            } else {
                Toast.makeText(
                    activity,
                    "Operation failed - stripe auth failed",
                    Toast.LENGTH_SHORT
                ).show()
                return@runBlocking
            }
        }
    }
}