package com.justcode.hxl.viewaction.path.gouwuche

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.gouwuche_item.view.*


class GouwucheAdapter(var list: MutableList<ShopData>, var context: Context) : RecyclerView.Adapter<MyViewHoder>() {
    var listener: OnAddClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoder {
        return MyViewHoder(LayoutInflater.from(context).inflate(R.layout.gouwuche_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHoder, position: Int) {
        with(holder.itemView) {
            tv_title.text = list[position].title
            tv_count.text = list[position].count.toString()
            iv_add.setOnClickListener {
                listener?.onClick(position,iv_add)
            }
        }
    }

    fun setOnAddClickListener(listener: OnAddClickListener): GouwucheAdapter {
        this.listener = listener
        return this
    }

}

class MyViewHoder(itemView: View) : RecyclerView.ViewHolder(itemView)


interface OnAddClickListener {
    fun onClick(position: Int,view:View)
}