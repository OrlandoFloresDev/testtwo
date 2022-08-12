package com.test.italika.extensions

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.test.italika.R
import com.test.italika.base.BaseActivity


fun Activity.showSnackBar(restId: Int, color: Int = Color.BLACK) {
    val snackBar = Snackbar.make(window.decorView.rootView, restId, Snackbar.LENGTH_LONG)
    snackBar.view.setBackgroundColor(color)
    snackBar.show()
}

fun Fragment.showSnackBar(restId: Int, color: Int = Color.BLACK) {
    activity?.let {
        val snackBar = Snackbar.make(it.window.decorView.rootView, restId, Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(color)
        snackBar.show()
    }
}

fun Activity.sendNotificationConpact(title: String, description: String) {
    val manager = getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
    var builder: NotificationCompat.Builder
    val context = applicationContext

    val CHANNEL_ID = "italika_channel"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID, "ItalikaChannel",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "Italika channel description"
        manager.createNotificationChannel(channel)
        builder = NotificationCompat.Builder(this, CHANNEL_ID)
    } else {
        builder = NotificationCompat.Builder(context)
    }

    val action = PendingIntent.getActivity(
        context,
        0, Intent(context, BaseActivity::class.java),
        PendingIntent.FLAG_CANCEL_CURRENT
    )

    builder.setContentIntent(action)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setTicker("Small text!")
        .setAutoCancel(true) //
        .setContentTitle(title)
        .setContentText(description);

    val notification = builder.build()

    val notificationCode = (Math.random() * 1000).toInt()
    manager.notify(notificationCode, notification)
}

fun Activity.showSnackBar(message: String, color: Int = Color.BLACK) {
    val snackBar = Snackbar.make(window.decorView.rootView, message, Snackbar.LENGTH_LONG)
    snackBar.view.setBackgroundColor(color)
    snackBar.show()
}

fun Fragment.showSnackBar(message: String, color: Int = Color.BLACK) {
    activity?.let {
        val snackBar = Snackbar.make(it.window.decorView.rootView, message, Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(color)
        snackBar.show()
    }
}