package com.justcode.hxl.activity_animatoo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.animatoo.Animatoo
import com.justcode.hxl.viewaction.R

class AnimatooDemoActivity : AppCompatActivity() {

    val tag by lazy {
        val tag = intent.getStringExtra("TAG")
        tag
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animatoo_demo)
    }

    override fun finish() {
        super.finish()
        when (tag) {
            "animateZoom" -> {
                Animatoo.animateZoom(this)

            }
            "animateFade" -> {
                Animatoo.animateFade(this)
            }
            "animateWindmill" -> {
                Animatoo.animateWindmill(this)
            }
            "animateSpin" -> {
                Animatoo.animateSpin(this)
            }
            "animateDiagonal" -> {
                Animatoo.animateDiagonal(this)
            }
            "animateSplit" -> {
                Animatoo.animateSplit(this)
            }
            "animateShrink" -> {
                Animatoo.animateShrink(this)
            }
            "animateCard" -> {
                Animatoo.animateInAndOut(this)
            }
            "animateInAndOut" -> {

                Animatoo.animateCard(this)
            }
            "animateSwipeLeft" -> {
                Animatoo.animateSwipeRight(this)
            }
            "animateSwipeRight" -> {
                Animatoo.animateSwipeLeft(this)
            }
            "animateSlideLeft" -> {
                Animatoo.animateSlideRight(this)
            }
            "animateSlideRight" -> {

                Animatoo.animateSlideLeft(this)
            }
            "animateSlideDown" -> {
                Animatoo.animateSlideUp(this)
            }
            "animateSlideUp" -> {
                Animatoo.animateSlideDown(this)

            }

        }
    }

}
