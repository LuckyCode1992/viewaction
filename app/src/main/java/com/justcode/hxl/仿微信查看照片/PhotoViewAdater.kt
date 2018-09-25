package com.justcode.hxl.仿微信查看照片

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.justcode.hxl.viewaction.R
import com.wanglu.photoviewerlibrary.PhotoViewer
import kotlinx.android.synthetic.main.adapter_photoviewer_item.view.*

class PhotoViewAdater(var list: MutableList<String>, var context: Context) : RecyclerView.Adapter<PhotoViewAdater.ViewHolder>() {

    var itemClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var myViewHolder: ViewHolder? = null
        var view = LayoutInflater.from(context).inflate(R.layout.adapter_photoviewer_item, parent, false)
        myViewHolder = ViewHolder(view)
        return myViewHolder
    }

    override fun getItemCount(): Int {
        return list?.size!!.toInt()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            Glide.with(context).load(list[position]).into(iv_tucao_img)
        }

        holder.itemView.setOnClickListener {
            itemClick?.invoke(position)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}