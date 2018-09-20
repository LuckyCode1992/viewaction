package com.justcode.hxl.viewaction.path.taoxin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_taoxin.*

class TaoxinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taoxin)
        button.setOnClickListener {
            taoxin_change.setRuning(true)
        }
        button2.setOnClickListener {
            taoxin_change.reset()
        }

    }
}
