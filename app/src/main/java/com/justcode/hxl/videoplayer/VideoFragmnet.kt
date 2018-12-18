package com.justcode.hxl.videoplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.fragment_video.*
import android.support.v7.widget.RecyclerView
import lucky_videoview.LuckyFrameLayout


class VideoFragmnet : Fragment() {
    var text: String = ""
    var currentPosition = -1
    var list: MutableList<String> = arrayListOf()
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
        list.add("https://city.fuwugongshe.com:8083/video/fmfw.mp4")


        val adapter = VideoAdapter(context!!, list)
        recycleview.adapter = adapter

        adapter.itemClick = {
            currentPosition = it
        }

        recycleview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView!!.layoutManager
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager is LinearLayoutManager) {
                    val linearManager = layoutManager
                    //获取最后一个可见view的位置
                    val lastItemPosition = linearManager.findLastVisibleItemPosition()
                    //获取第一个可见view的位置
                    val firstItemPosition = linearManager.findFirstVisibleItemPosition()
                    //获取可见view的总数
                    val visibleItemCount = linearManager.childCount
                    Log.d("currentPosition:", "currentPosition" + ":" + currentPosition+" firstItemPosition:"+firstItemPosition+" lastItemPosition:"+lastItemPosition)
                    if (currentPosition < firstItemPosition || currentPosition > lastItemPosition) {
                        //释放资源
                        LuckyFrameLayout.releaseAllVideos()
                    }

                }
            }
        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d("onHiddenChanged", text + ":" + hidden)
        if (hidden) {
            //释放资源
            LuckyFrameLayout.releaseAllVideos()
        }
    }

    override fun onStop() {
        super.onStop()
       //释放资源
        LuckyFrameLayout.releaseAllVideos()
    }
}