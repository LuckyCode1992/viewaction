package com.justcode.hxl.viewaction

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var moveX = 0.0f
        var moveY = 0.0f
        var nowX = 0.0f
        var nowY = 0.0f

        var viewX = view.x
        var viewY=view.y

        var oldX = iv.x
        var oldY = iv.y
        iv.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        moveX = event.getX()
                        moveY = event.getY()
                        Log.d("MainActivity_123","22222222")

                    }
                    MotionEvent.ACTION_MOVE -> {
                        Log.d("MainActivity_123","ACTION_MOVE")
                        nowX = v?.x!! + (event.getX() - moveX)
                        nowY = v.y + (event.getY() - moveY)
                        v.translationX = nowX
                        v.translationY = nowY
                        tv.text = "新的坐标点：(" + nowX + "," + nowY + ")"



                        var yy = nowX-oldX
                        var xx = nowY-oldY
                        view.translationY = viewY+yy
                        view.translationX = viewX+xx

//                        viewNowX =  viewX+(event.getX() - moveX)
//                        viewNowY = viewY+(event.getY() - moveY)
//                        Log.d("move_move","X:"+(event.getX() - moveX)+"\n"+"Y:"+(event.getY() - moveY))
//                        Log.d("move_move","viewNowX:"+viewNowX+"\n"+"viewY:"+viewY)
//                        view.translationX = viewNowX
//                        view.translationY = viewNowY
                    }
                    MotionEvent.ACTION_UP -> {

                    }
                    else -> {

                    }
                }
                return true
            }

        })

    }
}
