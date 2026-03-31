package com.aira.app

import android.provider.Settings
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.database.FirebaseDatabase

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        if (message.data["command"] == "WAKE") {
            CommandProcessor(this).fetchAndExecute()
            // Force ping update
            val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            FirebaseDatabase.getInstance().getReference("children/$deviceId/lastPing")
                .setValue(System.currentTimeMillis())
        }
    }

    override fun onNewToken(token: String) {
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        FirebaseDatabase.getInstance().getReference("children/$deviceId/fcm_token").setValue(token)
    }
}