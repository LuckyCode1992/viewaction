package com.justcode.hxl.Android和JS交互

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.widget.TextView
import android.widget.Toast
import com.justcode.hxl.sugar.toast
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true
        webview.webChromeClient = object : WebChromeClient() {

        }
        webview.addJavascriptInterface(AndroidObject(tv_show), "android")

        webview.loadUrl("file:///android_asset/js2android.html")
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
    }
}
