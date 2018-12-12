package com.justcode.hxl.luckyrecycleview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.justcode.hxl.lucky_recycleview.smartrefresh.layout.SmartRefreshLayout
import com.justcode.hxl.lucky_recycleview.smartrefresh.layout.footer.ClassicsFooter
import com.justcode.hxl.viewaction.R

class RecycleViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            LoadingHeader(LayoutInflater.from(this).inflate(R.layout.layout_loading, null, false))
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context)
        }
    }
}
