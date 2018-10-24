package com.justcode.hxl.imagescale

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.justcode.hxl.tools.scaleimage.ImageSource
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_image_scale.*

class ImageScaleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_scale)
       // iv_scale.setImage(ImageSource.asset("girl.jpg"))
        Glide.with(this).asBitmap().load("https://ws1.sinaimg.cn/large/0065oQSqly1fuo54a6p0uj30sg0zdqnf.jpg").into(object :SimpleTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                iv_scale.setImage(ImageSource.bitmap(resource))
            }

        })




    }
}
