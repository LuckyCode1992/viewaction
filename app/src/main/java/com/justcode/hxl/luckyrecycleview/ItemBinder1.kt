package com.justcode.hxl.luckyrecycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.justcode.hxl.lucky_recycleview.luckycycleview.BaseItemBinder
import com.justcode.hxl.lucky_recycleview.luckycycleview.ViewHolder
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.item1.view.*

class ItemBinder1 : BaseItemBinder<Item1>() {
    override var layout: Int
        get() = R.layout.item1
        set(value) {}

    override fun onBindViewHolder(holder: ViewHolder, item: Item1) {
        val currentPosition = holder.adapterPosition
        with(holder.itemView) {
            tv_name.text = item.name
            tv_age.text = item.age
            setOnClickListener {
                itemClick.invoke(currentPosition)
            }

            swip_item.isSwipeEnable = true

            left_menu.setOnClickListener {
                Toast.makeText(context, "点击了左边", Toast.LENGTH_LONG).show()
                swip_item.close()
            }
            right_menu.setOnClickListener({
                Toast.makeText(context, "点击了右边", Toast.LENGTH_LONG).show()
                swip_item.close()
            })
        }
    }

}


data class Item1(var name: String? = "hello", var age: String? = "20")