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

        start_float_window.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(applicationContext)) {
                    startService2Window()
                } else {
                    //启动Activity让用户授权
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    intent.setData(Uri.parse("package:$packageName"))
                    startActivityForResult(intent, 100)
                }
            } else {
                startService2Window()
            }
        }


    }

    fun startService2Window() {
        val intent = Intent(this, FloatWindowService::class.java)
        startService(intent)
        FloatWindowService.activity = this
    }

    @SuppressLint("ClickableViewAccessibility")
    fun showWindow() {
        //Activity中的方法,得到窗口管理器
//        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        //设置悬浮窗布局属性
//        val mWindowLayoutParams = WindowManager.LayoutParams()
//        //设置类型,具体有哪些值可取在后面附上
//        if (Build.VERSION.SDK_INT >= 26) {//8.0新特性
//            mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//        } else {
//            mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
//        }
//
//        //设置行为选项,具体有哪些值可取在后面附上
//        mWindowLayoutParams.flags = (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_BLUR_BEHIND
//                or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
//        //设置悬浮窗的显示位置
//        mWindowLayoutParams.gravity = Gravity.LEFT or Gravity.TOP
//        //        //设置悬浮窗的横竖屏,会影响屏幕方向,只要悬浮窗不消失,屏幕方向就会一直保持,可以强制屏幕横屏或竖屏
//        //        mWindowLayoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
//        //设置 x 轴的偏移量
//        mWindowLayoutParams.x = 0
//        //设置 y 轴的偏移量
//        mWindowLayoutParams.y = 0
//        //如果悬浮窗图片为透明图片,需要设置该参数为 PixelFormat.RGBA_8888
//        mWindowLayoutParams.format = PixelFormat.RGBA_8888
//        //设置悬浮窗的宽度
//        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
//        //设置悬浮窗的高度
//        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
//        //设置悬浮窗的布局
//        floatWindowView = LayoutInflater.from(this).inflate(R.layout.float_window, null)
//        //加载显示悬浮窗
//        mWindowManager?.addView(floatWindowView, mWindowLayoutParams)
//        floatWindowView?.setOnTouchListener(object : View.OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//              return false
//            }
//
//        })
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
