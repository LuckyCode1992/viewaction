package com.justcode.hxl.viewaction.acticitychange

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.Window
import com.justcode.hxl.viewaction.R
import android.transition.TransitionInflater


class ExplodeActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        val explode = TransitionInflater.from(this).inflateTransition(R.transition.explode)
        //退出时使用
        window.exitTransition = explode
        //第一次进入时使用
        window.enterTransition = explode
        //再次进入时使用
        window.reenterTransition = explode
        setContentView(R.layout.activity_explode)
    }
}
