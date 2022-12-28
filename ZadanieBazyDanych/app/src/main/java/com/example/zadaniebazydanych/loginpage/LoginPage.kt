package com.example.zadaniebazydanych.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.zadaniebazydanych.R
import com.example.zadaniebazydanych.mainpage.MainActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.zadaniebazydanych.ConfigFileHandler
import com.example.zadaniebazydanych.NetworkAdapter
import com.example.zadaniebazydanych.auth.GitHubAdapter
import com.example.zadaniebazydanych.auth.GoogleAdapter
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.runBlocking


class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ConfigFileHandler.createProp(applicationContext)
        setContentView(R.layout.activity_login)
    }

    fun BasicLog(view: View) {
        val email = findViewById<EditText>(R.id.email).text.toString()
        val pass = findViewById<EditText>(R.id.password).text.toString()
        var result = false;
        runBlocking {
            result = NetworkAdapter.authUser(email, pass)
        }
        if(result) {
            goToApp();
        }
        else {
            Toast.makeText(this, "Incorrect data", Toast.LENGTH_SHORT).show()
        }
    }

    fun GitHubLog(view: View) {
        val intent = GitHubAdapter.getLogIntent()
        ContextCompat.startActivity(this, intent, null);
    }

    fun GoogleLog(view: View) {
        val intent = GoogleAdapter.getIntent(this)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100) {
            try {
                val account = GoogleAdapter.getAccount(data!!)
                runBlocking {
                    NetworkAdapter.authGoogleUser(account.email ?: "", account.displayName ?: "", account.idToken ?: "")
                }
                goToApp()
            }
            catch(e: ApiException) {
                Toast.makeText(this, "Error Google Sign in", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val uri = getIntent().getData();
        if(uri != null) {
            val code = uri.getQueryParameter("code")
            runBlocking {
                GitHubAdapter.getToken(code !!);
                val user = GitHubAdapter.getUserInfo()
                val email = user.email ?: user.login
                NetworkAdapter.authGitUser(email, user.login, GitHubAdapter.tokenContainer.accessToken)
            }
            goToApp()
        }
    }

    fun goToSignUp(view: View) {
        val intent = Intent(this, SignUpPage::class.java)
        ContextCompat.startActivity(this, intent, null);
    }

    private fun goToApp() {
        val intent = Intent(this, MainActivity::class.java)
        ContextCompat.startActivity(this, intent, null);
        finish()
    }
}