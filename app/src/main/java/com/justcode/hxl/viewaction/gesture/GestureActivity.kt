package com.justcode.hxl.viewaction.gesture

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_gesture.*

class GestureActivity : AppCompatActivity(),GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {
    override fun onDoubleTap(e: MotionEvent?): Boolean {
        Log.d("GestureActivity123","onDoubleTap")
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        Log.d("GestureActivity123","onDoubleTapEvent")
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        Log.d("GestureActivity123","onSingleTapConfirmed")
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        Log.d("GestureActivity123","onShowPress")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.d("GestureActivity123","onSingleTapUp")
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        Log.d("GestureActivity123","onDown")
        return true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        Log.d("GestureActivity123","onFling")
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        Log.d("GestureActivity123","onScroll")
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        Log.d("GestureActivity123","onLongPress")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture)
        var gesture = GestureDetector(this)
        gesture.setOnDoubleTapListener(this)
        gesture.setIsLongpressEnabled(false)
        iv.setOnTouchListener { v, event ->
            gesture.onTouchEvent(event)

        }
    }
}
