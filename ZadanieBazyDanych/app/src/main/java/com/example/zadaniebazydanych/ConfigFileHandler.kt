package com.example.zadaniebazydanych

import android.content.Context
import android.content.res.Resources
import android.util.Log
import java.io.IOException
import java.util.*


object ConfigFileHandler {
    private val prop = Properties();
    fun getConfigValue(name: String?): String {
        try {
            return prop.getProperty(name)
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
            prop.load(inputStream)
        } catch (e: Resources.NotFoundException) {
            Log.e("error", "Unable to find the config file: " + e.message)
        } catch (e: IOException) {
            Log.e("error", "Failed to open config file.")
        }
    }
}