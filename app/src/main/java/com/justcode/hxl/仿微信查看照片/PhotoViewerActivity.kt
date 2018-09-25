package com.justcode.hxl.仿微信查看照片

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.justcode.hxl.viewaction.R
import com.wanglu.photoviewerlibrary.PhotoViewer
import kotlinx.android.synthetic.main.activity_photo_viewer.*

class PhotoViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_viewer)

        val picData = arrayListOf(
                "https://ws1.sinaimg.cn/large/0065oQSqly1fvexaq313uj30qo0wldr4.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqly1fv5n6daacqj30sg10f1dw.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqly1fuo54a6p0uj30sg0zdqnf.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqly1fuh5fsvlqcj30sg10onjk.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg",
                "https://ww1.sinaimg.cn/large/0065oQSqly1fu7xueh1gbj30hs0uwtgb.jpg",
                "https://ww1.sinaimg.cn/large/0065oQSqgy1fu39hosiwoj30j60qyq96.jpg",
                "https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg",
                "https://ww1.sinaimg.cn/large/0065oQSqgy1ftwcw4f4a5j30sg10j1g9.jpg"

        )

        val layoutManager = GridLayoutManager(this, 3)
        var adapter = PhotoViewAdater(picData, this)
        recycleview.layoutManager = layoutManager
        recycleview.adapter = adapter

        adapter.itemClick = {
            PhotoViewer
                    .setData(picData)
                    .setCurrentPage(it)
                    .setImgContainer(recycleview)
                    .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
                        override fun show(iv: ImageView, url: String) {
                            Glide.with(iv.context).load(url).into(iv)
                        }
                    })
                    .start(this)
        }

    }
}
