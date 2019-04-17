package com.justcode.hxl.CoordinatorLayout折叠式布局

import com.justcode.hxl.lucky_recycleview.luckycycleview.BaseItemBinder
import com.justcode.hxl.lucky_recycleview.luckycycleview.ViewHolder
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.item_coordinator.view.*
import java.util.*

class CoordinatorItemBinder : BaseItemBinder<String>() {

    val images = arrayListOf<Int>(R.drawable.image1_zdy, R.drawable.image2_zdy, R.drawable.image3__zdy, R.drawable.image4_zdy, R.drawable.image5, R.drawable.image6, R.drawable.lucky_img)
    override var layout: Int
        get() = R.layout.item_coordinator
        set(value) {}

    override fun onBindViewHolder(holder: ViewHolder, item: String) {
        with(holder.itemView) {
            var index = Random().nextInt(7)
            image.setImageResource(images[index])
        }
    }

}