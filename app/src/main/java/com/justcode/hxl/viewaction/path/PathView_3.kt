package com.justcode.hxl.viewaction.path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.R.attr.y
import android.R.attr.x

/**
 * 动态设置二阶贝塞尔曲线
 */
class PathView_3 : View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    var point = Point(200, 200)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var path = Path()
        var paint = Paint()
        paint.color = Color.parseColor("#ff00ff")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f

        path.moveTo(100f, 500f)
        path.quadTo(point.x.toFloat(), point.y.toFloat(), 700f, 500f)
        canvas?.drawPath(path, paint)

        canvas?.drawPoint(point.x.toFloat(), point.y.toFloat(), paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_UP -> {

            }
            MotionEvent.ACTION_MOVE -> {
                point.x = event.x.toInt()
                point.y = event.y.toInt()
                invalidate()
            }
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}