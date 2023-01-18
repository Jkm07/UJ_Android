package com.example.zadaniebazydanych.notifacation
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.zadaniebazydanych.NetworkAdapter
import kotlinx.coroutines.runBlocking

class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        var userText = Notification.getMessageText(p1 ?: return).toString()
        var serverReply = ""
        runBlocking {
            serverReply = NetworkAdapter.userChat(userText)
            Notification.replyUser(p0 ?: return@runBlocking, serverReply)
        }
    }
}