package com.justcode.hxl.updateapp

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.justcode.hxl.permission.RxPermissions
import com.justcode.hxl.updateapp.util.AppUpdateUtils
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_update_app.*
import java.io.File


class UpdateAppActivity : AppCompatActivity() {


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_app)

        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val isOpened = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationManager!!.areNotificationsEnabled()
        } else {
            true
        }
        if (!isOpened) {
            // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
            AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请打开通知权限")
                    .setPositiveButton("去打开") { dialog, which ->
                        dialog.dismiss()
                        val intent = Intent()
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        val uri = Uri.fromParts("package", application.packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
        }

        val rxPermissions = RxPermissions(this)
        rxPermissions.request(WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) {
                        Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "未授权", Toast.LENGTH_SHORT).show()
                    }
                }

        btn_notify.setOnClickListener {

        }
        btn_dialog.setOnClickListener {
            UpdateUtils()
                    .setOnUpdateListerner(object : UpdateUtils.UpdateListener {
                        override fun onStart() {

                        }

                        override fun onProgress(progress: Float, totalSize: Long) {

                        }

                        override fun setMax(totalSize: Long) {

                        }

                        override fun onFinish(file: File?) {

                        }

                        override fun onError(msg: String?) {

                        }

                    })
                    .downLoad(this, "http://47.96.121.195/gongzongapp_20190309_v1.3.0.apk")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        Log.d("UpdateAppActivity", "onActivityResult() called with: requestCode = [$requestCode], resultCode = [$resultCode], data = [$data]")
        when (resultCode) {
            Activity.RESULT_CANCELED -> when (requestCode) {
                // 得到通过UpdateDialogFragment默认dialog方式安装，用户取消安装的回调通知，以便用户自己去判断，比如这个更新如果是强制的，但是用户下载之后取消了，在这里发起相应的操作
                AppUpdateUtils.REQ_CODE_INSTALL_APP -> Toast.makeText(this, "用户取消了安装包的更新", Toast.LENGTH_LONG).show()
            }
        }
    }
}
