package com.justcode.hxl.viewaction.path.taoxin

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.R.attr.y
import android.R.attr.x
import android.graphics.*
import java.lang.Math.sqrt
import java.lang.Math.tan
import android.R.attr.x
import android.R.attr.y
import android.graphics.PorterDuffXfermode


class MycycleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    var paint: Paint? = null
    var mCenterX: Int = 0
    var mCenterY: Int = 0

    var mCirclePaint: Paint? = null
    var mPointPaint: Paint? = null
    var mControlPointPaint: Paint? = null
    var mCircleRadius = 150

    var mPointDatas: MutableList<PointF>? = null //放置四个数据点的集合
    var mPointControlls: MutableList<PointF>? = null//方式8个控制点的集合

    var mDuration = 1000 //动画总时间
    var mCurrTime = 0  //当前已进行时间
    var mCount = 100//将总时间划分多少块
    var mPiece = (mDuration / mCount).toLong() //每一块的时间
    private var isRuning = false
    var canvas: Canvas? = null

    var nor1 = 0f
    var nor2 = 0f
    var nor3 = 0f
    var nor4 = 0f
    var nor5 = 0f

    //控制点比例常量，数学问题，不用管
    val CONTROL_POINT_Multiple = 4 * (sqrt(2.toDouble()) - 1) / 3

    init {
        paint = Paint()
        paint?.let {
            it.color = Color.BLACK
            it.style = Paint.Style.FILL
            it.isAntiAlias = true
            it.strokeWidth = 2f
        }
        mCirclePaint = Paint()
        mCirclePaint?.let {
            it.setColor(Color.RED)
            it.strokeWidth = 10f
            it.style = Paint.Style.STROKE
            it.isAntiAlias = true
        }
        mPointPaint = Paint()
        mPointPaint?.let {
            it.color = Color.BLUE
            it.strokeWidth = 5f
            it.style = Paint.Style.FILL
            it.isAntiAlias = true
        }
        mControlPointPaint = Paint()
        mControlPointPaint?.let {
            it.color = Color.GREEN
            it.strokeWidth = 5f
            it.style = Paint.Style.FILL
            it.isAntiAlias = true
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //中心点
        mCenterX = width / 2
        mCenterY = height / 2

        mPointDatas = ArrayList()
        mPointControlls = ArrayList()
        //数据点 集合
        mPointDatas?.add(PointF(mCenterX.toFloat(), (mCenterY - mCircleRadius).toFloat()))
        mPointDatas?.add(PointF((mCenterX + mCircleRadius).toFloat(), mCenterY.toFloat()))
        mPointDatas?.add(PointF(mCenterX.toFloat(), (mCenterY + mCircleRadius).toFloat()))
        mPointDatas?.add(PointF((mCenterX - mCircleRadius).toFloat(), mCenterY.toFloat()))
        //控制点集合

        mPointControlls?.let {

            it.add(PointF((mCenterX + mCircleRadius * CONTROL_POINT_Multiple).toFloat(), (mCenterY - mCircleRadius).toFloat()))
            it.add(PointF((mCenterX + mCircleRadius).toFloat(), (mCenterY - mCircleRadius * CONTROL_POINT_Multiple).toFloat()))

            it.add(PointF((mCenterX + mCircleRadius).toFloat(), (mCenterY + mCircleRadius * CONTROL_POINT_Multiple).toFloat()))
            it.add(PointF((mCenterX + mCircleRadius * CONTROL_POINT_Multiple).toFloat(), (mCenterY + mCircleRadius).toFloat()))

            it.add(PointF((mCenterX - mCircleRadius * CONTROL_POINT_Multiple).toFloat(), (mCenterY + mCircleRadius).toFloat()))
            it.add(PointF((mCenterX - mCircleRadius).toFloat(), (mCenterY + mCircleRadius * CONTROL_POINT_Multiple).toFloat()))

            it.add(PointF((mCenterX - mCircleRadius).toFloat(), (mCenterY - mCircleRadius * CONTROL_POINT_Multiple).toFloat()))
            it.add(PointF((mCenterX - mCircleRadius * CONTROL_POINT_Multiple).toFloat(), (mCenterY - mCircleRadius).toFloat()))
        }

        if(nor1==0f){
            nor1 = mPointDatas!![0].y
            nor2 = mPointControlls!![2].x
            nor3 =   mPointControlls!![3].y
            nor4 =  mPointControlls!![4].y
            nor5 =  mPointControlls!![5].x
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        画坐标系
        canvas?.let {
            this.canvas = canvas
            //绘制x，y轴坐标系
            canvas.drawLine(mCenterX!!.toFloat(), 0f, mCenterX!!.toFloat(), height.toFloat(), paint)
            canvas.drawLine(0f, mCenterY!!.toFloat(), width.toFloat(), mCenterY!!.toFloat(), paint)

            //绘制数据点
            mPointDatas?.forEach {
                canvas.drawPoint(it.x, it.y, mPointPaint)
            }
            //绘制控制点
            mPointControlls?.forEach {
                canvas.drawPoint(it.x, it.y, mControlPointPaint)
            }

            //path画圆
            val pathCircle = Path()
            //移动到第一个数据点
            pathCircle.moveTo(mPointDatas!![0].x, mPointDatas!![0].y)
            mPointDatas?.let {
                for (i in 0 until it.size) {
                    if (i == it.size - 1) {
                        pathCircle.cubicTo(mPointControlls!![2 * i].x, mPointControlls!![2 * i].y, mPointControlls!![2 * i + 1].x, mPointControlls!![2 * i + 1].y, mPointDatas!![0].x, mPointDatas!![0].y)
                    } else {
                        pathCircle.cubicTo(mPointControlls!![2 * i].x, mPointControlls!![2 * i].y, mPointControlls!![2 * i + 1].x, mPointControlls!![2 * i + 1].y, mPointDatas!![i + 1].x, mPointDatas!![i + 1].y)
                    }
                }
            }

            canvas.drawPath(pathCircle, mCirclePaint)



            if (isRuning) {
                //动态改变数据点和辅助点
                mCurrTime += mPiece.toInt()
                if (mCurrTime < mDuration) {

                    mPointDatas!![0].y += 120f / mCount

                    mPointControlls!![2].x -= 20f / mCount
                    mPointControlls!![3].y -= 80f / mCount
                    mPointControlls!![4].y -= 80f / mCount
                    mPointControlls!![5].x += 20f / mCount

                    postInvalidateDelayed(mPiece)
                }
            }

        }


    }

    fun setRuning(runing: Boolean) {
        isRuning = runing
        invalidate()
    }

    fun reset() {
        isRuning = false
        mCurrTime = 0
        mPointDatas!![0].y = nor1
        mPointControlls!![2].x = nor2

        mPointControlls!![3].y = nor3
        mPointControlls!![4].y = nor4
        mPointControlls!![5].x = nor5
        invalidate()
    }


}