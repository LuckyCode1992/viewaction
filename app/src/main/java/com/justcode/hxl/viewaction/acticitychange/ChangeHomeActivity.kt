package com.justcode.hxl.viewaction.acticitychange

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.view.View
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_change_home.*

class ChangeHomeActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_home)

        btn_Explode.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, ExplodeActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        btn_Slide.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, SlideActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
        btn_Fade.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, FadeActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
        btn_Shared_Element.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, SharedElementActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, firstSharedView, "sharedView1").toBundle())
        }
        btn_Shared_Element_more.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, SharedElementActivity::class.java)

            val first = android.support.v4.util.Pair<View, String>(firstSharedView, ViewCompat.getTransitionName(firstSharedView))
            val second = android.support.v4.util.Pair<View, String>(secondSharedView, ViewCompat.getTransitionName(secondSharedView))
            val makeSceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation(this, first, second)
            startActivity(intent, makeSceneTransitionAnimation.toBundle())
        }
    }
}
