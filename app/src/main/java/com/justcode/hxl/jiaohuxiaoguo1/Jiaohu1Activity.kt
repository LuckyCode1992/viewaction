package com.justcode.hxl.jiaohuxiaoguo1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.widget.FrameLayout
import com.justcode.hxl.jiaohuxiaoguo1.behavior.HeaderBehavior
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_jiaohu1.*


class Jiaohu1Activity : AppCompatActivity() {
    private lateinit var mHeaderBehavior: HeaderBehavior
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jiaohu1)

        mHeaderBehavior = (findViewById<FrameLayout>(R.id.fl_head).layoutParams as CoordinatorLayout.LayoutParams).behavior as HeaderBehavior
        ivArrow.setOnClickListener {
            openHeader()
        }

    }

    private fun openHeader() {
        if (mHeaderBehavior.isClose()) {
            nsv.scrollTo(0, 0)
            mHeaderBehavior.scrollToOpen()
        }
    }

    override fun onBackPressed() {
        if (mHeaderBehavior.isClose()) {
            nsv.scrollTo(0, 0)
            mHeaderBehavior.scrollToOpen()
        } else {
            super.onBackPressed()
        }

    }
}
