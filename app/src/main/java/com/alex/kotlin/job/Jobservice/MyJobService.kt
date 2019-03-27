package com.alex.kotlin.job.Jobservice

import android.app.NotificationManager
import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Handler
import android.support.v4.content.LocalBroadcastManager
import android.util.Log

class MyJobService : JobService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return Service.START_NOT_STICKY
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.e("========","onStopJob")
        params?.jobId?.let {      sendMessage("Action","Stop",it) }
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        params?.jobId?.let {      sendMessage("Action","Start",it) }
        var duration = params?.extras?.getLong("Duration") ?: 1000
        if (duration == 0L){
            duration = 2000L
        }
        Log.e("========","$duration")
        Handler().postDelayed({
            jobFinished(params,false)
            Log.e("========","========")
            params?.jobId?.let {      sendMessage("Action","Stop",it) }
        }, duration)
        return true
    }

    fun sendMessage(action : String,value : String,jobID: Int){
        val intent = Intent(action)
        intent.putExtra("Value",value)
        intent.putExtra("JobID",jobID)
        LocalBroadcastManager.getInstance(baseContext).sendBroadcast(intent)
    }
}