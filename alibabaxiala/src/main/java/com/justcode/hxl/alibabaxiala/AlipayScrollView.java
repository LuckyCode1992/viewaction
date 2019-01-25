package com.justcode.hxl.alibabaxiala;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import android.widget.ScrollView;


import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import java.lang.reflect.Field;

/**
 * Created by xmuSistone on 2018/12/25.
 */

public class AlipayScrollView extends ScrollView {

    private AlipayContainerLayout parentView;
    private int[] SLOW_DOWN_STEP = new int[7];
    private float lastProcessY;
    private int downTouchOffset = 0;
    private Spring marginSpring, scrollSpring;
    private View topLayout;

    private int progressColor;
    private ProgressImageView progressImageView;
    private int progressHeight, progressCenterOffset;
    private View firstChildView;
    private int firstViewPosition = 0;

    private boolean refreshing = false;
    private OnRefreshListener onRefreshListener;
    private ScrollChangeListener scrollChangeListener;

    private boolean flinging = false;
    private OverScroller overScroller;

    public AlipayScrollView(Context context) {
        this(context, null);
    }

    public AlipayScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlipayScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 1. xml配置信息获取
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.alipay);
        int defaultProgressHeight = dp2px(55);
        this.progressHeight = typedArray.getDimensionPixelSize(R.styleable.alipay_progressHeight, defaultProgressHeight);
        this.progressCenterOffset = typedArray.getDimensionPixelSize(R.styleable.alipay_progressCenterOffset, 0);
        this.progressColor = typedArray.getColor(R.styleable.alipay_progressColor, Color.BLACK);
        typedArray.recycle();

        // 2. 初始化越界拖拽阻力参数
        int step = dp2px(20);
        int initSlowDownThreshold = progressHeight;
        int index = 0;
        for (int i = SLOW_DOWN_STEP.length - 1; i >= 0; i--) {
            SLOW_DOWN_STEP[i] = initSlowDownThreshold + step * index;
            index++;
        }

        // 3. 松手时的动画，用margin来做
        SpringConfig springConfig = SpringConfig.fromOrigamiTensionAndFriction(3, 2);
        SpringSystem mSpringSystem = SpringSystem.create();
        marginSpring = mSpringSystem.createSpring().setSpringConfig(springConfig);
        marginSpring.setOvershootClampingEnabled(true);
        marginSpring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int yPos = (int) spring.getCurrentValue();
                setFirstViewPosition(yPos);
                onPositionChanged();
            }

            @Override
            public void onSpringAtRest(Spring spring) {
                super.onSpringAtRest(spring);

                if (firstViewPosition < progressHeight / 2) {
                    refreshing = false;
                    progressImageView.stopProgress();
                }
            }
        });

        // 4. snap停靠，属性动画用腻了，还是用spring吧
        scrollSpring = mSpringSystem.createSpring().setSpringConfig(springConfig);
        scrollSpring.setOvershootClampingEnabled(true);
        scrollSpring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int scrollY = (int) spring.getCurrentValue();
                setScrollY(scrollY);
            }
        });

        // 5. 反射获取scroller，这个是用来在computeScroll中判定ScrollView是否fling停止了，格外注意proguard不能混淆ScrollView
        try {
            Field scrollerField = ScrollView.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            this.overScroller = (OverScroller) scrollerField.get(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void computeScroll() {
        super.computeScroll();

        // 判定是否fling停止，用来判定是否需要snap的逻辑入口
        if (flinging && overScroller.isFinished()) {
            flinging = false;
            if (null != scrollChangeListener) {
                scrollChangeListener.onFlingStop();
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        parentView = (AlipayContainerLayout) getParent();
        topLayout = parentView.getTopLayout();
        progressImageView = parentView.getProgressImageView();
        progressImageView.setProgressColor(progressColor);
        firstChildView = ((ViewGroup) getChildAt(0)).getChildAt(0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        adjustTopLayoutPos();
        if (null != scrollChangeListener) {
            scrollChangeListener.onScrollChange(t);
        }
    }

    /**
     * ScrollView上下滑动时，topLayout需要跟随滑动
     */
    private void adjustTopLayoutPos() {
        int topLayoutTop = -getScrollY();
        if (topLayoutTop < -topLayout.getHeight()) {
            topLayoutTop = -topLayout.getHeight();
        } else if (topLayoutTop > 0) {
            topLayoutTop = 0;
        }
        final int destLayoutTop = topLayoutTop;
        topLayout.offsetTopAndBottom(destLayoutTop - topLayout.getTop());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            flinging = false;
            lastProcessY = ev.getRawY();
            downTouchOffset = refreshing ? firstViewPosition - progressHeight : firstViewPosition;
            // 手指按下，动画结束
            scrollSpring.setAtRest();
        } else if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            if (parentView.getTouchingView() != this) {
                ev.offsetLocation(0, downTouchOffset);
            }
            onDragRelease();
            super.onInterceptTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (parentView.getTouchingView() != this) {
                ev.offsetLocation(0, downTouchOffset);
            }
            super.onTouchEvent(ev);
        } else if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            flinging = true;
            if (parentView.getTouchingView() != this) {
                ev.offsetLocation(0, downTouchOffset);
            }
            onDragRelease();
            super.onTouchEvent(ev);
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            onTouchMove(ev);
        }
        return true;
    }

    /**
     * 专门抽出一个函数用来处理touch拖动
     */
    private void onTouchMove(MotionEvent ev) {
        int distance = (int) (ev.getRawY() - lastProcessY);
        if (getScrollY() == 0) {
            if (firstViewPosition == 0) {
                if (distance < 0) {
                    if (parentView.getTouchingView() != this) {
                        ev.offsetLocation(0, downTouchOffset);
                    }
                    super.onTouchEvent(ev);
                } else {
                    this.setFirstViewPosition(firstViewPosition + distance);
                    onPositionChanged();
                }
            } else {
                // 1. scroll在最顶部，继续向下拉时添加阻力。此段代码就是让移动的distance缩小，越偏离顶部，阻力越大
                int originDistance = distance;
                distance = shrinkDragDistance(distance);
                int newPosition = firstViewPosition + distance;
                if (progressImageView.isRunning()) {
                    // 正在动画
                    if (firstViewPosition == progressHeight) {
                        if (originDistance > 0) {
                            //  向下拉，直接改变firstView的位置
                            setFirstViewPosition(newPosition);
                        } else {
                            if (parentView.getTouchingView() != this) {
                                ev.offsetLocation(0, downTouchOffset);
                            }
                            super.onTouchEvent(ev);
                        }
                    } else {
                        if (newPosition < progressHeight) {
                            newPosition = progressHeight;
                        }
                        setFirstViewPosition(newPosition);
                    }
                } else {
                    // 动画停止
                    if (newPosition < 0) {
                        newPosition = 0;
                    }
                    setFirstViewPosition(newPosition);
                    onPositionChanged();
                }
            }
            lastProcessY = ev.getRawY();
        } else {
            lastProcessY = ev.getRawY();
            if (parentView.getTouchingView() != this) {
                ev.offsetLocation(0, downTouchOffset);
            }
            super.onTouchEvent(ev);
        }
    }

    /**
     * 拖动越界时，让距离缩水，以增加阻力；越界越多，阻力越大
     */
    private int shrinkDragDistance(int distance) {
        if (distance > 0) {
            int tempPosition = firstViewPosition + distance;
            if (tempPosition > SLOW_DOWN_STEP[0]) {
                distance = distance / 128;
            } else if (tempPosition > SLOW_DOWN_STEP[1]) {
                distance = distance / 64;
            } else if (tempPosition > SLOW_DOWN_STEP[2]) {
                distance = distance / 32;
            } else if (tempPosition > SLOW_DOWN_STEP[3]) {
                distance = distance / 16;
            } else if (tempPosition > SLOW_DOWN_STEP[4]) {
                distance = distance / 8;
            } else if (tempPosition > SLOW_DOWN_STEP[5]) {
                distance = distance / 4;
            } else if (tempPosition > SLOW_DOWN_STEP[6]) {
                distance = distance / 2;
            }
        }
        return distance;
    }

    private void onPositionChanged() {
        // 1. 进度
        float progress = (float) firstViewPosition / progressHeight;
        if (progress < 0) {
            progress = 0;
        } else if (progress > 1) {
            progress = 1;
        }

        if (!progressImageView.isRunning()) {
            progressImageView.setStartEndTrim(progress * progress, progress * progress * 2);
        }

        // 2. 缩放
        progressImageView.setPivotY(progressImageView.getHeight());
        progressImageView.setScaleX(progress);
        progressImageView.setScaleY(progress);

        // 3. 位移
        int originProgressPos = (progressHeight - progressImageView.getHeight()) / 2 + progressCenterOffset;
        int progressDestPosition = (int) (originProgressPos + (1 - progress) * progressImageView.getHeight() / 5);
        progressImageView.offsetTopAndBottom(progressDestPosition - progressImageView.getTop());

        // 4. 透明度
        float alpha = progress * 2;
        if (alpha > 1) {
            alpha = 1.0f;
        }
        progressImageView.setAlpha(alpha);
    }

    private void onDragRelease() {
        int top = firstViewPosition;
        if (top >= progressHeight) {
            progressImageView.startProgress();
            marginSpring.setAtRest();
            marginSpring.setCurrentValue(top);
            marginSpring.setEndValue(progressHeight);

            if (!refreshing) {
                refreshing = true;
                if (null != onRefreshListener) {
                    onRefreshListener.onRefresh();
                }
            }
        } else {
            refreshing = false;
            marginSpring.setAtRest();
            marginSpring.setCurrentValue(top);
            marginSpring.setEndValue(0);
        }
    }

    /**
     * 用margin的形式完成下拉位移
     */
    public void setFirstViewPosition(int firstViewPosition) {
        this.firstViewPosition = firstViewPosition;
        ViewGroup.LayoutParams lp = firstChildView.getLayoutParams();
        if (lp instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams castLp = (LinearLayout.LayoutParams) lp;
            castLp.topMargin = firstViewPosition;
            firstChildView.setLayoutParams(castLp);
        } else if (lp instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams castLp = (RelativeLayout.LayoutParams) lp;
            if (castLp.topMargin != firstViewPosition) {
                castLp.topMargin = firstViewPosition;
                firstChildView.setLayoutParams(castLp);
            }
        } else if (lp instanceof LayoutParams) {
            LayoutParams castLp = (LayoutParams) lp;
            if (castLp.topMargin != firstViewPosition) {
                castLp.topMargin = firstViewPosition;
                firstChildView.setLayoutParams(castLp);
            }
        }
    }

    public boolean isRefreshing() {
        return refreshing;
    }

    /**
     * 手动更新刷新状态
     */
    public void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
        this.marginSpring.setCurrentValue(firstViewPosition);
        if (refreshing) {
            progressImageView.startProgress();
            marginSpring.setEndValue(progressHeight);
        } else {
            marginSpring.setEndValue(0);
        }
    }

    /**
     * dp和像素转换
     */
    public int dp2px(float dipValue) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * density + 0.5f);
    }

    public int getProgressHeight() {
        return progressHeight;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setScrollChangeListener(ScrollChangeListener scrollChangeListener) {
        this.scrollChangeListener = scrollChangeListener;
    }

    public void updateProcessY(float rawY) {
        this.lastProcessY = rawY;
    }

    public void snapTo(int scrollY) {
        if (scrollY != getScrollY()) {
            scrollSpring.setCurrentValue(getScrollY());
            scrollSpring.setEndValue(scrollY);
        }
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface ScrollChangeListener {

        /**
         * 滑动
         */
        void onScrollChange(int scrollY);

        /**
         * 滑动结束监听
         */
        void onFlingStop();
    }
}
