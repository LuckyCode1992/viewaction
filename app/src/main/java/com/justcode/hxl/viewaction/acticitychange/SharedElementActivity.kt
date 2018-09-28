package com.justcode.hxl.viewaction.acticitychange

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.transition.TransitionInflater
import android.view.Window
import com.justcode.hxl.viewaction.R

class SharedElementActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_element)
    }
}
