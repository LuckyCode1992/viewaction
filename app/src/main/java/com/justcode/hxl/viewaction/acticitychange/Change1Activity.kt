package com.justcode.hxl.viewaction.acticitychange

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.viewaction.R

class Change1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change1)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
    }
}
