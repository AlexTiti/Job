package com.alex.kotlin.job

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.LocalBroadcastManager
import com.alex.kotlin.job.Jobservice.MyJobService
import kotlinx.android.synthetic.main.activity_job_schedule.*

class JobScheduleActivity : AppCompatActivity() {

    private val jobReceiver = JobSchedulerReceiver()
    private var jobId = 0
    private val jobSchedule by lazy { getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_schedule)

        val intentFilter = IntentFilter("Action")
        LocalBroadcastManager.getInstance(this).registerReceiver(jobReceiver, intentFilter)
        startService(Intent(this, MyJobService::class.java))  //先启动Service

        btn_job.setOnClickListener {
            var number: String = editText.text.trim().toString()

            val jobBuilder = JobInfo.Builder(jobId, ComponentName(this, MyJobService::class.java))
//                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                    .setMinimumLatency(5000)  // 任务延迟开始
//
//                    .setOverrideDeadline(5000)  //设置截止时间
            //.setPeriodic(3000)  //设置循环执行间隔时间，不能和setOverrideDeadline（）和 setMinimumLatency（）同时设置
            val extra = PersistableBundle()
            extra.putLong("Duration", if (number == null) 1000 else number.toLong() * 1000)
            jobBuilder.setExtras(extra)
            jobSchedule.schedule(jobBuilder.build())
            jobId++

        }

        btn_cancel.setOnClickListener {
            jobSchedule.cancel(jobId)
        }

        btn_cancel_all.setOnClickListener {
            jobSchedule.cancelAll()
        }

    }

    inner class JobSchedulerReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            if (intent?.action?.equals("Action") == true) {
                val data = intent?.getStringExtra("Value")
                val jobId = intent?.getIntExtra("JobID", 0)
                when (data) {
                    "Start" -> {
                        btn_start.setBackgroundColor(Color.GREEN)
                        btn_stop.setBackgroundColor(Color.LTGRAY)
                        jobId?.toString()?.let {
                            tv_detail.text = "Job $it Start"
                        }
                    }
                    "Stop" -> {
                        btn_stop.setBackgroundColor(Color.RED)
                        btn_start.setBackgroundColor(Color.LTGRAY)
                        jobId?.toString()?.let {
                            tv_detail.text = "Job $it Stop"
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(jobReceiver)
        super.onDestroy()
    }

}
