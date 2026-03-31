package com.aira.app

import android.content.Context
import android.provider.Settings
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.FirebaseDatabase

class PingWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val deviceId = Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
        val ref = FirebaseDatabase.getInstance().getReference("children/$deviceId")
        ref.child("lastPing").setValue(System.currentTimeMillis())
        CommandProcessor(applicationContext).fetchAndExecute()
        return Result.success()
    }
}