package com.goddoro.module.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import com.goddoro.module.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer

/**
 * Created by goddoro on 2021-05-17.
 */

class PlayerService : Service() {

    lateinit var player: ExoPlayer

    override fun onCreate() {
        super.onCreate()

        player = SimpleExoPlayer.Builder(this).build()
    }


    fun showToast( text : String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return PlayerBinder()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    inner class PlayerBinder : Binder() {

        val service : PlayerService
            get() = this@PlayerService


    }
    companion object {

        const val CHANNEL_ID = "CHANNEL_ID"
    }


}