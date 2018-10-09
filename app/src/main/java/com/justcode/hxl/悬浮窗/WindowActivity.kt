package com.justcode.hxl.悬浮窗

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.viewaction.R
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.content.Intent
import android.net.Uri
import android.provider.Settings.canDrawOverlays
import android.os.Build
import android.provider.Settings
import android.view.*
import android.widget.Toast
import android.view.WindowManager
import com.justcode.hxl.floatwindow.service.FloatWindowService
import kotlinx.android.synthetic.main.activity_window.*


class WindowActivity : AppCompatActivity() {

    var mWindowManager: WindowManager? = null
    var floatWindowView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)

        shouquan.setOnClickListener {
            //启动Activity让用户授权
            val intent = Intent()
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:$packageName"))
            startActivityForResult(intent, 100)
        }
        start_float_window.setOnClickListener {
            startService2Window()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (Settings.canDrawOverlays(applicationContext)) {
//                    startService2Window()
//                } else {
//                    //启动Activity让用户授权
//                    val intent = Intent()
//                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    intent.setData(Uri.parse("package:$packageName"))
//                    startActivityForResult(intent, 100)
//
//                }
//            } else {
//                startService2Window()
//            }
        }


    }

    fun startService2Window() {
        val intent = Intent(this, FloatWindowService::class.java)
        startService(intent)
        FloatWindowService.activity = this
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(applicationContext)) {
                    startService2Window()
                } else {
                    Toast.makeText(this, "ACTION_MANAGE_OVERLAY_PERMISSION权限已被拒绝", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
