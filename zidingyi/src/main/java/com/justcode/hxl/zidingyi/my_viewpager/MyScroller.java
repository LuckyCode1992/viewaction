package com.justcode.hxl.zidingyi.my_viewpager;

import android.os.SystemClock;

public class MyScroller {
    private long startTime;
    private boolean isFinsh;
    private long totalTime = 500;

    public void scroll() {

    }

    private float startx, starty;
    private int distancex, distanceY;

    public float getStartx() {
        return startx;
    }

    public void setStartx(float startx) {
        this.startx = startx;
    }

    public float getStarty() {
        return starty;
    }

    public void setStarty(float starty) {
        this.starty = starty;
    }

    public int getDistancex() {
        return distancex;
    }

    public void setDistancex(int distancex) {
        this.distancex = distancex;
    }

    public int getDistanceY() {
        return distanceY;
    }

    public void setDistanceY(int distanceY) {
        this.distanceY = distanceY;
    }

    public void startScroll(float startx, float starty, int distancex, int distanceY) {
        this.startx = startx;
        this.starty = starty;
        this.distancex = distancex;
        this.distanceY = distanceY;
        this.startTime = SystemClock.uptimeMillis();//系统开机时间
        this.isFinsh = false;
    }

    public float getCurrentx() {
        return currentx;
    }

    public void setCurrentx(float currentx) {
        this.currentx = currentx;
    }

    /**
     * 求移动一小段的距离
     * 求移动一小段对应的坐标
     * 求移动一小段对应的时间
     * <p>
     * true 正在移动
     * false：移动结束
     */
    private float currentx;

    public boolean cuputeScrollOffset() {
        if (isFinsh){
            return false;
        }
        long endTime = SystemClock.uptimeMillis();
        long passTime = endTime - startTime;
        if (passTime < totalTime) {
            ///计算平均速度
            float voleCity = distancex / totalTime;
            float distanceSmallX = passTime * distancex / totalTime;
            currentx = startx + distanceSmallX;

        } else {
            isFinsh = true;
            currentx = startx + distancex;
        }
        return true;
    }
}
