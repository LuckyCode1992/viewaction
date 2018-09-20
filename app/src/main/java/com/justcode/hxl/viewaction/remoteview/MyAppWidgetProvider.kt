package com.justcode.hxl.viewaction.remoteview

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.SystemClock
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import com.justcode.hxl.viewaction.R

class MyAppWidgetProvider : AppWidgetProvider {
    constructor() : super()

    companion object {
        val TAG = "MyAppWidgetProvider"
        val CLICK_ACTION = "com.justcode.hxl.viewaction.remoteview.ACTION"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.d(TAG, "onReceive:action = " + intent?.action)
        if (intent?.action == CLICK_ACTION) {
            Toast.makeText(context, "clicked it ", Toast.LENGTH_SHORT).show()

            Thread(Runnable {
                val bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.kefu_2_pdf)
                val appWidgetManger = AppWidgetManager.getInstance(context)
                for (i in 0..37) {
                    val degree = (i * 10) % 360
                    val remoteViews = RemoteViews(context?.packageName, R.layout.widget)
                    remoteViews.setImageViewBitmap(R.id.iv, ratateBitmap(context, bitmap, degree))
                    val intent = Intent()
                    intent.setAction(CLICK_ACTION)
                    var pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                    remoteViews.setOnClickPendingIntent(R.id.iv, pendingIntent)
                    appWidgetManger.updateAppWidget(ComponentName(context, MyAppWidgetProvider::class.java), remoteViews)
                    SystemClock.sleep(30)
                }
            }).start()
        }
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(TAG, "onUpdate")
        val counter = appWidgetIds?.size
        counter?.let {
            for (i in 0..counter) {
                var appWidgetId = appWidgetIds[i]
                onWidgetUpdate(context,appWidgetManager,appWidgetId)
            }
        }

    }

    private fun onWidgetUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int) {
        Log.d(TAG, "onWidgetUpdate  appWidgetId:"+appWidgetId)
        val remoteViews = RemoteViews(context?.packageName,R.layout.widget)
        val intent = Intent()
        intent.setAction(CLICK_ACTION)
        val pendingIntent = PendingIntent.getBroadcast(context,0,intent,0)
        remoteViews.setOnClickPendingIntent(R.id.iv,pendingIntent)
        appWidgetManager?.updateAppWidget(appWidgetId,remoteViews)

    }

    private fun ratateBitmap(context: Context?, bitmap: Bitmap?, degree: Int): Bitmap? {
        val height = bitmap?.height
        val width = bitmap?.width
        val matrix = Matrix()
        matrix.reset()
        matrix.setRotate(degree.toFloat())
        val newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width!!, height!!, matrix, false)
        if (newBitmap == bitmap) {
            return newBitmap
        }
        bitmap.recycle()
        return newBitmap


    }


}