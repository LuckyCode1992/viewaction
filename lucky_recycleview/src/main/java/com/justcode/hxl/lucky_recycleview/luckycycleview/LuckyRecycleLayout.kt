package com.justcode.hxl.lucky_recycleview.luckycycleview

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.justcode.hxl.lucky_recycleview.R
import kotlinx.android.synthetic.main.recycleview_layout.view.*
import me.drakeet.multitype.ItemViewBinder
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.OneToManyFlow

class LuckyRecycleLayout : FrameLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val adapter: MultiTypeAdapter = MultiTypeAdapter()
    var recyclerView: RecyclerView? = null
    private val allItemList = mutableListOf<Any>()
    var emptyView: View? = null
        set(value) {
            field = value
            notifyItems()
        }
    var items = mutableListOf<Any>()
        set(value) {
            field = value
            notifyItems()
        }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val view = LayoutInflater.from(context).inflate(R.layout.recycleview_layout, this, true)
        recyclerView = view.findViewById(R.id.recycleview)
        recycleview.layoutManager = LinearLayoutManager(context)
        emptyView = defaultEmptyLayoutCreator?.invoke()
    }

    companion object {
        var defaultEmptyLayoutCreator: (() -> View)? = null
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        recycleview.layoutManager = layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanSizeLookup = layoutManager.spanSizeLookup
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = adapter.items[position]
                    return if (viewType is View) {
                        layoutManager.spanCount
                    } else {
                        spanSizeLookup?.getSpanSize(position) ?: 1
                    }
                }
            }
        }
    }

    private fun notifyItems() {
        allItemList.clear()
        if (items.isEmpty()) {
            emptyView?.let { allItemList.add(it) }
        }
        allItemList.addAll(items)
        adapter.items = allItemList
        recycleview.adapter?.notifyDataSetChanged() ?: run {
            recycleview.adapter = adapter
        }
    }

    fun <T> register(clazz: Class<out T>, binder: ItemViewBinder<T, *>) {
        adapter.register(clazz, binder)
    }

    fun <T> register(clazz: Class<out T>): OneToManyFlow<T> {
        return adapter.register(clazz)
    }
}