package com.justcode.hxl.luckyrecycleview

import com.justcode.hxl.lucky_recycleview.luckycycleview.BaseItemBinder
import com.justcode.hxl.lucky_recycleview.luckycycleview.ViewHolder
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.item2.view.*

class ItemBinder2 : BaseItemBinder<Item2>() {
    override var layout: Int
        get() = R.layout.item2
        set(value) {}

    override fun onBindViewHolder(holder: ViewHolder, item: Item2) {
        with(holder.itemView) {
            iv.setImageResource(item.imageUrl!!)
            tv_title.text = item.item22.title
            tv_content.text = item.item22.content

            setOnClickListener {
                itemClick.invoke(item.item22.title!!)
            }
        }
    }

}

data class Item2(var imageUrl: Int? = R.drawable.kefu_2_pdf, var item22:Item22)
data class Item22(var title: String? = "标题", var content: String? = "内容")