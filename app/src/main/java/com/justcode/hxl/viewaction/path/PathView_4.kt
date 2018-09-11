package com.justcode.hxl.viewaction.path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.R.attr.y
import android.R.attr.x
import android.R.attr.y
import android.R.attr.x


/**
 * 动态设置三阶贝塞尔曲线
 */
class PathView_4 : View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        paintBezier.setStyle(Paint.Style.STROKE)
        paintBezier.setColor(Color.BLACK)
        paintBezier.setStrokeWidth(10f)



        paintLine.setStyle(Paint.Style.STROKE)
        paintLine.setColor(Color.RED)
        paintLine.setStrokeWidth(3f)
    }

    companion object {
        var isControlPointTwo = false
    }
    var paintBezier = Paint()
    var paintLine = Paint()
    var controlPointOne = Point(200, 200)
    var controlPointTwo = Point(500, 200)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var path = Path()
        path.moveTo(0f, 500f)
        path.cubicTo(controlPointOne.x.toFloat(), controlPointOne.y.toFloat(), controlPointTwo.x.toFloat(), controlPointTwo.y.toFloat(), 700f, 500f)
        canvas?.drawPath(path, paintBezier)

        canvas?.drawPoint(controlPointOne.x.toFloat(), controlPointOne.y.toFloat(), paintBezier)
        canvas?.drawPoint(controlPointTwo.x.toFloat(), controlPointTwo.y.toFloat(), paintBezier)

        //绘制连线
        canvas?.drawLine(0f, 500f, controlPointOne.x.toFloat(), controlPointOne.y.toFloat(), paintLine)
        canvas?.drawLine(700f, 500f, controlPointOne.x.toFloat(), controlPointOne.y.toFloat(), paintLine)

        canvas?.drawLine(0f, 500f, controlPointTwo.x.toFloat(), controlPointTwo.y.toFloat(), paintLine)
        canvas?.drawLine(700f, 500f, controlPointTwo.x.toFloat(), controlPointTwo.y.toFloat(), paintLine)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_UP -> {

            }
            MotionEvent.ACTION_MOVE -> {
                if (isControlPointTwo) {
                    controlPointOne.x = event.x.toInt()
                    controlPointOne.y = event.y.toInt()
                } else {
                    controlPointTwo.x = event.x.toInt()
                    controlPointTwo.y = event.y.toInt()
                }
                invalidate()

            }
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}