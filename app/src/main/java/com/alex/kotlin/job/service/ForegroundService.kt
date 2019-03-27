package com.alex.kotlin.job.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.alex.kotlin.job.R

class ForegroundService : Service() {

    private var notificationManagerCompat : NotificationManager? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        Log.e("=======","onCreate")
        notificationManagerCompat = baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("=======","onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun notification(){
        val group = "service"
        val notificationChannelGroup = NotificationChannelGroup(group,"Service")
        notificationManagerCompat?.createNotificationChannelGroup(notificationChannelGroup)

        val channelId = "channel"
        val notificationChannel = NotificationChannel(channelId,"Channel",NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.description = "我是前台服务"
        notificationChannel.group = group
        notificationManagerCompat?.createNotificationChannel(notificationChannel)

        val notification = Notification.Builder(this,channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
                .setContentTitle("Android 8.0")
                .setContentText("前台服务")
                .setAutoCancel(true)
                .build()
        startForeground(100,notification)

    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

}