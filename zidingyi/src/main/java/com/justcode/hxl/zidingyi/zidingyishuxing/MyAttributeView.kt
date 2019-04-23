package com.justcode.hxl.zidingyi.zidingyishuxing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.justcode.hxl.zidingyi.R

class MyAttributeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    //获取属性的方式：
    //1.用命名空间获取
    //2.遍历属性集合
    //3.使用系统工具
    var myAge: Int? = null
    var myName: String? = null
    var myBg: Bitmap? = null

    init {
        //1.用命名空间获取
        val name = attrs?.getAttributeValue("http://schemas.android.com/apk/res-auto", "name")
        val age = attrs?.getAttributeValue("http://schemas.android.com/apk/res-auto", "age")
        val bg = attrs?.getAttributeValue("http://schemas.android.com/apk/res-auto", "bg")
        Log.d("MyAttributeView", "name:$name  age:$age  bg:$bg ")

        //2.遍历属性集合

        for (i in 0 until attrs?.attributeCount!!) {
            Log.d("MyAttributeView", attrs?.getAttributeName(i) + ":" + attrs.getAttributeValue(i))
        }
        //3.使用系统工具
        val obtainStyledAttributes = context?.obtainStyledAttributes(attrs, R.styleable.MyAttributeView)

        for (i in 0 until obtainStyledAttributes!!.indexCount) {
            val index = obtainStyledAttributes.getIndex(i)
            when (index) {
                R.styleable.MyAttributeView_name -> {
                    myName = obtainStyledAttributes.getString(index)
                }
                R.styleable.MyAttributeView_age -> {
                    myAge = obtainStyledAttributes.getInt(index,0)
                }
                R.styleable.MyAttributeView_bg -> {
                    val drawable = obtainStyledAttributes.getDrawable(index)
                    val bitmapDrawable = drawable as BitmapDrawable
                    myBg = bitmapDrawable.bitmap
                }
            }
        }
        obtainStyledAttributes.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        canvas?.drawText("$myName---$myAge",50f,50f,paint)
        canvas?.drawBitmap(myBg,50f,200f,paint)

    }
}