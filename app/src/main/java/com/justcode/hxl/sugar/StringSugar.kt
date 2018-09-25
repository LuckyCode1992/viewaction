package com.justcode.hxl.sugar

import android.util.Log
import android.widget.Toast
import com.justcode.hxl.viewaction.MyAPP

fun String.toast() {
    Toast.makeText(MyAPP.myApp, this, Toast.LENGTH_SHORT).show()
}

fun String.log_d(tag: String) {
    Log.d(tag, this)
}