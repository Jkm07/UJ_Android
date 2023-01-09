package com.example.zadaniebazydanych.payments

import android.content.Context
import android.text.TextUtils
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zadaniebazydanych.auth.UserInfo
import com.example.zadaniebazydanych.makeorderpage.MakeOrder
import com.payu.base.models.ErrorResponse
import com.payu.base.models.PayUPaymentParams
import com.payu.checkoutpro.PayUCheckoutPro
import com.payu.checkoutpro.utils.PayUCheckoutProConstants
import com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_NAME
import com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_STRING
import com.payu.ui.model.listeners.PayUCheckoutProListener
import com.payu.ui.model.listeners.PayUHashGenerationListener
import java.security.MessageDigest
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.HashMap

object PayUHandler {
        fun init (actvity: AppCompatActivity, order: MakeOrder.OrderSend, sendFunc: () -> Unit) {
            val payUPaymentParams = PayUPaymentParams.Builder()
                .setAmount(order.price.toDouble().toString())
                .setIsProduction(false)
                .setKey("gtKFFx")
                .setProductInfo(order.realName + " " + order.email + " " + order.date)
                .setTransactionId(System.currentTimeMillis().toString())
                .setFirstName(UserInfo.Username)
                .setEmail(UserInfo.Email)
                .setPhone("7903376251")
                .setSurl("https://payuresponse.firebaseapp.com/success")
                .setFurl("https://payuresponse.firebaseapp.com/failure")
                .build()

            PayUCheckoutPro.open(
                actvity, payUPaymentParams,
                object : PayUCheckoutProListener {

                    override fun onPaymentSuccess(response: Any) {
                        response as HashMap<*, *>
                        val payUResponse = response[PayUCheckoutProConstants.CP_PAYU_RESPONSE]
                        val merchantResponse = response[PayUCheckoutProConstants.CP_MERCHANT_RESPONSE]
                        Toast.makeText(actvity, "onPaymentSuccess", Toast.LENGTH_LONG).show()
                        sendFunc()
                        Log.d("info", "success")
                    }

                    override fun onPaymentFailure(response: Any) {
                        response as HashMap<*, *>
                        val payUResponse = response[PayUCheckoutProConstants.CP_PAYU_RESPONSE]
                        val merchantResponse = response[PayUCheckoutProConstants.CP_MERCHANT_RESPONSE]
                        Toast.makeText(actvity, "onPaymentFailure", Toast.LENGTH_LONG).show()

                    }

                    override fun onPaymentCancel(isTxnInitiated:Boolean) {
                        Toast.makeText(actvity, "onPaymentCancel", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(errorResponse: ErrorResponse) {
                        val errorMessage: String
                        if (errorResponse != null && errorResponse.errorMessage != null && errorResponse.errorMessage!!.isNotEmpty())
                            errorMessage = errorResponse.errorMessage!!
                        else
                            errorMessage ="blad"
                        Log.d("info", errorMessage)
                    }

                    override fun setWebViewProperties(webView: WebView?, bank: Any?) {}

                    override fun generateHash(
                        valueMap: HashMap<String, String?>,
                        hashGenerationListener: PayUHashGenerationListener
                    ) {

                        if ( valueMap.containsKey(CP_HASH_STRING)
                            && valueMap.containsKey(CP_HASH_STRING) != null
                            && valueMap.containsKey(CP_HASH_NAME)
                            && valueMap.containsKey(CP_HASH_NAME) != null) {


                            val hashData = valueMap[CP_HASH_STRING]
                            val hashName = valueMap[CP_HASH_NAME]
                            val hash: String? = calculateHash(hashData.toString() + "4R38IvwiV57FwVpsgOvTXBdLE4tHUXFW")
                            if (!TextUtils.isEmpty(hash)) {
                                val dataMap: HashMap<String, String?> = HashMap()
                                dataMap[hashName!!] = hash!!
                                hashGenerationListener.onHashGenerated(dataMap)
                            }
                        }
                    }
                })
        }

    private fun calculateHash(hashString: String): String {
        val messageDigest =
            MessageDigest.getInstance("SHA-512")
        messageDigest.update(hashString.toByteArray())
        val mdbytes = messageDigest.digest()
        return getHexString(mdbytes)
    }
    private fun getHexString(data: ByteArray): String {
        val hexString: StringBuilder = StringBuilder()
        for (aMessageDigest: Byte in data) {
            var h: String = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2)
                h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    }
}