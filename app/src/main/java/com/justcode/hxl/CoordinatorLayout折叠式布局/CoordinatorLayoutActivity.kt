package com.justcode.hxl.CoordinatorLayout折叠式布局

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_coordinator_layout.*

class CoordinatorLayoutActivity : AppCompatActivity() {
    var luckyManager: LinearLayoutManager? = null
    var titleManger: LinearLayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout)

        val list: MutableList<String> = arrayListOf()
        for (i in 0..40) {
            list.add("hello" + i)
        }


        luckyManager = LinearLayoutManager(this)
        lucky_recycle.register(String::class.java, CoordinatorItemBinder())
        lucky_recycle.setLayoutManager(luckyManager!!)
        lucky_recycle.items = list.toMutableList()


        val titleItemBinder = TitleItemBinder()
        lucky_recycle_coordinator.register(String::class.java, titleItemBinder)
        titleManger = LinearLayoutManager(this)
        titleManger!!.orientation = LinearLayoutManager.HORIZONTAL
        lucky_recycle_coordinator.setLayoutManager(titleManger!!)
        lucky_recycle_coordinator.items = list.toMutableList()


        titleItemBinder.titleitemClick = {
            luckyManager?.scrollToPositionWithOffset(it, 0)
            luckyManager?.stackFromEnd = false
        }

        lucky_recycle_coordinator.recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val linearManager = recyclerView?.layoutManager as LinearLayoutManager
                //获取最后一个可见view的位置
                val lastItemPosition = linearManager.findLastVisibleItemPosition()
                //获取第一个可见view的位置
                val firstItemPosition = linearManager.findFirstVisibleItemPosition()

                luckyManager?.scrollToPositionWithOffset(firstItemPosition, 0)
                luckyManager?.stackFromEnd = false


            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

        })
        lucky_recycle.recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val linearManager = recyclerView?.layoutManager as LinearLayoutManager
                //获取最后一个可见view的位置
                val lastItemPosition = linearManager.findLastVisibleItemPosition()
                //获取第一个可见view的位置
                val firstItemPosition = linearManager.findFirstVisibleItemPosition()

                titleManger?.scrollToPositionWithOffset(firstItemPosition, 0)
                titleManger?.stackFromEnd = false
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }

        })


    }
}
