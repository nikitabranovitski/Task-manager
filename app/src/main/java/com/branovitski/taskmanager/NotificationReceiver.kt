package com.branovitski.taskmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.karn.notify.Notify

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Notify
            .with(context)
            .content {
                title = "New note?"
                text = "Time to create new note!"
            }
            .show()
    }
}