package com.justcode.hxl.zidingyi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.zidingyi.animation.rotateAnim
import kotlinx.android.synthetic.main.cycle_menu.*

class CycleMenuActivity : AppCompatActivity() {

    var isShowLevel1 = true
    var isShowLevel2 = true
    var isShowLevel3 = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cycle_menu)

        iv_home.setOnClickListener {
            if (isShowLevel2) {
                isShowLevel2 = false
                showLevel2()
                if (isShowLevel3) {
                    isShowLevel3 = false
                    hideLevel3()
                }
            } else {
                isShowLevel2 = true
                hideLevel2()

            }
        }
        iv_search.setOnClickListener {

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

        }
        iv_ch1.setOnClickListener {

        }
        iv_ch2.setOnClickListener {

        }
        iv_ch3.setOnClickListener {

        }
        iv_ch4.setOnClickListener {

        }
        iv_ch5.setOnClickListener {

        }
        iv_ch6.setOnClickListener {

        }
        iv_ch7.setOnClickListener {

        }
    }

    private fun hideLevel2() {
        rl_level2.rotateAnim(180f, 360f, y0 = rl_level2.height * 1f)
    }

    private fun showLevel2() {
        rl_level2.rotateAnim(0f, 180f, y0 = rl_level2.height * 1f)
    }

    private fun showLevel3() {
        rl_level3.rotateAnim(180f, 360f, y0 = rl_level3.height * 1f)
    }

    private fun hideLevel3() {
        rl_level3.rotateAnim(0f, 180f, y0 = rl_level3.height * 1f)
    }


}
