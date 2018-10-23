package com.justcode.hxl

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.Android和JS交互.WebViewActivity
import com.justcode.hxl.viewaction.R
import com.justcode.hxl.viewaction.acticitychange.Change1Activity
import com.justcode.hxl.viewaction.acticitychange.ChangeHomeActivity
import com.justcode.hxl.viewaction.path.gouwuche.GouwucheActivity
import com.justcode.hxl.viewaction.path.taoxin.TaoxinActivity
import com.justcode.hxl.viewaction.remoteview.NotifyActivity
import com.justcode.hxl.viewaction.viewdonghua.ViewDonghuaActivity
import com.justcode.hxl.仿微信查看照片.PhotoViewerActivity
import com.justcode.hxl.悬浮窗.WindowActivity
import com.justcode.hxl.权限问题.PermissionsActivity

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
        tv_viewdonghua.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, ViewDonghuaActivity::class.java)
            startActivity(intent)
        }
        tv_changeacticity1.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, Change1Activity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
        }
        tv_changeacticity2.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, ChangeHomeActivity::class.java)
            startActivity(intent)
        }
        tv_webview.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, WebViewActivity::class.java)
            startActivity(intent)
        }
        tv_xuanfuchuang.setOnClickListener{
            var intent = Intent()
            intent.setClass(this, WindowActivity::class.java)
            startActivity(intent)
        }
        tv_premissions.setOnClickListener{
            var intent = Intent()
            intent.setClass(this, PermissionsActivity::class.java)
            startActivity(intent)
        }
    }

}
