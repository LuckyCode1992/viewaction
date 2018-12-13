package com.justcode.hxl.lucky_recycleview.luckycycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import me.drakeet.multitype.ItemViewBinder

abstract class BaseItemBinder<T> : ItemViewBinder<T, ViewHolder>() {
    abstract var layout: Int
    var itemClick: (Any) -> Unit = {}
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false))
    }

    abstract override fun onBindViewHolder(holder: ViewHolder, item: T)
}