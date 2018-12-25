package com.justcode.hxl

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.justcode.hxl.tools.keyboard_pwd.KeyboardUtil
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_input_pwd.*

class InputPwdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_pwd)


        input_pwd.initStyle(R.drawable.edit_num_bg, 6, 0.33f, "#999999", "#999999", 20)
        input_pwd.setOnTextFinishListener {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        val keyboardUtil = KeyboardUtil(this)
        keyboardUtil.attachTo(input_pwd.editText)
        input_pwd.setOnClickListener {
            keyboardUtil.showKeyboard()
        }
        input_pwd.editText.setOnClickListener {
            keyboardUtil.showKeyboard()

        }


    }
}
