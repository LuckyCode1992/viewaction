package com.justcode.hxl.viewaction

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.viewaction.path.gouwuche.GouwucheActivity
import com.justcode.hxl.viewaction.path.taoxin.TaoxinActivity
import com.justcode.hxl.viewaction.remoteview.NotifyActivity
import com.justcode.hxl.仿微信查看照片.PhotoViewerActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_NotifyActivity.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, NotifyActivity::class.java)
            startActivity(intent)
        }

        tv_taoxin.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, TaoxinActivity::class.java)
            startActivity(intent)
        }

        tv_gouwuche.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, GouwucheActivity::class.java)
            startActivity(intent)
        }

        tv_fangweixinchakanzhaopian.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, PhotoViewerActivity::class.java)
            startActivity(intent)
        }
    }

}
