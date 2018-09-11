package com.justcode.hxl.viewaction.view_touch

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_ontouch.*

class OntouchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ontouch)


        tv_waibulanjie.setOnClickListener {
            MyRelativeLayout.intercepted = true
        }
        tv_neibulanjie.setOnClickListener {
            MyRelativeLayout.intercepted = false
        }

        intercepted_false.setOnClickListener {
            MyRelativeLayout.intercepted = false
        }
        intercepted_true.setOnClickListener {
            MyRelativeLayout.intercepted = true
        }
        touch_true.setOnClickListener {
            MyRelativeLayout.touch = true
        }
        touch_false.setOnClickListener {
            MyRelativeLayout.touch = false
        }

iv4.setOnTouchListener(object : View.OnTouchListener{
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("Ontouch_Ontouch", "view onTouchEvent ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("Ontouch_Ontouch", "view onTouchEvent  ACTION_MOVE")
            }
            MotionEvent.ACTION_UP -> {
                Log.d("Ontouch_Ontouch", "view onTouchEvent  ACTION_UP")
            }
            else -> {

            }
        }
        return true
    }

})


        iv.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Log.d("OntouchActivity123", "iv_onTouch_false")
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.d("OntouchActivity123", "iv_onTouch_false_ACTION_DOWN")
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.d("OntouchActivity123", "iv_onTouch_false_ACTION_UP")
                    }
                }
                return false
            }

        })
        rl.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Log.d("OntouchActivity123", "rl_onTouch_false")
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.d("OntouchActivity123", "rl_onTouch_false_ACTION_DOWN")
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.d("OntouchActivity123", "rl_onTouch_false_ACTION_UP")
                    }
                }
                return false
            }

        })



        iv1.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Log.d("OntouchActivity123", "iv_onTouch_true")
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.d("OntouchActivity123", "iv_onTouch_true_ACTION_DOWN")
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.d("OntouchActivity123", "iv_onTouch_true_ACTION_UP")
                    }
                }

                return true
            }

        })
        rl1.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Log.d("OntouchActivity123", "rl_onTouch_false")
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.d("OntouchActivity123", "rl_onTouch_false_ACTION_DOWN")
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.d("OntouchActivity123", "rl_onTouch_false_ACTION_UP")
                    }
                }
                return false
            }

        })



        iv2.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Log.d("OntouchActivity123", "iv_onTouch_false")
                return false
            }

        })
        rl2.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Log.d("OntouchActivity123", "rl_onTouch_true")
                return true
            }

        })



        iv3.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Log.d("OntouchActivity123", "iv_onTouch_true")
                return true
            }

        })
        rl3.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Log.d("OntouchActivity123", "rl_onTouch_true")
                return true
            }

        })

    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("OntouchActivity123", "activity 拦截")
        return super.dispatchTouchEvent(ev)
    }
}
