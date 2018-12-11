package com.justcode.hxl.videoplayer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.justcode.hxl.video_player.NiceVideoPlayerManager
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : AppCompatActivity() {
    private var showingFragment: Fragment? = null
    val fragment1 by lazy {
        val fragment =  VideoFragmnet()
        fragment.text = "fragment1"
        fragment
    }

    val fragment2 by lazy {
        val fragment =  VideoFragmnet()
        fragment.text = "fragment2"
        fragment
    }

    val fragment3 by lazy {
        val fragment =  VideoFragmnet()
        fragment.text = "fragment3"
        fragment
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        switchFragment(fragment1)
        btn1.setOnClickListener {
            switchFragment(fragment1)
        }
        btn2.setOnClickListener {
            switchFragment(fragment2)
        }
        btn3.setOnClickListener {
            switchFragment(fragment3)
        }


    }
    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        showingFragment?.let {
            if (it.isAdded) {
                transaction.hide(showingFragment)
            }
        }
        if (fragment.isAdded) {
            transaction.show(fragment)
        } else {
            transaction.add(R.id.content, fragment)
        }
        transaction.commitAllowingStateLoss()
        showingFragment = fragment
    }

    override fun onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return
        super.onBackPressed()
    }
}
