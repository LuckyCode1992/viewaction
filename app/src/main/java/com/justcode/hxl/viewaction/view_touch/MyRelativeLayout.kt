package com.justcode.hxl.viewaction.view_touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout

class MyRelativeLayout : RelativeLayout {

    companion object {
        var intercepted = false
        var touch = false
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("Ontouch_Ontouch", "viewgroup dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {


        ev?.let {
            var x = ev.x
            var y = ev.y
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("Ontouch_Ontouch", "viewgroup onInterceptTouchEvent ACTION_DOWN x:$x  y:$y")
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d("Ontouch_Ontouch", "viewgroup onInterceptTouchEvent ACTION_MOVE x:$x  y:$y")
                }
                MotionEvent.ACTION_UP -> {
                    Log.d("Ontouch_Ontouch", "viewgroup onInterceptTouchEvent ACTION_UP x:$x  y:$y")
                }
                else -> {

                }
            }

        }
        Log.d("Ontouch_Ontouch", "viewgroup intercepted: $intercepted")
        return intercepted
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("Ontouch_Ontouch", "viewgroup onTouchEvent touch： $touch")

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("Ontouch_Ontouch", "viewgroup onTouchEvent ACTION_DOWN x:$x  y:$y")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("Ontouch_Ontouch", "viewgroup onTouchEvent  ACTION_MOVE x:$x  y:$y")
            }
            MotionEvent.ACTION_UP -> {
                Log.d("Ontouch_Ontouch", "viewgroup onTouchEvent  ACTION_UP x:$x  y:$y")
            }
            else -> {

            }
        }
        return touch
    }
}