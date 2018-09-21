package com.justcode.hxl.viewaction.path.gouwuche;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * 差值器
 */
public class BezierTypeEvaluator implements TypeEvaluator<PointF> {
    private PointF mControllPoint;

    public BezierTypeEvaluator(PointF mControllPoint) {
        this.mControllPoint = mControllPoint;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        PointF pointCur = new PointF();
        /**
         * 这是三阶贝塞尔曲线公式，就是二次函数的公式
         */
        pointCur.x = (1 - fraction) * (1 - fraction) * startValue.x + 2 * fraction * (1 - fraction) * mControllPoint.x + fraction * fraction * endValue.x;
        pointCur.y = (1 - fraction) * (1 - fraction) * startValue.y + 2 * fraction * (1 - fraction) * mControllPoint.y + fraction * fraction * endValue.y;
        return pointCur;
    }
}
