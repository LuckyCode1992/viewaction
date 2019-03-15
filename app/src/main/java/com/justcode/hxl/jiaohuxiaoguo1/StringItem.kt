package com.justcode.hxl.jiaohuxiaoguo1

import com.justcode.hxl.lucky_recycleview.luckycycleview.BaseItemBinder
import com.justcode.hxl.lucky_recycleview.luckycycleview.ViewHolder
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.item_string.view.*

class StringItem() :BaseItemBinder<String>(){
    override var layout: Int
        get() = R.layout.item_string
        set(value) {}
    override fun onBindViewHolder(holder: ViewHolder, item: String) {
        with(holder.itemView){
            tv_string.text = item
        }
    }

}