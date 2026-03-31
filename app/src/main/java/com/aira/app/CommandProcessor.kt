package com.aira.app

import android.content.Context
import android.os.Vibrator
import android.provider.Settings
import com.google.firebase.database.FirebaseDatabase

class CommandProcessor(private val context: Context) {
    fun fetchAndExecute() {
        val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        val ref = FirebaseDatabase.getInstance().getReference("children/$deviceId/commands")
        ref.orderByChild("status").equalTo("pending").get().addOnSuccessListener { snapshot ->
            snapshot.children.forEach { cmdSnap ->
                val type = cmdSnap.child("type").getValue(String::class.java)
                val params = cmdSnap.child("params").value as? Map<*, *>
                executeCommand(type, params)
                cmdSnap.ref.child("status").setValue("done")
            }
        }
    }

    private fun executeCommand(type: String?, params: Map<*, *>?) {
        when (type) {
            "vibrate" -> {
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(2000)
            }
            "flash_on" -> {
                // Stub - implement CameraHelper
            }
            "location" -> {
                // Stub - implement LocationHelper
            }
            // Add more commands as needed
        }
    }
}