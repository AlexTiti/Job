package com.alex.kotlin.job.Jobservice

import android.content.Intent
import android.support.v4.app.JobIntentService
import android.support.v4.content.LocalBroadcastManager

class MyJobIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        val number = intent.getIntExtra("intent",5)
        val intentHandle = Intent("Action")
        intentHandle.putExtra("Extra",number * number)
        LocalBroadcastManager.getInstance(baseContext).sendBroadcast(intentHandle)
    }
}