package com.justcode.hxl.tools.点赞.animator;

import android.animation.ValueAnimator;
import android.graphics.Canvas;

import com.justcode.hxl.tools.点赞.Ease;
import com.justcode.hxl.tools.点赞.EasingInterpolator;
import com.justcode.hxl.tools.点赞.ShineView;

/**
 * @author vondear
 * @date 2016/7/5 下午5:09
 */
public class ShineAnimator extends ValueAnimator {

    float MAX_VALUE = 1.5f;
    long ANIM_DURATION = 1500;
    Canvas canvas;

    public ShineAnimator() {
        setFloatValues(1f, MAX_VALUE);
        setDuration(ANIM_DURATION);
        setStartDelay(200);
        setInterpolator(new EasingInterpolator(Ease.QUART_OUT));
    }

    public ShineAnimator(long duration, float maxValue, long delay) {
        setFloatValues(1f, maxValue);
        setDuration(duration);
        setStartDelay(delay);
        setInterpolator(new EasingInterpolator(Ease.QUART_OUT));
    }

    public void startAnim(final ShineView shineView, final int centerAnimX, final int centerAnimY) {

        start();
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

}
