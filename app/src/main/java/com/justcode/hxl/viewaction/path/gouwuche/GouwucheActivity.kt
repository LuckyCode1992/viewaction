package com.justcode.hxl.viewaction.path.gouwuche

import android.animation.Animator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.justcode.hxl.viewaction.R

import kotlinx.android.synthetic.main.activity_gouwuche.*
import android.graphics.PointF
import android.widget.ImageView
import android.animation.ValueAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.ScaleAnimation


class GouwucheActivity : AppCompatActivity() {
    var list: MutableList<ShopData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gouwuche)

        list.add(ShopData(10, "牛奶"))
        list.add(ShopData(20, "咖啡"))
        list.add(ShopData(17, "面包"))
        list.add(ShopData(17, "油条"))
        list.add(ShopData(12, "豆浆"))
        list.add(ShopData(14, "花生"))
        list.add(ShopData(16, "瓜子"))
        list.add(ShopData(10, "馒头"))
        list.add(ShopData(15, "包子"))
        list.add(ShopData(15, "饺子"))
        list.add(ShopData(17, "小面"))
        list.add(ShopData(10, "牛奶"))
        list.add(ShopData(10, "牛奶"))
        list.add(ShopData(10, "牛奶"))
        list.add(ShopData(10, "牛奶"))
        list.add(ShopData(10, "牛奶"))
        list.add(ShopData(10, "牛奶"))
        list.add(ShopData(10, "牛奶"))
        list.add(ShopData(10, "牛奶"))
        list.add(ShopData(10, "牛奶"))

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recycleview.layoutManager = layoutManager
        recycleview.adapter = GouwucheAdapter(list, this).setOnAddClickListener(object : OnAddClickListener {
            override fun onClick(position: Int, view: View) {
                //贝塞尔起始数据点
                val startPosition = IntArray(2)
                //贝塞尔结束数据点
                val endPosition = IntArray(2)
                //控制点
                val recyclerPosition = IntArray(2)

                /**
                 * 获取相对父控件的坐标
                 */
                view.getLocationInWindow(startPosition)
                shopping_cart.getLocationInWindow(endPosition)
                recycleview.getLocationInWindow(recyclerPosition)

                val startF = PointF()
                val endF = PointF()
                val controllF = PointF()


                startF.x = startPosition[0].toFloat()
                startF.y = startPosition[1].toFloat() - recyclerPosition[1]
                endF.x = endPosition[0].toFloat()
                endF.y = endPosition[1].toFloat() - recyclerPosition[1]
                controllF.x = endF.x
                controllF.y = startF.y

                val imageView = ImageView(this@GouwucheActivity)
                main_layout.addView(imageView)
                imageView.setImageResource(R.mipmap.ic_add_circle_blue_700_36dp)
                imageView.getLayoutParams().width = view.width
                imageView.getLayoutParams().height = view.height
                imageView.visibility = View.VISIBLE
                imageView.x = startF.x
                imageView.y = startF.y


                val valueAnimator = ValueAnimator.ofObject(BezierTypeEvaluator(controllF), startF, endF)
                valueAnimator.addUpdateListener { animation ->
                    val pointF = animation.animatedValue as PointF
                    imageView.x = pointF.x
                    imageView.y = pointF.y
                }
                valueAnimator.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        imageView.visibility = View.GONE
                        main_layout.removeView(imageView)
                    }

                })
                valueAnimator.duration = 800
                valueAnimator.start()

                val sacleAnimation = ScaleAnimation(0.6f, 1f, 0.6f, 1f, (shopping_cart.width / 2).toFloat(), (shopping_cart.height / 2).toFloat())
                sacleAnimation.duration = 800
                sacleAnimation.interpolator = AccelerateInterpolator()
                shopping_cart.startAnimation(sacleAnimation)
            }

        })

    }

}


