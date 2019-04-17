package com.justcode.hxl.zidingyi.ad_viewpager

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_ad.*
import android.widget.LinearLayout
import android.widget.Toast
import com.justcode.hxl.zidingyi.R


class AdActivity : AppCompatActivity() {
    val imageList = ArrayList<ImageView>()
    val imgList = arrayOf(R.drawable.image1_zdy, R.drawable.image2_zdy, R.drawable.image3__zdy, R.drawable.image4_zdy)
    var currentPoint = 0
    var currentPointRel = 0
    var handler: Handler? = null
    var leftOrRight = 1//1是往左滑动，-1是往右滑动
    val runnable = Runnable {
        val item = ad_view.currentItem + leftOrRight
        ad_view.currentItem = item
        zidong()
    }

    fun dp2px(dpValue: Float): Int {
        val scale = this.getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad)
        handler = Handler()
        imgList.forEach {
            val imageView = ImageView(this)
            imageView.adjustViewBounds = true
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setImageResource(it)
            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            imageView.layoutParams = layoutParams
            imageList.add(imageView)

            //添加点 point
            val point = View(this)
            val layoutParamsPoint = LinearLayout.LayoutParams(ViewGroup.LayoutParams(dp2px(10f), dp2px(10f)))
            layoutParamsPoint.gravity = Gravity.CENTER
            layoutParamsPoint.leftMargin = dp2px(10f)
            layoutParamsPoint.rightMargin = dp2px(10f)
            point.layoutParams = layoutParamsPoint
            point.setBackgroundResource(R.drawable.circle_selector)
            point.isEnabled = false
            ll_point.addView(point)

        }
        ll_point.getChildAt(0)?.let {
            it.isEnabled = true
        }


        //设置适配器
        ad_view.adapter = object : PagerAdapter() {


            /**
             * 比较 view和obj是不是同一个实例
             * view是这个页面
             * object是指instantiateItem返回的结果
             */
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            /**
             * 相当于getView方法
             * container:质是viewPager 本身
             * 创建
             *
             */
            @SuppressLint("ClickableViewAccessibility")
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                //左右无限滚动
                val realPosition = position % imageList.size

                val imageView = imageList[realPosition]
                //要用add的方式添加view进去
                container.addView(imageView)

                //按住不动
                imageView.setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            handler?.removeCallbacks(runnable)
                        }
                        MotionEvent.ACTION_MOVE -> {

                        }
                        MotionEvent.ACTION_CANCEL -> {

                        }
                        MotionEvent.ACTION_UP -> {
                            handler?.removeCallbacks(runnable)
                            zidong()
                        }
                    }
                    return@setOnTouchListener false
                }

                imageView.setTag(realPosition)
                imageView.setOnClickListener {
                    val index = imageView.getTag() as Int

                    Toast.makeText(this@AdActivity, "index:" + index, Toast.LENGTH_LONG).show()
                }
                return imageView
            }

            override fun getCount(): Int {
//                return imageList.size
                return Int.MAX_VALUE
            }

            /**
             * 销毁 最多存在3个view，多余会优先销毁靠前面的
             *
             */
            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//                super.destroyItem(container, position, `object`)
                container.removeView(`object` as View)
            }
        }

        //设置中间位置
        //这里就是计算出要设置的位置，目的得到一个imageList.size的倍数，只有是这个的倍数才能从第一张开始
        //这里要配合instantiateItem中的方法思考
        val item = Int.MAX_VALUE / 2 - Int.MAX_VALUE / 2 % imageList.size
        ad_view.currentItem = item


        //viewpager改变监听
        ad_view.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            /**
             * 页面状态变化时，回掉
             * 静止-》滚动  滚动-》静止 静止-》拖拽
             * state
             */
            override fun onPageScrollStateChanged(state: Int) {
                //拖拽状态
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    handler?.removeCallbacks(runnable)
                    //滑动后自然沉降的状态
                } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    handler?.removeCallbacks(runnable)
                    zidong()
                    //静止状态
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {

                }
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
                //通过当前位置和滚动后的位置比较是左滑动还是右滑动了
                if (position >= currentPointRel) {
                    leftOrRight = 1
                } else {
                    leftOrRight = -1
                }

                currentPointRel = position


                //左右无限滚动
                val realPosition = position % imageList.size

                ll_point.getChildAt(currentPoint).isEnabled = false
                ll_point.getChildAt(realPosition).isEnabled = true
                currentPoint = realPosition
            }

        })

        zidong()
    }

    fun zidong() {
        handler?.postDelayed(runnable, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(runnable)

    }
}
