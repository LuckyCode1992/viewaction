package com.justcode.hxl.圆角

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import com.justcode.hxl.sugar.dpToPx
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_round.*

class RoundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round)

        //背景色
        cb_background.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                layout.setBackgroundColor(Color.parseColor("#FAD9DC"))
            } else {
                layout.setBackground(null)
            }

        })

        //圆形
        cb_circle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            layout.setRoundAsCircle(isChecked)

            findViewById<View>(R.id.layout_radius).visibility = if (isChecked) View.INVISIBLE else View.VISIBLE
        })
        //边框粗细
        seekbar_stroke_width.setOnSeekBarChangeListener(object : SimpleSeekBarChangeListener() {

             override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                layout.setStrokeWidth(progress)
            }
        })
        //左上角半径
        seekbar_radius_top_left.setOnSeekBarChangeListener(object : SimpleSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                layout.setTopLeftRadius(getProgressRadius(progress))
            }
        })
        //右上角半径
        seekbar_radius_top_right.setOnSeekBarChangeListener(object : SimpleSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                layout.setTopRightRadius(getProgressRadius(progress))
            }
        })
        //左下角半径
        seekbar_radius_bottom_left.setOnSeekBarChangeListener(object : SimpleSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                layout.setBottomLeftRadius(getProgressRadius(progress))
            }
        })
        //右下角半径
        seekbar_radius_bottom_right.setOnSeekBarChangeListener(object : SimpleSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                layout.setBottomRightRadius(getProgressRadius(progress))
            }
        })
        seekbar_stroke_width.setProgress(5f.dpToPx().toInt())

        cb_circle.setChecked(true)
    }

    private fun getProgressRadius(progress: Int): Int {
        val size = 300f.dpToPx()
        return (progress.toFloat() / 100 * size).toInt() / 2
    }
    abstract class SimpleSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {

        override fun onStartTrackingTouch(seekBar: SeekBar) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {

        }
    }
}
