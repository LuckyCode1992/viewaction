package com.justcode.hxl.videoplayer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.item_video.view.*
import lucky_videoview.LuckyFrameLayout


class VideoAdapter(var context: Context, var list: MutableList<String>) : RecyclerView.Adapter<MyViewHoder>() {

    var itemClick: (Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoder {
        var holder = MyViewHoder(LayoutInflater.from(context).inflate(R.layout.item_video, parent, false))
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHoder, position: Int) {
        with(holder.itemView) {
            videoplayer.setUp(
                    list[position],
                    "标题", LuckyFrameLayout.SCREEN_WINDOW_LIST)
            Glide.with(context).load("http://jzvd-pic.nathen.cn/jzvd-pic/bd7ffc84-8407-4037-a078-7d922ce0fb0f.jpg").into(videoplayer.thumbImageView)
            videoplayer.setOnitemClick {
                itemClick.invoke(position)
            }

        }
    }

}

class MyViewHoder(view: View) : RecyclerView.ViewHolder(view)