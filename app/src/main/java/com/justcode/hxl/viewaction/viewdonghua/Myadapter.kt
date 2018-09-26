package com.justcode.hxl.viewaction.viewdonghua

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.item_myadapter.view.*

class Myadapter(var list: MutableList<String>, var context: Context) : RecyclerView.Adapter<Myadapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var myViewHolder: ViewHolder? = null
        var view = LayoutInflater.from(context).inflate(R.layout.item_myadapter, parent, false)
        myViewHolder = ViewHolder(view)
        return myViewHolder
    }

    override fun getItemCount(): Int {
        return list?.size!!.toInt()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            tv.text = list[0]
    }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}