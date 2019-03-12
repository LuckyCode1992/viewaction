package com.justcode.hxl.viewaction.remoteview

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
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


    }

    var builder: Notification.Builder? = null
    var notificationManager: NotificationManager? = null



    fun initNotify() {
        val CHANNEL_ID = "channel_id_1"
        val CHANNEL_NAME = "channel_name_1"

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val isOpened = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationManager!!.areNotificationsEnabled()
        } else {
            true
        }
        if (!isOpened){
            // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
            val intent =  Intent()
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            val uri = Uri.fromParts("package", application.packageName, null)
            intent.data = uri
            startActivity(intent)
           return
        }


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder = Notification.Builder(this)
        } else {
            /**
             * Oreo不用Priority了，用importance
             * IMPORTANCE_NONE 关闭通知
             * IMPORTANCE_MIN 开启通知，不会弹出，但没有提示音，状态栏中无显示
             * IMPORTANCE_LOW 开启通知，不会弹出，不发出提示音，状态栏中显示
             * IMPORTANCE_DEFAULT 开启通知，不会弹出，发出提示音，状态栏中显示
             * IMPORTANCE_HIGH 开启通知，会弹出，发出提示音，状态栏中显示
             */
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH) //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道， //通知才能正常弹出
            notificationManager!!.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this, CHANNEL_ID)
        }
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
        val notification = builder!!.build()
        notificationManager!!.notify(555, notification)
    }

}
