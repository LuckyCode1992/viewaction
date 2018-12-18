package com.justcode.hxl.CoordinatorLayout折叠式布局

import android.graphics.Color
import com.justcode.hxl.lucky_recycleview.luckycycleview.BaseItemBinder
import com.justcode.hxl.lucky_recycleview.luckycycleview.ViewHolder
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.item_title.view.*

class TitleItemBinder : BaseItemBinder<String>() {
    var titleitemClick: (Int) -> Unit = {}
    var currentPosition = 0
    override var layout: Int
        get() = R.layout.item_title
        set(value) {}

    override fun onBindViewHolder(holder: ViewHolder, item: String) {
        with(holder.itemView) {
            tv_title.text = item

            if (currentPosition == holder.adapterPosition) {
                tv_title.setTextColor(Color.BLUE)
            } else {
                tv_title.setTextColor(Color.BLACK)
            }
            setOnClickListener {
                titleitemClick.invoke(holder.adapterPosition)

            }

        }
    }

}