package com.justcode.hxl.viewaction.remoteview

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_notifyactivity.*

class NotifyActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifyactivity)
        tv.setOnClickListener {
            initNotify()
        }
        tv1.setOnClickListener {
            initRemoteView()
        }

    }
    var builder: Notification.Builder? = null
    var notificationManager: NotificationManager? = null
    private fun initRemoteView() {


    }



    @TargetApi(Build.VERSION_CODES.O)
    private fun initNotify() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        builder = Notification.Builder(this)
        val intent = Intent()
        intent.setClass(this, PendingActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        builder!!.setSmallIcon(R.drawable.kefu_2_pdf)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.kefu_2_pdf))
                .setContentIntent(pendingIntent)
                .setContentTitle("我是标题")
                .setTicker("我是内容")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var mChannel = NotificationChannel("my_channel_id", "my_channel_name", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager?.createNotificationChannel(mChannel)
            builder!!.setChannelId("my_channel_id")
        }


        var notification = builder!!.build()
        notification.flags = Notification.FLAG_AUTO_CANCEL


        notificationManager?.notify(10, notification)
    }
}
