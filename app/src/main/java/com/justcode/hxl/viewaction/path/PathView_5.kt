package com.justcode.hxl.viewaction.path

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class PathView_5 : View {

    var mX: Float? = 0f
    var mY: Float? = 0f

    var path = Path()
    var paint = Paint()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        paint.setAntiAlias(true)
        paint.setStyle(Paint.Style.STROKE)
        paint.strokeWidth = 5f
        paint.color = Color.YELLOW
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                touchDown(event)
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(event)
            }
        }
        //更新绘制
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }

    fun touchMove(event: MotionEvent) {
        val x = event.getX();
        val y = event.getY();

        val previousX = mX
        val previousY = mY

        val dx = Math.abs(x - previousX!!)
        val dy = Math.abs(y - previousY!!)

        //两点之间的距离大于等于3时，连接连接两点形成直线
        if (dx >= 2 || dy >= 2) {
            //两点连成直线
            path.lineTo(x,y)

            //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
            mX = x
            mY = y
        }

    }

    fun touchDown(event: MotionEvent) {

        val x = event.x
        val y = event.y

        mX = x
        mY = y

        //mPath绘制的绘制起点
        path.moveTo(x, y)

    }
    fun clear(){
        path.reset()
        //更新绘制
        invalidate()
    }
}