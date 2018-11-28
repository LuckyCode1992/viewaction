package com.justcode.hxl.chelaile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_che_laile.*

class CheLaileActivity : AppCompatActivity() {
    var list: MutableList<Data> = arrayListOf()
    val myAdapter by lazy {
        CheLaileAdapter(this, list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_che_laile)


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycleview.layoutManager = layoutManager
        recycleview.adapter = myAdapter


        var data1 = Data()
        data1.name = "重庆大学"
        data1.isStart = true
        list.add(data1)

        var data2 = Data()
        data2.name = "重庆交通大学"
        list.add(data2)

        var data3 = Data()
        data3.name = "重庆邮电大学"
        data3.isSmallCar = true
        list.add(data3)

        var data4 = Data()
        data4.name = "四川美术学院"
        list.add(data4)

        var data5 = Data()
        data5.name = "重庆师范大学"
        list.add(data5)

        var data6 = Data()
        data6.name = "上海复旦大学"
        data6.isBigCar = true
        list.add(data6)

        var data7 = Data()
        data7.name = "清华"
        data7.isLocation = true
        list.add(data7)

        var data8 = Data()
        data8.name = "哈尔滨工业大学"
        list.add(data8)

        var data9 = Data()
        data9.name = "麻省理工学院"
        list.add(data9)

        var data10 = Data()
        data10.name = "重庆理工大学"
        data10.isEnd = true
        list.add(data10)

        myAdapter.update(list)

        myAdapter.itemClick = {
            recycleview.scrollToPosition(it)
            val mLayoutManager = recycleview.getLayoutManager() as LinearLayoutManager
            mLayoutManager.scrollToPositionWithOffset(it, 0)

        }
    }
}
