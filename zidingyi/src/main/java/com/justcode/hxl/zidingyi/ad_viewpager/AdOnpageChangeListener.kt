package com.justcode.hxl.zidingyi.ad_viewpager

import android.support.v4.view.ViewPager


open class AdOnpageChangeListener: ViewPager.OnPageChangeListener {

    /**
     * 页面状态变化时，回掉
     * 静止-》滚动  滚动-》静止 静止-》拖拽
     * state
     */
    override fun onPageScrollStateChanged(state: Int) {

    }

    //页面滚动了的时候回掉
    //position 当前位置
    //positionOffset 滑动的百分比
    //positionOffsetPixels 滑动的像素
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    /**
     * 当某个页面被选中的时候调用
     * position 选中的页面
     */
    override fun onPageSelected(position: Int) {
    }

}