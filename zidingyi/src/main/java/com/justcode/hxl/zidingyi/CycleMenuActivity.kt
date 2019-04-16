package com.justcode.hxl.zidingyi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.justcode.hxl.zidingyi.animation.rotateAnim
import kotlinx.android.synthetic.main.cycle_menu.*

class CycleMenuActivity : AppCompatActivity() {

    var isShowLevel1 = false
    var isShowLevel2 = false
    var isShowLevel3 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cycle_menu)

        rl_level2.post {
            rl_level2.pivotX = rl_level2.width / 2f
            rl_level2.pivotY = rl_level2.height * 1f
            rl_level2.rotation = 180f
        }

        rl_level3.post {
            rl_level3.pivotX = rl_level3.width / 2f
            rl_level3.pivotY = rl_level3.height * 1f
            rl_level3.rotation = 180f
        }


        iv_home.setOnClickListener {
            if (isShowLevel2) {
                isShowLevel2 = false
                hideLevel2()
                if (isShowLevel3) {
                    isShowLevel3 = false
                    hideLevel3()
                }
            } else {
                isShowLevel2 = true
                showLevel2()
            }
        }
        iv_search.setOnClickListener {
            Toast.makeText(this, "iv_search", Toast.LENGTH_LONG).show()
        }
        iv_menu.setOnClickListener {
            if (isShowLevel3) {
                isShowLevel3 = false
                hideLevel3()
            } else {
                isShowLevel3 = true
                showLevel3()
            }
        }
        iv_my.setOnClickListener {
            Toast.makeText(this, "iv_my", Toast.LENGTH_LONG).show()
        }
        iv_ch1.setOnClickListener {
            Toast.makeText(this, "iv_ch1", Toast.LENGTH_LONG).show()
        }
        iv_ch2.setOnClickListener {
            Toast.makeText(this, "iv_ch2", Toast.LENGTH_LONG).show()
        }
        iv_ch3.setOnClickListener {
            Toast.makeText(this, "iv_ch3", Toast.LENGTH_LONG).show()
        }
        iv_ch4.setOnClickListener {
            Toast.makeText(this, "iv_ch4", Toast.LENGTH_LONG).show()
        }
        iv_ch5.setOnClickListener {
            Toast.makeText(this, "iv_ch5", Toast.LENGTH_LONG).show()
        }
        iv_ch6.setOnClickListener {
            Toast.makeText(this, "iv_ch6", Toast.LENGTH_LONG).show()
        }
        iv_ch7.setOnClickListener {
            Toast.makeText(this, "iv_ch7", Toast.LENGTH_LONG).show()
        }


    }

    private fun hideLevel2() {
        rl_level2.rotateAnim(180f, 360f, y0 = rl_level2.height * 1f)
        rl_level2.pivotX = rl_level2.width / 2f
        rl_level2.pivotY = rl_level2.height * 1f
        rl_level2.rotation = 180f
    }

    private fun showLevel2() {
        rl_level2.rotateAnim(180f, 360f, y0 = rl_level2.height * 1f)
        rl_level2.pivotX = rl_level2.width / 2f
        rl_level2.pivotY = 0f
        rl_level2.rotation = 360f
    }

    private fun showLevel3() {
        rl_level3.rotateAnim(180f, 360f, y0 = rl_level3.height * 1f)
        rl_level3.pivotX = rl_level3.width / 2f
        rl_level3.pivotY = 0f
        rl_level3.rotation = 360f
    }

    private fun hideLevel3() {
        rl_level3.rotateAnim(180f, 360f, y0 = rl_level3.height * 1f)
        rl_level3.pivotX = rl_level3.width / 2f
        rl_level3.pivotY = rl_level3.height * 1f
        rl_level3.rotation = 180f
    }


}
