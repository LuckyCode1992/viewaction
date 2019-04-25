package com.justcode.hxl.zidingyi.my_viewpager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.justcode.hxl.zidingyi.R
import kotlinx.android.synthetic.main.activity_my_view_pager.*

class MyViewPagerActivity : AppCompatActivity() {

    val ids = intArrayOf(R.drawable.image1_zdy, R.drawable.image1_zdy, R.drawable.image1_zdy, R.drawable.image1_zdy)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_view_pager)
        ids.forEach {
            val imageView = ImageView(this)
//            imageView.adjustViewBounds = true
//            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setImageResource(it)
//            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
//            imageView.layoutParams = layoutParams
            my_viewpager.addView(imageView)
        }
    }
}
