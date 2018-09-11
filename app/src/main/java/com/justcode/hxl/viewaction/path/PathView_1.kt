package com.justcode.hxl.viewaction.path

import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View

/**
 * 简单的画线
 */
class PathView_1 : View {

    constructor(context: Context?) : this(context, null, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, null, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.color = Color.parseColor("#FF0000")

        paint2.isAntiAlias = true
        paint2.style = Paint.Style.STROKE
        paint2.strokeWidth = 2f
        paint2.color = Color.parseColor("#00ff00")
    }


    var path = Path()
    var path2 = Path()
    var paint = Paint()
    var paint2 = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.lineTo(200f,200f)
        path.lineTo(400f,0f)
        canvas?.drawPath(path,paint)


        path.moveTo(300f,300f)
        path.lineTo(350f,400f)
        canvas?.drawPath(path,paint)

        path.moveTo(100f,500f)
        path.lineTo(200f,500f)
        path.lineTo(150f,600f)
        path.close()
        canvas?.drawPath(path,paint)


        paint2.pathEffect = DashPathEffect(floatArrayOf(2f,5f),0f)
        path2.moveTo(100f,600f)
        path2.lineTo(300f,700f)
        canvas?.drawPath(path2,paint2)


//        paint.pathEffect = DashPathEffect(floatArrayOf(5f,2f),0f)
//        path.moveTo(100f,720f)
//        path.lineTo(300f,820f)
//        canvas?.drawPath(path,paint)
//
//        paint.pathEffect = DashPathEffect(floatArrayOf(5f,5f),1f)
//        path.moveTo(100f,830f)
//        path.lineTo(300f,930f)
//        canvas?.drawPath(path,paint)
//
//        paint.pathEffect = DashPathEffect(floatArrayOf(5f,5f),10f)
//        path.moveTo(100f,940f)
//        path.lineTo(300f,1040f)
//        canvas?.drawPath(path,paint)
//
//        paint.pathEffect = DashPathEffect(floatArrayOf(10f,10f),100f)
//        path.moveTo(100f,1050f)
//        path.lineTo(300f,1150f)
//        canvas?.drawPath(path,paint)
    }
}