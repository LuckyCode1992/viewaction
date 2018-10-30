package com.justcode.hxl.tools.maputil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 目前主流地图，百度，高德，腾讯（google在国内还是比较少的）
 */
public class MapUtil {

    private static MapUtil intance = new MapUtil();

    private MapUtil() {
    }

    static Context context0;

    public static MapUtil getIntance(Context context) {
        context0 = context;
        return intance;
    }


    /***************************高德地图*********************************************/
    public void myLocation2WhereByGaode(float lat, float lon) {

        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&sname=我的位置&dlat=" + lat + "&dlon=" + lon + "&dname=我的目的地" + "" + "&dev=0&m=0&t=1");
            context0.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void myLocation2WhereByGaode(float lat, float lon, String whereName) {

        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&sname=我的位置&dlat=" + lat + "&dlon=" + lon + "&dname=" + whereName + "&dev=0&m=0&t=1");
            context0.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void from2WhereByGaode(float fromlat, float fromlon, float lat, float lon) {
        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&slat=" + fromlat + "&slon=" + fromlon + "&sname=" + "我的位置" + "&dlat=" + lat + "&dlon=" + lon + "&dname=" + "我的目的地" + "&dev=0&m=0&t=1");
            context0.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /***************************高德地图*********************************************/


    /***************************百度地图*********************************************/
    public void myLocation2WhereByBaidu(float lat, float lon) {
        try {

            Intent intent = Intent.getIntent("intent://map/direction?" +
                    //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                    "destination=latlng:" + lat + "," + lon + "|name:我的目的地" +        //终点
                    "&mode=&" +          //导航路线方式
                    "region=" +           //
                    "&src=#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");

            context0.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void myLocation2WhereByBaidu(float lat, float lon, String whereName) {
        try {

            Intent intent = Intent.getIntent("intent://map/direction?" +
                    //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                    "destination=latlng:" + lat + "," + lon + "|name:" + whereName +        //终点
                    "&mode=&" +          //导航路线方式
                    "region=" +           //
                    "&src=#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");

            context0.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void from2WhereByBaidu(float fromlat, float fromlon, float lat, float lon) {
        try {

            Intent intent = Intent.getIntent("intent://map/direction?" +
                    "origin=latlng:" + fromlat + "," + fromlon + "|name:我的出发地" + "&" +   //起点  此处不传值默认选择当前位置
                    "destination=latlng:" + lat + "," + lon + "|name:" + "我的目的地" +        //终点
                    "&mode=&" +          //导航路线方式
                    "region=" +           //
                    "&src=#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");

            context0.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /***************************百度地图*********************************************/

    /***************************腾讯地图*********************************************/
    public void myLocation2WhereByTengxun(float lat, float lon) {

        try {

            Intent intent = Intent.getIntent("qqmap://map/routeplan?type=drive&from=我的位置&" +
                    "fromcoord=" +
//                    "39.994745" +
//                    "," +
//                    "116.247282" +
                    "&to=我的目的地&" +
                    "tocoord=" +
                    lat +
                    "," +
                    lon +
                    "&referer=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77");

            context0.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void myLocation2WhereByTengxun(float lat, float lon, String whereName) {
        try {

            Intent intent = Intent.getIntent("qqmap://map/routeplan?type=drive&from=我的位置&" +
                    "fromcoord=" +
//                    "39.994745" +
//                    "," +
//                    "116.247282" +
                    "&to=" +
                    whereName +
                    "&tocoord=" +
                    lat +
                    "," +
                    lon +
                    "&referer=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77");

            context0.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void from2WhereByTengxun(float fromlat, float fromlon, float lat, float lon) {
        try {

            Intent intent = Intent.getIntent("qqmap://map/routeplan?type=drive&from=我的位置&" +
                    "fromcoord=" +
                    fromlat +
                    "," +
                    fromlon +
                    "&to=" +
                    "我的目的地" +
                    "&tocoord=" +
                    lat +
                    "," +
                    lon +
                    "&referer=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77");

            context0.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***************************腾讯地图*********************************************/
    public boolean hasBaiduMap() {
        return isAvilible(context0, "com.baidu.BaiduMap");
    }

    public boolean hasGaodeMap() {
        return isAvilible(context0, "com.autonavi.minimap");
    }

    /***
     * 是否安装腾讯地图
     * @return
     */
    public boolean hasTencentMap() {
        try {
            if (!new File("/data/data/com.tencent.map").exists()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public boolean isAvilible(Context context, String packageName) {
        try {
            //获取packagemanager
            final PackageManager packageManager = context.getPackageManager();
            //获取所有已安装程序的包信息
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            //用于存储所有已安装程序的包名
            List<String> packageNames = new ArrayList<String>();
            //从pinfo中将包名字逐一取出，压入pName list中
            if (packageInfos != null) {
                for (int i = 0; i < packageInfos.size(); i++) {
                    String packName = packageInfos.get(i).packageName;
                    packageNames.add(packName);
                }
            }
            //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
            return packageNames.contains(packageName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
