package com.example.zadaniebazydanych.notifacation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BADGE_ICON_LARGE
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.zadaniebazydanych.R
import com.example.zadaniebazydanych.productpage.ProductPage

object Notification {
    private var initDef = false
    private var initImp = false
    private var id = 1
    private var badgeNumber = 1
    private lateinit var pendingIntent: PendingIntent
    private lateinit var pendingIntentBroadcast: PendingIntent
    private lateinit var intent: Intent
    private lateinit var intentBroadcast: Intent
    private var itWasShowedNewProduct = false
    private var itWasShowedPromation = false
    private val KEY_TEXT_REPLY = "key_text_reply"
    private var convId = -1



    fun createNotificationChannelDefault(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notify"
            val descriptionText = "notify"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("default", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            initDef = true
        }
    }

    fun createNotificationChannelImportant(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notify"
            val descriptionText = "notify"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("important", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            initImp = true
        }
    }

    fun notifyUserDefault(context: Context, title: String, content: String) {
        if(itWasShowedNewProduct) {
            return
        }
        if(!initDef) {
            createNotificationChannelDefault(context)
        }
        if(!this::pendingIntent.isInitialized) {
            pendingIntent = createIntent(context)
        }
        val builder = NotificationCompat.Builder(context, "default")
            .setSmallIcon(R.drawable.shoping_cart_icon)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setNumber(badgeNumber)
            .setBadgeIconType(BADGE_ICON_LARGE)
            .setContentIntent(pendingIntent)
        with(NotificationManagerCompat.from(context)) {
            notify(id, builder.build())
        }
        ++id
        itWasShowedNewProduct = true
    }

    fun notifyUserImportant(context: Context, title: String, content: String) {
        if(itWasShowedPromation) {
            return
        }
        if(!initImp) {
            createNotificationChannelImportant(context)
        }
        if(!this::pendingIntentBroadcast.isInitialized) {
            pendingIntentBroadcast = createIntentBroadcast(context)
        }

        var remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel("Hehe ziemnior")
            build()
        }

        var action: NotificationCompat.Action =
            NotificationCompat.Action.Builder(R.drawable.shoping_cart_icon,
                "Odpowiedź", pendingIntentBroadcast)
                .addRemoteInput(remoteInput)
                .build()

        val builder = NotificationCompat.Builder(context, "important")
            .setSmallIcon(R.drawable.shoping_cart_icon)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setNumber(badgeNumber)
            .setBadgeIconType(BADGE_ICON_LARGE)
            .addAction(action)
        with(NotificationManagerCompat.from(context)) {
            notify(id, builder.build())
        }
        convId = id
        ++id
        itWasShowedPromation = true
    }

    fun replyUser(context: Context, message: String) {
        val builder = NotificationCompat.Builder(context, "important")
            .setSmallIcon(R.drawable.shoping_cart_icon)
            .setContentTitle("Reply from serwer")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setNumber(badgeNumber)
        with(NotificationManagerCompat.from(context)) {
            notify(convId, builder.build())
        }
        itWasShowedPromation = true
    }

    private fun createIntent(context: Context): PendingIntent {
        if(!this::intent.isInitialized) {
            intent = createIntentValue(context)
        }
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun createIntentBroadcast(context: Context): PendingIntent {
        if(!this::intentBroadcast.isInitialized) {
            intentBroadcast = createIntentBroadcastValue(context)
        }
        return PendingIntent.getBroadcast(context, 0, intentBroadcast, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun createIntentValue(context: Context): Intent {
        val intent =  Intent(context, ProductPage::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra("id", "1")
        return intent
    }

    private fun createIntentBroadcastValue(context: Context): Intent {
        val intent =  Intent(context, MyBroadcastReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra("id", "1")
        return intent
    }

    fun getMessageText(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT_REPLY)
    }

}