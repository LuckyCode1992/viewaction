package com.justcode.hxl.stack_card

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_card_detial.*

class CardDetialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detial)
        val title = intent.getStringExtra("title")
        val loggo = intent.getIntExtra("logo",0)
        val background = intent.getIntExtra("background",0)

        relative_layout.setBackgroundResource(background)
        logo_image_view.setImageResource(loggo)
        title_text_view.text = title
    }
}
