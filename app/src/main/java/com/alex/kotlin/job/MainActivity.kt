package com.alex.kotlin.job

import android.app.Dialog
import android.app.NotificationManager
import android.content.*
import android.database.ContentObserver
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.annotation.RequiresApi
import android.support.v4.app.JobIntentService
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.alex.kotlin.job.Jobservice.MyJobIntentService
import com.alex.kotlin.job.service.ForegroundService
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
startActivity()
        }
    }

    val contentObservable = object : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            val cursor = contentResolver.query(uri, arrayOf("_id", "name"), null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Log.e("========", cursor.getInt(cursor.getColumnIndex("_id")).toString())
                    Log.e("========", cursor.getString(cursor.getColumnIndex("name")).toString())
                } while (cursor.moveToNext())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, BuildConfig.model, Toast.LENGTH_SHORT).show()

        val uri = Uri.parse("content://com.alex.kotlin.job.provider/job")
        contentResolver.registerContentObserver(uri, true, contentObservable)

//        val reciver = JobBroadcast()
//        var intentFilter  = IntentFilter("Action")
//        LocalBroadcastManager.getInstance(this)
//                .registerReceiver(reciver,intentFilter)

        button.setOnClickListener {

            //           val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("http://www.alex.com/link/login.html")
//            startActivity(intent)
//
//            val contentValue = ContentValues()
//            contentValue.put("name","AAAA")
//            contentResolver.insert(uri,contentValue)




            val layoutParams = WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE shl WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL shl  WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED

            windowManager.addView(imageView,layoutParams)

//            val intent = Intent("com.alex.kotlin.job.JobScheduleActivity")
//            intent.data = Uri.parse("content://com.alex.kotlin.job.provider/path")
//            startActivity(intent)

//            intent.putExtra("intent",10)
//            JobIntentService.enqueueWork(this,MyJobIntentService::class.java,100,intent)

        }

        imageView.setOnClickListener {
            val uri = Uri.parse("content://com.alex.kotlin.job.provider/job")

            val cursor = contentResolver.query(uri, arrayOf("_id", "name"), null, null, null)

            if (cursor != null && cursor.moveToFirst()) {

                do {
                    Log.e("========", cursor.getInt(cursor.getColumnIndex("_id")).toString())
                    Log.e("========", cursor.getString(cursor.getColumnIndex("name")).toString())
                } while (cursor.moveToNext())
            }

        }

    }

    class JobBroadcast : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val l = intent!!.getIntExtra("Extra", 1)
            Toast.makeText(context, " $l", Toast.LENGTH_SHORT).show()
        }
    }

}
