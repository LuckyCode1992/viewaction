package com.justcode.hxl.viewaction.acticitychange

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.transition.TransitionInflater
import android.view.Window
import com.justcode.hxl.viewaction.R

class SlideActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        val slide = TransitionInflater.from(this).inflateTransition(R.transition.slide)
        //退出时使用
        window.exitTransition = slide
        //第一次进入时使用
        window.enterTransition = slide
        //再次进入时使用
        window.reenterTransition = slide
        setContentView(R.layout.activity_slide)
    }
}
