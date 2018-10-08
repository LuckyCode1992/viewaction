package com.justcode.hxl.floatwindow.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.justcode.hxl.floatwindow.R;
import com.justcode.hxl.floatwindow.service.FloatWindowService;
import com.justcode.hxl.floatwindow.windowmanager.AssistMenuWindowManager;


public class FloatWindowMenuView extends LinearLayout {

    //记录大悬浮窗的宽度
    public static int viewWidth;

    //记录大悬浮窗的高度
    public static int viewHeight;

    Context context;

    private static CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts = new String[]{"安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "信用卡"};
    private int[] mItemImgs = new int[]{R.mipmap.home_mbank_1_clicked,
            R.mipmap.home_mbank_2_clicked, R.mipmap.home_mbank_3_clicked,
            R.mipmap.home_mbank_4_clicked, R.mipmap.home_mbank_5_clicked,
            R.mipmap.home_mbank_6_clicked};

    public FloatWindowMenuView(final Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.float_window_menu, this);
        View view = findViewById(R.id.big_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        Button close = (Button) findViewById(R.id.close);
        Button back = (Button) findViewById(R.id.back);
        Button gohome = (Button) findViewById(R.id.go_home);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service
                AssistMenuWindowManager.removeBigWindow(context);
                AssistMenuWindowManager.removeSmallWindow(context);
                Intent intent = new Intent(getContext(), FloatWindowService.class);
                context.stopService(intent);
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击返回的时候，移除大悬浮窗，创建小悬浮窗
                AssistMenuWindowManager.removeBigWindow(context);
                AssistMenuWindowManager.createSmallWindow(context);
            }
        });
        gohome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, FloatWindowService.activity.getClass());
                context.startActivity(intent);
            }
        });
    }


    public FloatWindowMenuView(final Context context, boolean type) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.float_window_menu2, this);
        View view = findViewById(R.id.big_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout.setFocusable(false);

        mCircleMenuLayout
                .setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {

                    @Override
                    public void itemClick(View view, int pos) {
                        Toast.makeText(context, mItemTexts[pos],
                                Toast.LENGTH_SHORT).show();

                        AssistMenuWindowManager.removeBigWindow(context);
                        AssistMenuWindowManager.createSmallWindow(context);
                    }

                    @Override
                    public void itemCenterClick(View view) {
                        Toast.makeText(context, "关闭悬浮球",
                                Toast.LENGTH_SHORT).show();

                        AssistMenuWindowManager.removeBigWindow(context);
                        AssistMenuWindowManager.removeSmallWindow(context);
                        Intent intent = new Intent(getContext(), FloatWindowService.class);
                        context.stopService(intent);

                    }
                });
    }

}
