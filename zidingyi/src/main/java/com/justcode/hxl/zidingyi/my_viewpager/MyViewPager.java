package com.justcode.hxl.zidingyi.my_viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MyViewPager extends ViewGroup {

    /**
     * 手势识别器：
     * 1.定义出来
     * 2.实例化
     * 3.在onTounEvent中，把事件传递给手势识别器
     */

    private GestureDetector detector;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    private void initView(Context context) {
        scroller = new MyScroller();
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Log.d("GestureDetector_", "onLongPress");

            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d("GestureDetector_", "onScroll");
                //相对当前 distanceX 滑动的距离
                scrollBy((int) distanceX, getScrollY());
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d("GestureDetector_", "onDoubleTap");
                return super.onDoubleTap(e);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//遍历孩子，给每个孩子指定屏幕的坐标
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            child.layout(i * getWidth(), 0, (i + 1) * getHeight(), getHeight());
        }
    }

    private float startX, endX;
    private int currentIndex;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录坐标
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //2.得到新的坐标
                endX = event.getX();
                int tempIndex = currentIndex;
                if ((startX - endX) > getWidth() / 2) {
                    //显示下一个页面
                    tempIndex++;
                } else if ((endX - startX) > getWidth() / 2) {
                    //显示上一个页面
                    tempIndex--;
                }
                scrollToPager(tempIndex);
                break;
        }
        return true;
    }

    MyScroller scroller;

    /**
     * 屏蔽非法值
     *
     * @param tempIndex
     */
    private void scrollToPager(int tempIndex) {
        if (tempIndex < 0) {
            tempIndex = 0;
        }
        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }
        //当前的位置
        currentIndex = tempIndex;
        //移动到指定位置
        scrollTo(currentIndex * getWidth(), getScrollY());

//        int distancex = (int) (currentIndex * getWidth() * getScaleX());
//        scroller.startScroll(getScaleX(), getScaleY(), distancex, 0);
//
//        invalidate();
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
//        if (scroller.cuputeScrollOffset()) {
//            float currentx = scroller.getCurrentx();
//            scrollTo((int) currentx, 0);
//            invalidate();
//        }
    }
}
