package com.justcode.hxl.viewaction.acticitychange

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.transition.TransitionInflater
import android.view.Window
import com.justcode.hxl.viewaction.R

class FadeActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        val fade = TransitionInflater.from(this).inflateTransition(R.transition.fade)
        //退出时使用
        window.exitTransition = fade
        //第一次进入时使用
        window.enterTransition = fade
        //再次进入时使用
        window.reenterTransition = fade
        setContentView(R.layout.activity_fade)
    }
}
