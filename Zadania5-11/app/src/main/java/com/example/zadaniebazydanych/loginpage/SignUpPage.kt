package com.example.zadaniebazydanych.loginpage
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zadaniebazydanych.R
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.zadaniebazydanych.NetworkAdapter
import kotlinx.coroutines.runBlocking


class SignUpPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    fun goToSignIn(view: View) {
        finish()
    }

    fun createAccount(view: View) {
        val username = findViewById<EditText>(R.id.login).text.toString()
        val email = findViewById<EditText>(R.id.email).text.toString()
        val pass = findViewById<EditText>(R.id.password).text.toString()
        var message = ""
        runBlocking {
            message = NetworkAdapter.insertUser(username, email, pass)
        }
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}