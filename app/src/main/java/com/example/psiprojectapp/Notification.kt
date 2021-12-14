package com.example.psiprojectapp

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

//const val notificationID = 1
//const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"
const val groupKey = "com.example.psiprojectapp"

class Notification : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {
        val channelID : String
        val notificationID : Int

        if (intent.extras != null) {
            channelID = intent.getStringExtra("channelID").toString()
            notificationID = intent.getIntExtra("notificationID", 1)
        }
        else {
            channelID = "channel1"
            notificationID = 1
        }

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setGroup(groupKey)
            .setGroupSummary(true)
            .build()

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }

}