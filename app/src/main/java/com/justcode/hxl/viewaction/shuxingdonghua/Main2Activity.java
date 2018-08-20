package com.justcode.hxl.viewaction.shuxingdonghua;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.justcode.hxl.viewaction.R;

public class Main2Activity extends AppCompatActivity {

    Button fangda, suoxiao, jianxian, jianyin, yidong, xuanzhuan, yidongjiajianxian, yidongjiaxuanzhuan, yidongjiafangdasuoxiao,zzhouxuanzhuan;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fangda = findViewById(R.id.fangda);
        suoxiao = findViewById(R.id.suoxiao);
        jianxian = findViewById(R.id.jianxian);
        jianyin = findViewById(R.id.jianyin);
        yidong = findViewById(R.id.yidong);
        xuanzhuan = findViewById(R.id.xuanzhuan);
        yidongjiajianxian = findViewById(R.id.yidongjiajianxian);
        yidongjiaxuanzhuan = findViewById(R.id.yidongjiaxuanzhuan);
        yidongjiafangdasuoxiao = findViewById(R.id.yidongjiafangdasuoxiao);
        zzhouxuanzhuan = findViewById(R.id.zzhouxuanzhuan);
        iv = findViewById(R.id.iv);

        fangda.setOnClickListener(onClickListener);
        suoxiao.setOnClickListener(onClickListener);
        jianxian.setOnClickListener(onClickListener);
        jianyin.setOnClickListener(onClickListener);
        yidong.setOnClickListener(onClickListener);
        xuanzhuan.setOnClickListener(onClickListener);
        yidongjiajianxian.setOnClickListener(onClickListener);
        yidongjiaxuanzhuan.setOnClickListener(onClickListener);
        yidongjiafangdasuoxiao.setOnClickListener(onClickListener);
        zzhouxuanzhuan.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fangda:
                    scaleBig(iv);
                    break;
                case R.id.suoxiao:
                    scaleSmall(iv);
                    break;
                case R.id.jianxian:
                    alphaShow(iv);
                    break;
                case R.id.jianyin:
                    alphaDisShow(iv);
                    break;
                case R.id.yidong:
                    translate(iv);
                    break;
                case R.id.xuanzhuan:
                    rotateCenter(iv);
                    break;
                case R.id.yidongjiajianxian:
                    translateAndalphaShow(iv);
                    break;
                case R.id.yidongjiaxuanzhuan:
                    translateAndRotate(iv);
                    break;
                case R.id.yidongjiafangdasuoxiao:
                    translateAndScale(iv);
                    break;
                case R.id.zzhouxuanzhuan:
                    rotateZ(iv);
            }
        }
    };

    private void rotateZ(View view) {
        RotateAnimationZ animation = new RotateAnimationZ();
        animation.isZhengfangxiang = true;
        animation.direction = animation.X;
        animation.setDuration(2000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(animation);
    }

    private void translateAndScale(View view) {
        TranslateAnimation animation1 = new TranslateAnimation(Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 200f, Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 0f);
        animation1.setDuration(2000);
        animation1.setAnimationListener(listener);
        animation1.setInterpolator(new AccelerateInterpolator());
        animation1.setRepeatCount(Animation.INFINITE);
        animation1.setRepeatMode(Animation.RESTART);
        animation1.setFillAfter(false);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f, view.getWidth() / 2, view.getHeight() / 2);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setAnimationListener(listener);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.RESTART);
        scaleAnimation.setFillAfter(false);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(animation1);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setFillAfter(false);
        view.startAnimation(animationSet);
    }

    private void translateAndRotate(View view) {
        RotateAnimation animation1 = new RotateAnimation(0f, 359f, Animation.ABSOLUTE, view.getWidth()/2, Animation.ABSOLUTE, view.getHeight()/2);
        animation1.setDuration(2000);
        animation1.setAnimationListener(listener);
        animation1.setInterpolator(new LinearInterpolator());
        animation1.setRepeatCount(3);
        animation1.setRepeatMode(Animation.RESTART);
        animation1.setFillAfter(false);

        TranslateAnimation animation2 = new TranslateAnimation(Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 200f, Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 0f);
        animation2.setDuration(2000);
        animation2.setAnimationListener(listener);
        animation2.setInterpolator(new AccelerateInterpolator());
        animation2.setRepeatCount(0);
        animation2.setRepeatMode(Animation.RESTART);
        animation2.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(animation1);
        animationSet.addAnimation(animation2);
        animationSet.setFillAfter(true);
        view.startAnimation(animationSet);

    }

    void translateAndalphaShow(View view) {
        TranslateAnimation animation1 = new TranslateAnimation(Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 200f, Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 0f);
        animation1.setDuration(2000);
        animation1.setAnimationListener(listener);
        animation1.setInterpolator(new AccelerateInterpolator());
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(Animation.RESTART);
        animation1.setFillAfter(true);


        AlphaAnimation animation2 = new AlphaAnimation(0f, 1f);
        animation2.setDuration(2000);
        animation2.setAnimationListener(listener);
        animation2.setInterpolator(new AccelerateInterpolator());
        animation2.setRepeatCount(0);
        animation2.setRepeatMode(Animation.RESTART);
        animation2.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(animation1);
        animationSet.addAnimation(animation2);
        animationSet.setFillAfter(true);
        view.startAnimation(animationSet);
    }

    void rotateCenter(View view) {
        RotateAnimation animation = new RotateAnimation(0f, 359f, Animation.ABSOLUTE, view.getWidth()/2, Animation.ABSOLUTE, view.getHeight()/2);
        animation.setDuration(1000);
        animation.setAnimationListener(listener);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(3);
        animation.setRepeatMode(Animation.RESTART);
        animation.setFillAfter(false);
        view.startAnimation(animation);
    }

    private void translate(View view) {
        TranslateAnimation animation = new TranslateAnimation(Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 200f, Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 0f);
        animation.setDuration(2000);
        animation.setAnimationListener(listener);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setRepeatCount(0);
        animation.setRepeatMode(Animation.RESTART);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    void alphaDisShow(View view) {
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(2000);
        animation.setAnimationListener(listener);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setRepeatCount(2);
        animation.setRepeatMode(Animation.RESTART);
        animation.setFillAfter(true);
        view.startAnimation(animation);


    }

    void alphaShow(View view) {
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(2000);
        animation.setAnimationListener(listener);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setRepeatCount(2);
        animation.setRepeatMode(Animation.RESTART);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }


    void scaleSmall(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.5f, 1f, 0.5f, view.getWidth() / 2, view.getHeight() / 2);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setAnimationListener(listener);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.RESTART);
        scaleAnimation.setFillAfter(false);
        view.startAnimation(scaleAnimation);
    }

    void scaleBig(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f, view.getWidth() / 2, view.getHeight() / 2);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setAnimationListener(listener);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.RESTART);
        scaleAnimation.setFillAfter(false);
        view.startAnimation(scaleAnimation);
    }

    Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            Log.d("Animation_Animation", animation.toString());
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.d("Animation_Animation", animation.toString());
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            Log.d("Animation_Animation", animation.toString());
        }
    };

    /** 差值器
     *   AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速

     AccelerateInterpolator  在动画开始的地方速率改变比较慢，然后开始加速

     AnticipateInterpolator 开始的时候向后然后向前甩

     AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值

     BounceInterpolator   动画结束的时候弹起

     CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线

     DecelerateInterpolator 在动画开始的地方快然后慢

     LinearInterpolator   以常量速率改变

     OvershootInterpolator    向前甩一定值后再回到原来位置
     */

    /**重复模式
     * RESTART：重新从头开始执行。

     REVERSE：反方向执行。
     */
}
