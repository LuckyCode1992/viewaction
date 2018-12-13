package com.justcode.hxl

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.justcode.hxl.Android和JS交互.WebViewActivity
import com.justcode.hxl.activity_animatoo.AnimatooActivity
import com.justcode.hxl.chelaile.CheLaileActivity
import com.justcode.hxl.imagescale.ImageScaleActivity
import com.justcode.hxl.lucky_recycleview.smartrefresh.layout.SmartRefreshLayout
import com.justcode.hxl.lucky_recycleview.smartrefresh.layout.footer.ClassicsFooter
import com.justcode.hxl.luckyrecycleview.LoadingHeader
import com.justcode.hxl.luckyrecycleview.RecycleViewActivity
import com.justcode.hxl.maputil.MapUtilActivity
import com.justcode.hxl.permission.RxPermissions
import com.justcode.hxl.videoplayer.VideoPlayerActivity
import com.justcode.hxl.viewaction.R
import com.justcode.hxl.viewaction.acticitychange.Change1Activity
import com.justcode.hxl.viewaction.acticitychange.ChangeHomeActivity
import com.justcode.hxl.viewaction.path.gouwuche.GouwucheActivity
import com.justcode.hxl.viewaction.path.taoxin.TaoxinActivity
import com.justcode.hxl.viewaction.remoteview.NotifyActivity
import com.justcode.hxl.viewaction.viewdonghua.ViewDonghuaActivity
import com.justcode.hxl.二维码条形码验证码.QR_BAR_V_CODE_Activity
import com.justcode.hxl.仿微信查看照片.PhotoViewerActivity
import com.justcode.hxl.悬浮窗.WindowActivity
import com.justcode.hxl.权限问题.PermissionsActivity
import com.justcode.hxl.点赞.GiveALikeActivity
import com.justcode.hxl.设备信息和工具.DeviceActivity
import com.justcode.hxl.进度条.ProgressActivity

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
        tv_xuanfuchuang.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, WindowActivity::class.java)
            startActivity(intent)
        }
        tv_premissions.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, PermissionsActivity::class.java)
            startActivity(intent)
        }
        tv_codes.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, QR_BAR_V_CODE_Activity::class.java)
            startActivity(intent)
        }
        tv_scale_image.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, ImageScaleActivity::class.java)
            startActivity(intent)
        }
        tv_device_info.setOnClickListener {

            RxPermissions(this)
                    .request(Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE)
                    .subscribe {
                        if (it) {
                            var intent = Intent()
                            intent.setClass(this, DeviceActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "请给权限", Toast.LENGTH_SHORT).show()
                        }
                    }

        }
        tv_progress.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, ProgressActivity::class.java)
            startActivity(intent)
        }
        tv_give_a_like.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, GiveALikeActivity::class.java)
            startActivity(intent)
        }
        tv_maputil.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, MapUtilActivity::class.java)
            startActivity(intent)
        }

        tv_activity_animatoo.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, AnimatooActivity::class.java)
            startActivity(intent)
        }

        tv_activity_chelaile.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, CheLaileActivity::class.java)
            startActivity(intent)
        }
        tv_activity_video.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, VideoPlayerActivity::class.java)
            startActivity(intent)
        }
        tv_activity_recycle.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, RecycleViewActivity::class.java)
            startActivity(intent)
        }


    }

}
