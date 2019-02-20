package com.justcode.hxl.Android和JS交互

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.widget.RelativeLayout
import android.widget.TextView
import com.justcode.hxl.sugar.toast
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    var handler: Handler? = null
    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        handler = Handler()
        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true
        webview.webChromeClient = object : WebChromeClient() {

        }
        webview.addJavascriptInterface(AndroidObject(tv_show), "android")
//        webview.addJavascriptInterface(AndroidObject(rl,handler!!), "native")

        webview.loadUrl("file:///android_asset/js2android.html")
//        webview.loadUrl("http://192.168.0.178:8080/#/floorNavigation")
        sure.setOnClickListener {
            var input = et_input.text.toString()
            if (TextUtils.isEmpty(input)) {
                "请输入内容".toast()
                return@setOnClickListener
            }
            // Android版本变量
            val version = Build.VERSION.SDK_INT
            // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
            if (version < 18) {
                webview.loadUrl("javascript:android2js('$input')")
            } else {
                webview.evaluateJavascript("javascript:android2js('" + input + "')"
                ) {
                    input.toast()
                }
            }

        }
    }

    class AndroidObject(var tv: TextView) {
        @JavascriptInterface
        fun showjs2android(input: String) {
            tv.text = input
        }

        @JavascriptInterface
        fun showAppHeader(str: String? = null) {
            "show".toast()
            Log.d("AndroidObject", "show")

        }

        @JavascriptInterface
        fun hideAppHeader(str: String? = null) {
            "hide".toast()
            Log.d("AndroidObject", "hide")
        }
    }
//    class AndroidObject(var rl: RelativeLayout, var handler: Handler) {
//
//        @JavascriptInterface
//        fun showAppHeader(str: String? = null) {
//            "show".toast()
//            Log.d("AndroidObject", "show")
//            handler.post{
//                rl.visibility = View.VISIBLE
//            }
//
//
//        }
//
//        @JavascriptInterface
//        fun hideAppHeader(str: String? = null) {
//            "hide".toast()
//            Log.d("AndroidObject", "hide")
//            handler.post{
//                rl.visibility = View.GONE
//            }
//
//        }
//    }
}
