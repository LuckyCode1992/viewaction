package com.justcode.hxl.videoplayer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.justcode.hxl.viewaction.R


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


        }
    }

}

class MyViewHoder(view: View) : RecyclerView.ViewHolder(view)