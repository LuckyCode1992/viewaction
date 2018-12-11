package com.justcode.hxl.videoplayer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.justcode.hxl.video_player.TxVideoPlayerController
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.item_video.view.*

class VideoAdapter(var context: Context, var list: MutableList<String>) : RecyclerView.Adapter<MyViewHoder>() {
    var controller: TxVideoPlayerController? = null
    var itemClick: (Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoder {
        var holder = MyViewHoder(LayoutInflater.from(context).inflate(R.layout.item_video, parent, false))
        controller = TxVideoPlayerController(context)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHoder, position: Int) {
        with(holder.itemView) {
            controller?.let {
                view_player.setController(it)
                it.setTitle("demo")
            }

            // 将列表中的每个视频设置为默认16:9的比例
            val params = view_player.getLayoutParams()
            params.width = getResources().getDisplayMetrics().widthPixels // 宽度为屏幕宽度
            params.height = (params.width * 9f / 16f).toInt()    // 高度为宽度的9/16
            view_player.setLayoutParams(params)

            view_player.setUp(list[position], null)

            controller?.setOnStartListener {
                itemClick.invoke(position)
            }

        }
    }

}

class MyViewHoder(view: View) : RecyclerView.ViewHolder(view)