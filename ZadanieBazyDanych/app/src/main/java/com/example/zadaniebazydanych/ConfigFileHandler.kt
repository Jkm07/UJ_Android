package com.example.zadaniebazydanych

import android.content.Context
import android.content.res.Resources
import android.util.Log
import java.io.IOException
import java.util.*


object ConfigFileHandler {
    private val outhProp = Properties();
    private val basicProp = Properties();
    fun getAuthConfigValue(name: String?): String {
        try {
            return outhProp.getProperty(name)
        } catch (e: Resources.NotFoundException) {
            Log.e("error", "Unable to find the config file: " + e.message)
        } catch (e: IOException) {
            Log.e("error", "Failed to open config file.")
        }
        return ""
    }
    fun getBasicConfigVale(name: String?): String {
        try {
            return basicProp.getProperty(name)
        } catch (e: Resources.NotFoundException) {
            Log.e("error", "Unable to find the config file: " + e.message)
        } catch (e: IOException) {
            Log.e("error", "Failed to open config file.")
        }
        return ""
    }

    fun createProp(context: Context) {
        try {
            val assets = context.assets
            val inputStream = assets.open("outh.properties")
            outhProp.load(inputStream)
            val basicInputStream = assets.open("basic.properties")
            basicProp.load(basicInputStream)
        } catch (e: Resources.NotFoundException) {
            Log.e("error", "Unable to find the config file: " + e.message)
        } catch (e: IOException) {
            Log.e("error", "Failed to open config file.")
        }
    }
}