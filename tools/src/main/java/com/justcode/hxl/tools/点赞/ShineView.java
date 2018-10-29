package com.justcode.hxl.tools.点赞;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


import com.justcode.hxl.tools.点赞.animator.ShineAnimator;

import java.util.Random;

/**
 * @author vondear
 * @date 2016/7/5 下午3:57
 */
public class ShineView extends View {
    private static final String TAG = "ShineView";
    static int colorRandom[] = new int[10];
    //default 10ms ,change to 25ms for saving cpu.
    private static long FRAME_REFRESH_DELAY = 25;
    ShineAnimator mShineAnimator;
    ValueAnimator clickAnimator;
    ShineButton mShineButton;
    int colorCount = 10;
    //Customer property
    int shineCount;
    float smallOffsetAngle;
    float turnAngle;
    long animDuration;
    long clickAnimDuration;
    float shineDistanceMultiple;
    int smallShineColor = colorRandom[0];
    int bigShineColor = colorRandom[1];
    int shineSize = 0;
    boolean allowRandomColor = false;
    boolean enableFlashing = false;
    RectF rectF = new RectF();
    RectF rectFSmall = new RectF();
    Random random = new Random();
    int centerAnimX;
    int centerAnimY;
    int btnWidth;
    int btnHeight;
    double thirdLength;
    float value;
    float clickValue = 0;
    boolean isRun = false;
    private Paint paint;
    private Paint paint2;
    private Paint paintSmall;
    private float distanceOffset = 0.2f;


    public ShineView(Context context) {
        super(context);
    }

    public ShineView(Context context, final ShineButton shineButton, ShineParams shineParams) {
        super(context);


        initShineParams(shineParams, shineButton);


        this.mShineAnimator = new ShineAnimator(animDuration, shineDistanceMultiple, clickAnimDuration);
        ValueAnimator.setFrameDelay(FRAME_REFRESH_DELAY);
        this.mShineButton = shineButton;


        paint = new Paint();
        paint.setColor(bigShineColor);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setStrokeWidth(20);
        paint2.setStrokeCap(Paint.Cap.ROUND);

        paintSmall = new Paint();
        paintSmall.setColor(smallShineColor);
        paintSmall.setStrokeWidth(10);
        paintSmall.setStyle(Paint.Style.STROKE);
        paintSmall.setStrokeCap(Paint.Cap.ROUND);

        clickAnimator = ValueAnimator.ofFloat(0f, 1.1f);
        ValueAnimator.setFrameDelay(FRAME_REFRESH_DELAY);
        clickAnimator.setDuration(clickAnimDuration);
        clickAnimator.setInterpolator(new EasingInterpolator(Ease.QUART_OUT));
        clickAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                clickValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        clickAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                clickValue = 0;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mShineAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                shineButton.removeView(ShineView.this);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }


    public ShineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void showAnimation(ShineButton shineButton) {
        btnWidth = shineButton.getWidth();
        btnHeight = shineButton.getHeight();
        thirdLength = getThirdLength(btnHeight, btnWidth);
        int[] location = new int[2];
        shineButton.getLocationInWindow(location);
        centerAnimX = location[0] + btnWidth / 2;
        centerAnimY = getMeasuredHeight() - shineButton.getBottomHeight() + btnHeight / 2;
        mShineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                value = (float) valueAnimator.getAnimatedValue();
                if (shineSize != 0 && shineSize > 0) {
                    paint.setStrokeWidth((shineSize) * (shineDistanceMultiple - value));
                    paintSmall.setStrokeWidth(((float) shineSize / 3 * 2) * (shineDistanceMultiple - value));
                } else {
                    paint.setStrokeWidth((btnWidth / 2) * (shineDistanceMultiple - value));
                    paintSmall.setStrokeWidth((btnWidth / 3) * (shineDistanceMultiple - value));
                }


                rectF.set(centerAnimX - (btnWidth / (3 - shineDistanceMultiple) * value), centerAnimY - (btnHeight / (3 - shineDistanceMultiple) * value), centerAnimX + (btnWidth / (3 - shineDistanceMultiple) * value), centerAnimY + (btnHeight / (3 - shineDistanceMultiple) * value));
                rectFSmall.set(centerAnimX - (btnWidth / ((3 - shineDistanceMultiple) + distanceOffset) * value), centerAnimY - (btnHeight / ((3 - shineDistanceMultiple) + distanceOffset) * value), centerAnimX + (btnWidth / ((3 - shineDistanceMultiple) + distanceOffset) * value), centerAnimY + (btnHeight / ((3 - shineDistanceMultiple) + distanceOffset) * value));

                invalidate();
            }
        });
        mShineAnimator.startAnim(this, centerAnimX, centerAnimY);
        clickAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < shineCount; i++) {
            if (allowRandomColor) {
                paint.setColor(colorRandom[Math.abs(colorCount / 2 - i) >= colorCount ? colorCount - 1 : Math.abs(colorCount / 2 - i)]);
            }
            canvas.drawArc(rectF, 360f / shineCount * i + 1 + ((value - 1) * turnAngle), 0.1f, false, getConfigPaint(paint));
        }

        for (int i = 0; i < shineCount; i++) {
            if (allowRandomColor) {
                paint.setColor(colorRandom[Math.abs(colorCount / 2 - i) >= colorCount ? colorCount - 1 : Math.abs(colorCount / 2 - i)]);
            }
            canvas.drawArc(rectFSmall, 360f / shineCount * i + 1 - smallOffsetAngle + ((value - 1) * turnAngle), 0.1f, false, getConfigPaint(paintSmall));

        }
        paint.setStrokeWidth(btnWidth * (clickValue) * (shineDistanceMultiple - distanceOffset));
        if (clickValue != 0) {
            paint2.setStrokeWidth(btnWidth * (clickValue) * (shineDistanceMultiple - distanceOffset) - 8);
        } else {
            paint2.setStrokeWidth(0);
        }
        canvas.drawPoint(centerAnimX, centerAnimY, paint);
        canvas.drawPoint(centerAnimX, centerAnimY, paint2);
        if (mShineAnimator != null && !isRun) {
            isRun = true;
            showAnimation(mShineButton);
        }
    }

    private Paint getConfigPaint(Paint paint) {
        if (enableFlashing) {
            paint.setColor(colorRandom[random.nextInt(colorCount - 1)]);
        }
        return paint;
    }

    private double getThirdLength(int btnHeight, int btnWidth) {
        int all = btnHeight * btnHeight + btnWidth * btnWidth;
        return Math.sqrt(all);
    }

    private void initShineParams(ShineParams shineParams, ShineButton shineButton) {
        shineCount = shineParams.shineCount;
        turnAngle = shineParams.shineTurnAngle;
        smallOffsetAngle = shineParams.smallShineOffsetAngle;
        enableFlashing = shineParams.enableFlashing;
        allowRandomColor = shineParams.allowRandomColor;
        shineDistanceMultiple = shineParams.shineDistanceMultiple;
        animDuration = shineParams.animDuration;
        clickAnimDuration = shineParams.clickAnimDuration;
        smallShineColor = shineParams.smallShineColor;
        bigShineColor = shineParams.bigShineColor;
        shineSize = shineParams.shineSize;
        if (smallShineColor == 0) {
            smallShineColor = colorRandom[6];
        }

        if (bigShineColor == 0) {
            bigShineColor = shineButton.getColor();
        }

    }

    public static class ShineParams {
        public boolean allowRandomColor = false;
        public long animDuration = 1500;
        public int bigShineColor = 0;
        public long clickAnimDuration = 200;
        public boolean enableFlashing = false;
        public int shineCount = 7;
        public float shineTurnAngle = 20;
        public float shineDistanceMultiple = 1.5f;
        public float smallShineOffsetAngle = 20;
        public int smallShineColor = 0;
        public int shineSize = 0;

        public ShineParams() {
            colorRandom[0] = Color.parseColor("#FFFF99");
            colorRandom[1] = Color.parseColor("#FFCCCC");
            colorRandom[2] = Color.parseColor("#996699");
            colorRandom[3] = Color.parseColor("#FF6666");
            colorRandom[4] = Color.parseColor("#FFFF66");
            colorRandom[5] = Color.parseColor("#F44336");
            colorRandom[6] = Color.parseColor("#666666");
            colorRandom[7] = Color.parseColor("#CCCC00");
            colorRandom[8] = Color.parseColor("#666666");
            colorRandom[9] = Color.parseColor("#999933");
        }
    }
}
