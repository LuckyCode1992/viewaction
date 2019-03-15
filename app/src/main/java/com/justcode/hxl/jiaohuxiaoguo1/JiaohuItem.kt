package com.justcode.hxl.jiaohuxiaoguo1

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.justcode.hxl.lucky_recycleview.luckycycleview.BaseItemBinder
import com.justcode.hxl.lucky_recycleview.luckycycleview.ViewHolder
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.item_jiaohu.view.*

class JiaohuItem(var context: Context, var list: MutableList<String>) : BaseItemBinder<Integer>() {
    override var layout: Int
        get() = R.layout.item_jiaohu
        set(value) {}

    override fun onBindViewHolder(holder: ViewHolder, item: Integer) {
        with(holder.itemView) {
            //初始化recycleview
            val layout2 = LinearLayoutManager(context)
            val layout3 = LinearLayoutManager(context)

            layout2.orientation = LinearLayoutManager.VERTICAL
            layout3.orientation = LinearLayoutManager.VERTICAL

            lucky_recycle2.setLayoutManager(layout2)
            lucky_recycle3.setLayoutManager(layout3)


            lucky_recycle2.register(String::class.java, StringItem())
            lucky_recycle3.register(String::class.java, StringItem())


            lucky_recycle2.items = list.toMutableList()
            lucky_recycle3.items = list.toMutableList()
        }
    }

}