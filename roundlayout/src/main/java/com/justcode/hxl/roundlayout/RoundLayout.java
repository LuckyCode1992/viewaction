package com.justcode.hxl.roundlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.justcode.hxl.roundlayout.helper.Attrs;
import com.justcode.hxl.roundlayout.helper.Helper;

public class RoundLayout extends FrameLayout implements Attrs {
    Helper mHelper;
    public RoundLayout(@NonNull Context context) {
        this(context, null);
    }

    public RoundLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = new Helper();
        mHelper.initAttrs(context, attrs);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHelper.onSizeChanged(this, w, h);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(mHelper.mLayer, null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        mHelper.onClipDraw(canvas);
        canvas.restore();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mHelper.mClipBackground) {
            canvas.save();
            canvas.clipPath(mHelper.mClipPath);
            super.draw(canvas);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }

    @Override
    public void invalidate() {
        if (null != mHelper)
            mHelper.refreshRegion(this);
        super.invalidate();
    }

    @Override
    public void setClipBackground(boolean clipBackground) {
        mHelper.mClipBackground = clipBackground;
        invalidate();
    }

    @Override
    public void setRoundAsCircle(boolean roundAsCircle) {
        mHelper.mRoundAsCircle = roundAsCircle;
        invalidate();
    }

    @Override
    public void setRadius(int radius) {
        for (int i = 0; i < mHelper.radii.length; i++) {
            mHelper.radii[i] = radius;
        }
        invalidate();
    }

    @Override
    public void setTopLeftRadius(int topLeftRadius) {
        mHelper.radii[0] = topLeftRadius;
        mHelper.radii[1] = topLeftRadius;
        invalidate();
    }

    @Override
    public void setTopRightRadius(int topRightRadius) {
        mHelper.radii[2] = topRightRadius;
        mHelper.radii[3] = topRightRadius;
        invalidate();
    }

    @Override
    public void setBottomLeftRadius(int bottomLeftRadius) {
        mHelper.radii[6] = bottomLeftRadius;
        mHelper.radii[7] = bottomLeftRadius;
        invalidate();
    }

    @Override
    public void setBottomRightRadius(int bottomRightRadius) {
        mHelper.radii[4] = bottomRightRadius;
        mHelper.radii[5] = bottomRightRadius;
        invalidate();
    }

    @Override
    public void setStrokeWidth(int strokeWidth) {
        mHelper.mStrokeWidth = strokeWidth;
        invalidate();
    }

    @Override
    public void setStrokeColor(int strokeColor) {
        mHelper.mStrokeColor = strokeColor;
        invalidate();
    }

    @Override
    public boolean isClipBackground() {
        return mHelper.mClipBackground;
    }

    @Override
    public boolean isRoundAsCircle() {
        return mHelper.mRoundAsCircle;
    }

    public float getTopLeftRadius() {
        return mHelper.radii[0];
    }

    public float getTopRightRadius() {
        return mHelper.radii[2];
    }

    public float getBottomLeftRadius() {
        return mHelper.radii[4];
    }

    public float getBottomRightRadius() {
        return mHelper.radii[6];
    }

    public int getStrokeWidth() {
        return mHelper.mStrokeWidth;
    }

    public int getStrokeColor() {
        return mHelper.mStrokeColor;
    }
}
