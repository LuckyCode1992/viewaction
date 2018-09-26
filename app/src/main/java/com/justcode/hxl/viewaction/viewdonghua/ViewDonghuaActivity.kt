package com.justcode.hxl.viewaction.viewdonghua

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_view_donghua.*

class ViewDonghuaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_donghua)

        val picData = arrayListOf(
                "测试数据12345678910",
                "测试数据12345678910",
                "测试数据12345678910",
                "测试数据12345678910",
                "测试数据12345678910",
                "测试数据12345678910",
                "测试数据12345678910",
                "测试数据12345678910",
                "测试数据12345678910"

        )

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        var adapter = Myadapter(picData, this)
        recycleview.layoutManager = layoutManager
        recycleview.adapter = adapter
    }
}
