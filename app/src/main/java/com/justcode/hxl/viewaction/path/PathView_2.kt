package com.justcode.hxl.viewaction.path

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * 画贝塞尔曲线
 */
class PathView_2 : View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        paint.color = Color.parseColor("#ff00ff")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3f
        }


    var path = Path()
    var paint = Paint()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        path.quadTo(250f,800f,400f,500f)
//        canvas?.drawPath(path,paint)

        path.cubicTo(250f,800f,500f,500f,600f,800f)
        canvas?.drawPath(path,paint)

    }

}