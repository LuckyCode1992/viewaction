package com.justcode.hxl.二维码条形码验证码

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.justcode.hxl.tools.二维码条形码验证码.BarCode
import com.justcode.hxl.tools.二维码条形码验证码.QRCode
import com.justcode.hxl.tools.二维码条形码验证码.RandomValidationCode
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_qr__bar__v__code_.*

class QR_BAR_V_CODE_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr__bar__v__code_)

        btn_qrcode.setOnClickListener {
            var qrcode = et_qrcode.text.toString()
            if (TextUtils.isEmpty(qrcode)) {
                Toast.makeText(this, "二维码不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            QRCode.builder(qrcode)
                    .codeSide(600)
                    .into(iv_qrcode)
        }
        btn_brcode.setOnClickListener {
            var brcode = et_brcode.text.toString()
            if (TextUtils.isEmpty(brcode)) {
                Toast.makeText(this, "条形码不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

         BarCode.builder(brcode)
                 .codeWidth(1000)
                 .codeHeight(300)
                 .into(iv_brcode)

        }
        btn_vcode.setOnClickListener {
            iv_vcode.setImageBitmap(RandomValidationCode.getInstance().createBitmap())
            Log.d("vcode", ":" + RandomValidationCode.getInstance().code)
        }
        btn_scan.setOnClickListener {

        }
    }
}
