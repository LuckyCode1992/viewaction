package com.justcode.hxl.jiaohuxiaoguo1.utils

import android.content.Context
import android.util.TypedValue
import com.justcode.hxl.viewaction.R


object Utils{
    fun getScrollHeight(ctx: Context):Float{
       val  mHeight = ctx.resources.getDimension(R.dimen.behavior_height).toInt()
       val actionBarSize = getActionBarHeight(ctx)
        return (mHeight - actionBarSize)/getScrollFriction()
    }
    fun getScrollFriction():Float = 1.5f
    fun getActionBarHeight(ctx: Context): Int {
        var actionBarHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, ctx.resources.displayMetrics).toInt()
        val tv = TypedValue()
        if (ctx.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, ctx.resources.displayMetrics)
        }
        return actionBarHeight
    }
    fun getStatusBarHeight(ctx: Context):Int{
        var result = 0
        val resourceId = ctx.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = ctx.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
    fun range(min:Float,max:Float,value:Float):Float{
        return Math.min(Math.max(min,value),max)
    }
}