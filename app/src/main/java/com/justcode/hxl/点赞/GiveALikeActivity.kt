package com.justcode.hxl.点赞

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.justcode.hxl.tools.点赞.ShineButton
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_give_alike.*
import java.util.*

class GiveALikeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_give_alike)

        po_image0.init(this)
        po_image1.init(this)
        po_image2.init(this)
        po_image3.init(this)


        val shineButtonJava = ShineButton(this)

        shineButtonJava.setBtnColor(Color.GRAY)
        shineButtonJava.setBtnFillColor(Color.RED)
        shineButtonJava.setShapeResource(R.raw.heart)
        shineButtonJava.setAllowRandomColor(true)
        shineButtonJava.setShineSize(100)

        val layoutParams = RelativeLayout.LayoutParams(100, 100)
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        shineButtonJava.layoutParams = layoutParams
        wrapper.addView(shineButtonJava)

        po_image0.setOnCheckStateChangeListener(object : ShineButton.OnCheckedChangeListener {
            override fun onCheckedChanged(view: View?, checked: Boolean) {
                Log.d("GiveALikeActivity_check", "po_image0:" + checked)

            }

        })
        po_image1.setOnCheckStateChangeListener(object : ShineButton.OnCheckedChangeListener {
            override fun onCheckedChanged(view: View?, checked: Boolean) {
                Log.d("GiveALikeActivity_check", "po_image1:" + checked)

            }

        })
        po_image2.setOnCheckStateChangeListener(object : ShineButton.OnCheckedChangeListener {
            override fun onCheckedChanged(view: View?, checked: Boolean) {
                Log.d("GiveALikeActivity_check", "po_image2:" + checked)

            }

        })
        po_image3.setOnCheckStateChangeListener(object : ShineButton.OnCheckedChangeListener {
            override fun onCheckedChanged(view: View?, checked: Boolean) {
                Log.d("GiveALikeActivity_check", "po_image3:" + checked)

            }

        })
        val random = Random()
        love.setOnClickListener {
            val rgb = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255))
            heart_layout.addHeart(rgb)
        }
    }
}
