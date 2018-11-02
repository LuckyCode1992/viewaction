package com.justcode.hxl.activity_animatoo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.animatoo.Animatoo
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_animatoo.*
import android.content.Intent


class AnimatooActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animatoo)

        button1.setOnClickListener {
            skip(button1.text.toString())
            Animatoo.animateZoom(this)  //fire the zoom animation
        }
        button2.setOnClickListener {
            skip(button2.text.toString())
            Animatoo.animateFade(this)  //fire the zoom animation
        }
        button3.setOnClickListener {
            skip(button3.text.toString())
            Animatoo.animateWindmill(this)  //fire the zoom animation
        }
        button4.setOnClickListener {
            skip(button4.text.toString())
            Animatoo.animateSpin(this)  //fire the zoom animation
        }
        button5.setOnClickListener {
            skip(button5.text.toString())
            Animatoo.animateDiagonal(this)  //fire the zoom animation
        }
        button6.setOnClickListener {
            skip(button6.text.toString())
            Animatoo.animateSplit(this)  //fire the zoom animation
        }
        button7.setOnClickListener {
            skip(button7.text.toString())
            Animatoo.animateShrink(this)  //fire the zoom animation
        }
        button8.setOnClickListener {
            skip(button8.text.toString())
            Animatoo.animateCard(this)  //fire the zoom animation
        }
        button9.setOnClickListener {
            skip(button9.text.toString())
            Animatoo.animateInAndOut(this)  //fire the zoom animation
        }
        button10.setOnClickListener {
            skip(button10.text.toString())
            Animatoo.animateSwipeLeft(this)  //fire the zoom animation
        }
        button11.setOnClickListener {
            skip(button11.text.toString())
            Animatoo.animateSwipeRight(this)  //fire the zoom animation
        }
        button12.setOnClickListener {
            skip(button12.text.toString())
            Animatoo.animateSlideLeft(this)  //fire the zoom animation
        }
        button13.setOnClickListener {
            skip(button13.text.toString())
            Animatoo.animateSlideRight(this)  //fire the zoom animation
        }
        button14.setOnClickListener {
            skip(button14.text.toString())
            Animatoo.animateSlideDown(this)  //fire the zoom animation
        }
        button15.setOnClickListener {
            skip(button15.text.toString())
            Animatoo.animateSlideUp(this)  //fire the zoom animation
        }

    }

    fun skip(tag: String) {
        val intent = Intent(this, AnimatooDemoActivity::class.java)
        intent.putExtra("TAG", tag)
        startActivity(intent)

    }
}
