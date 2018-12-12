package com.justcode.hxl.luckyrecycleview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.justcode.hxl.lucky_recycleview.smartrefresh.layout.api.RefreshHeader
import com.justcode.hxl.lucky_recycleview.smartrefresh.layout.internal.InternalAbstract
import kotlinx.android.synthetic.main.layout_loading.view.*


class LoadingHeader : InternalAbstract, RefreshHeader {

    constructor(wrapper: View) : super(wrapper)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight)
        if (percent > 0) {
            if (view.loading_view.isAnimating.not()) {
                view.loading_view.playAnimation()
            }
        } else {
            view.loading_view.cancelAnimation()
        }
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

}