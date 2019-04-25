package com.justcode.hxl

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.justcode.hxl.Android和JS交互.WebViewActivity
import com.justcode.hxl.CoordinatorLayout折叠式布局.CoordinatorLayoutActivity
import com.justcode.hxl.StepperView.StepperViewActivity
import com.justcode.hxl.activity_animatoo.AnimatooActivity
import com.justcode.hxl.chelaile.CheLaileActivity
import com.justcode.hxl.imagescale.ImageScaleActivity
import com.justcode.hxl.luckyrecycleview.RecycleViewActivity
import com.justcode.hxl.maputil.MapUtilActivity
import com.justcode.hxl.permission.RxPermissions
import com.justcode.hxl.stack_card.StackCardActivity
import com.justcode.hxl.updateapp.UpdateAppActivity
import com.justcode.hxl.videoplayer.VideoPlayerActivity
import com.justcode.hxl.viewaction.R
import com.justcode.hxl.viewaction.acticitychange.Change1Activity
import com.justcode.hxl.viewaction.acticitychange.ChangeHomeActivity
import com.justcode.hxl.viewaction.path.gouwuche.GouwucheActivity
import com.justcode.hxl.viewaction.path.taoxin.TaoxinActivity
import com.justcode.hxl.viewaction.remoteview.NotifyActivity
import com.justcode.hxl.viewaction.shuxingdonghua.Main2Activity
import com.justcode.hxl.viewaction.viewdonghua.ViewDonghuaActivity
import com.justcode.hxl.二维码条形码验证码.QR_BAR_V_CODE_Activity
import com.justcode.hxl.jiaohuxiaoguo1.Jiaohu1Activity
import com.justcode.hxl.zidingyi.CycleMenuActivity
import com.justcode.hxl.zidingyi.ad_viewpager.AdActivity
import com.justcode.hxl.zidingyi.my_viewpager.MyViewPagerActivity
import com.justcode.hxl.zidingyi.myswitch.SwitchActivity
import com.justcode.hxl.zidingyi.zidingyishuxing.ZidingyishuxingActivity
import com.justcode.hxl.仿微信查看照片.PhotoViewerActivity
import com.justcode.hxl.仿阿里巴巴下拉刷新.AlibabaActivity
import com.justcode.hxl.圆角.RoundActivity
import com.justcode.hxl.官方侧滑菜单DrawerLayout.DrawerLayoutActivity
import com.justcode.hxl.悬浮窗.WindowActivity
import com.justcode.hxl.权限问题.PermissionsActivity
import com.justcode.hxl.点赞.GiveALikeActivity
import com.justcode.hxl.网络状态和声音.NetWorkAndVoiceActivity
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
        tv_shuxingdonghua.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, Main2Activity::class.java)
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
        tv_activity_stack_card.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, StackCardActivity::class.java)
            startActivity(intent)
        }
        tv_activity_drawerlayout.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, DrawerLayoutActivity::class.java)
            startActivity(intent)
        }
        tv_activity_coordinator.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, CoordinatorLayoutActivity::class.java)
            startActivity(intent)
        }
        tv_activity_input_pwd.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, InputPwdActivity::class.java)
            startActivity(intent)
        }

        tv_activity_network_voice.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, NetWorkAndVoiceActivity::class.java)
            startActivity(intent)
        }

        tv_activity_stepper_view.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, StepperViewActivity::class.java)
            startActivity(intent)
        }
        tv_activity_alibaba_xiala.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, AlibabaActivity::class.java)
            startActivity(intent)
        }
        tv_activity_updateapp.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, UpdateAppActivity::class.java)
            startActivity(intent)
        }
        tv_activity_jiaohu1.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, Jiaohu1Activity::class.java)
            startActivity(intent)
        }
        tv_activity_round.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, RoundActivity::class.java)
            startActivity(intent)
        }
        btn_cycle_menu.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, CycleMenuActivity::class.java)
            startActivity(intent)
        }
        btn_ad.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, AdActivity::class.java)
            startActivity(intent)
        }
        btn_switch.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, SwitchActivity::class.java)
            startActivity(intent)
        }
        btn_zidingyishuxing.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, ZidingyishuxingActivity::class.java)
            startActivity(intent)
        }
        btn_myviewpager.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, MyViewPagerActivity::class.java)
            startActivity(intent)
        }
    }

}
