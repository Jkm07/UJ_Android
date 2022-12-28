package com.example.zadaniebazydanych.auth

import com.google.gson.GsonBuilder
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.zadaniebazydanych.ConfigFileHandler
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


object GitHubAdapter {
    private val clientId = ConfigFileHandler.getConfigValue("clientId")
    private val clientSecret = ConfigFileHandler.getConfigValue("clientSecret")
    private val redirectUri = "zadanieAndroid://callback"
    public var tokenContainer: AccessToken = AccessToken("", "");

    private val baseUrl: String = "https://github.com/";
    private val baseUrlApi: String = "https://api.github.com/";
    private val gson = GsonBuilder().setLenient().create()
    private val instance = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(
        gson
    )).build().create(Api::class.java)
    private val instanceApi = Retrofit.Builder().baseUrl(baseUrlApi).addConverterFactory(GsonConverterFactory.create(
        gson
    )).build().create(Api::class.java)

    fun getLogIntent() : Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize?client_id=$clientId&scope=user&redirect_uri=$redirectUri"))
    }

    suspend fun getToken(code: String) {
        tokenContainer = instance.getAccessToken(clientId, clientSecret, code);
    }

    suspend fun getUserInfo() : User {
        return instanceApi.getUserName("Bearer ${tokenContainer.accessToken}")
    }

    fun getRedirectUri() : String {
        return redirectUri;
    }

    private interface Api {
        @Headers("Accept: application/json")
        @FormUrlEncoded
        @POST("/login/oauth/access_token")
        suspend fun getAccessToken(@Field("client_id") c1: String = clientId, @Field("client_secret") c2: String = clientSecret, @Field("code") code : String) : AccessToken

        @Headers("Accept: application/json")
        @GET("/user")
        suspend fun getUserName(@Header("Authorization") token: String) : User;
    }

    data class AccessToken(@SerializedName("access_token") val accessToken: String,@SerializedName("token_type") val tokenType: String)
    data class User(val id: Int, val login: String, val email: String)
}