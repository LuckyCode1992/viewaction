package com.justcode.hxl.sugar

import android.util.TypedValue
import com.justcode.hxl.MyAPP.Companion.context

fun Float.dpToPx(): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)