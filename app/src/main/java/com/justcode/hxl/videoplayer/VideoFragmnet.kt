package com.justcode.hxl.videoplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.justcode.hxl.video_player.NiceVideoPlayerManager

import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragmnet : Fragment() {
    var text: String = ""
    var currentPosition = -1
    var list:MutableList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recycleview.layoutManager = layoutManager

        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-10_10-20-26.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-03_13-02-41.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-28_18-20-56.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-26_10-06-25.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-21_16-41-07.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-21_16-41-07.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-03_13-02-41.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-28_18-20-56.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-10_10-20-26.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-26_10-06-25.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-21_16-41-07.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-21_16-41-07.mp4")
        list.add("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-03_13-02-41.mp4")


    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d("onHiddenChanged", text + ":" + hidden)
        if (hidden) {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }
}