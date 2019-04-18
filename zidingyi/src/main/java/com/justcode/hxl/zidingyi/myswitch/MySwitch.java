package com.justcode.hxl.zidingyi.myswitch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.justcode.hxl.zidingyi.R;


//1.构造方法实例化类
//2.测量-measure  onMeasure
//如果当前view是一个viewGroup 还有义务测量子view
//子view有建议权
//3，指定位置 layout()   onLayout
//指定控件位置，一般view不用写这个方法，viewgroup才需要
//4.绘制视图  draw（）   onDraw()
//根据上面两个方法参数，进行绘制
public class MySwitch extends View {

    private Bitmap backgroundBitmap, slidingBitmap;
    private int slidMax;//距离左边最大 的值
    private int slide;//距离左边
    private Paint paint;
    private boolean isOpen = false;

    public MySwitch(Context context) {
        super(context);
    }

    /**
     * 如果在布局文件中使用该类，必须用到这个构造方法
     *
     * @param context
     * @param attrs
     */
    public MySwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        slidMax = backgroundBitmap.getWidth() - slidingBitmap.getWidth();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = !isOpen;
                if (isOpen) {
                    slide = slidMax;
                }else {
                    slide = 0;
                }
                //会导致onDraw（）方法执行，强制绘制
                invalidate();
            }
        });
    }

    public MySwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        canvas.drawBitmap(slidingBitmap, slide, 0, paint);
    }
}
