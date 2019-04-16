package com.justcode.hxl.zidingyi.animation

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.RotateAnimation

inline fun View.rotateAnim(start: Float? = 0f, end: Float? = 180f, x0: Float? = -1f, y0: Float? = -1f,
                           duration: Long? = 500, fillAfter: Boolean? = true
) {
    val x = if (x0 == -1f) {
        this.width / 2f
    } else {
        x0
    }
    val y = if (y0 == -1f) {
        this.height / 2f
    } else {
        y0
    }

    val rorate = RotateAnimation(start!!, end!!, x!!, y!!)
    rorate.duration = duration!!
    //设置动画停留在播放后的状态
    rorate.fillAfter = fillAfter!!
    this.startAnimation(rorate)

}
inline fun View.roateAnimByObj(start: Float?=0f,end: Float?=180f,x0:Float?=-1f,y0: Float?=-1f,duration: Long?=500){
    val x = if (x0 == -1f) {
        this.width / 2f
    } else {
        x0
    }
    val y = if (y0 == -1f) {
        this.height / 2f
    } else {
        y0
    }
    this.pivotX = x!!
    this.pivotY = y!!
    val objectAnimator = ObjectAnimator.ofFloat(this, "rotation",start!!,end!!)
    objectAnimator.duration = duration!!
    objectAnimator.start()

}