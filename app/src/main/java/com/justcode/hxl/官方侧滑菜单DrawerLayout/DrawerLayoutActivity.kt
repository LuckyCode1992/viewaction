package com.justcode.hxl.官方侧滑菜单DrawerLayout

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.View
import android.widget.Toast
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_drawer_layout.*

class DrawerLayoutActivity : AppCompatActivity() {
    var isExtrusion = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_layout)

        drawerlayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
                Log.d("DrawerLayoutActivity ", "newState:$newState")
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                Log.d("DrawerLayoutActivity ", "slideOffset:$slideOffset")


                //侧滑，不挤压效果，平推效果
                // 得到contentView
                if (isExtrusion)
                    return
                val content = drawerlayout.getChildAt(0)
                val offset = (drawerView.width * slideOffset)

                var tag = drawerView.tag?.toString()
                if (tag == "rl_left") {
                    content.translationX = offset
                }
                if (tag == "rl_right") {
                    content.translationX = -offset
                }
            }

            override fun onDrawerClosed(drawerView: View) {
                Log.d("DrawerLayoutActivity ", "drawerView:closed")
            }

            override fun onDrawerOpened(drawerView: View) {
                Log.d("DrawerLayoutActivity ", "drawerView:Opened")
            }

        })

        btn_open_left.setOnClickListener {
            drawerlayout.openDrawer(rl_left)
        }
        btn_open_right.setOnClickListener {
            drawerlayout.openDrawer(rl_right)
        }
        btn_close_left.setOnClickListener {
            drawerlayout.closeDrawer(rl_left)
        }
        btn_close_right.setOnClickListener {
            drawerlayout.closeDrawer(rl_right)
        }

        rl_left.setOnClickListener {
            Toast.makeText(this, "点击了左边", Toast.LENGTH_SHORT).show()
        }
        rl_right.setOnClickListener {
            Toast.makeText(this, "点击了右边", Toast.LENGTH_SHORT).show()
        }

        btn_has_shadow.setOnClickListener {
            //设置没有阴影或者自定义阴影
            drawerlayout.setScrimColor(Color.GRAY)
        }
        btn_has_extrusion.setOnClickListener {
            isExtrusion = true
        }
        btn_no_shadow.setOnClickListener {
            //设置没有阴影或者自定义阴影
            drawerlayout.setScrimColor(Color.TRANSPARENT)
        }
        btn_no_extrusion.setOnClickListener {
            isExtrusion = false
        }


    }

}
