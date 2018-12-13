package com.justcode.hxl.luckyrecycleview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.widget.Toast
import com.justcode.hxl.lucky_recycleview.smartrefresh.layout.SmartRefreshLayout
import com.justcode.hxl.lucky_recycleview.smartrefresh.layout.footer.ClassicsFooter
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_recycle_view.*

class RecycleViewActivity : AppCompatActivity() {

    val handler by lazy {
        Handler()
    }
    val itemBinder1 by lazy {
        ItemBinder1()
    }
    val itemBinder2 by lazy {
        ItemBinder2()
    }

    val itemList = arrayListOf<Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        //设置刷新的头部效果和底部的加载效果
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            LoadingHeader(LayoutInflater.from(this).inflate(R.layout.layout_loading, null, false))
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context)
        }
        //刷新和加载的回调用
        reflush_layout.setOnRefreshListener {

            handler.postDelayed({
                updateItem()
                it.finishRefresh()
            }, 500)

        }
        reflush_layout.setOnLoadMoreListener {
            handler.postDelayed({
                //不演示了
                it.finishLoadMore()
            }, 500)

        }
        //初始化recycleview
        val layout = LinearLayoutManager(this)
        layout.orientation = LinearLayoutManager.VERTICAL
        lucky_recycle.setLayoutManager(layout)
        lucky_recycle.register(Item1::class.java, itemBinder1)
        lucky_recycle.register(Item2::class.java, itemBinder2)

        itemBinder1.itemClick = {
           val position = it as Int
            Toast.makeText(this, "点击了$position", Toast.LENGTH_LONG).show()
        }

    }
    private fun updateItem() {
        itemList.clear()
        for (i in 0..15) {
            if (i % 3 == 0) {
                itemList.add(Item1("name:hello$i", "age:1$i"))
            } else {
                itemList.add(Item2(R.drawable.kefu_2_pdf, Item22("title:标题$i", "content:内容$i")))
            }
        }
        lucky_recycle.items = itemList
    }
}
