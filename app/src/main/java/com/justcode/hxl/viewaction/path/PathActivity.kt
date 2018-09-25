package com.justcode.hxl.viewaction.path

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.sugar.log_d
import com.justcode.hxl.sugar.toast
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_path.*

class PathActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path)

        clear.setOnClickListener{
            p5.clear()
        }
        "hello".toast()
        "hi".log_d("PathActivity")
//        button.setOnClickListener {
//            PathView_4.isControlPointTwo = !PathView_4.isControlPointTwo
//        }
    }
}
