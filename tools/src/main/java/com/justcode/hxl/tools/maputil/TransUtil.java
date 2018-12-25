package com.justcode.hxl.tools.maputil;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class TransUtil {

    static int B_DIV_SCALE = 15;
    static BigDecimal x_pi_big = new BigDecimal("3.14159265358979324").multiply(new BigDecimal("3000")).divide(new BigDecimal("180"), B_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    // π
    static BigDecimal pi = new BigDecimal("3.1415926535897932384626");
    // 长半轴
    static BigDecimal a = new BigDecimal("6378245");
    static double aDouble = 6378245.0;
    // 扁率
    static BigDecimal ee = new BigDecimal("0.00669342162296594323");
    static double eeDouble = 0.00669342162296594323;
    static BigDecimal b_1 = new BigDecimal("-1");
    static BigDecimal b_2 = new BigDecimal("10");
    static BigDecimal b_3 = new BigDecimal("2");
    static BigDecimal b_4 = new BigDecimal("105");
    static BigDecimal b_5 = new BigDecimal("3");
    static BigDecimal b_6 = new BigDecimal("35");
    static BigDecimal b_7 = new BigDecimal("160");
    static BigDecimal b_8 = new BigDecimal("1");
    static BigDecimal b_9 = new BigDecimal("150");
    static BigDecimal b_10 = new BigDecimal("180");

    static BigDecimal b_11 = new BigDecimal("0.00002");
    static BigDecimal b_12 = new BigDecimal("0.000003");
    static BigDecimal b_13 = new BigDecimal("0.0065");
    static BigDecimal b_14 = new BigDecimal("0.006");


    public static double[] gps2Baidu(double lat, double lng) {
        double[] latlng = null;

        URL url = null;
        URLConnection connection = null;
        try {
            url = new URL("http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=" + String.valueOf(lat) + "&y="
                    + String.valueOf(lng));
            connection = url.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
            out.flush();
            out.close();

            // 服务器的回应的字串，并解析
            String sCurrentLine;
            String sTotalString;
            sCurrentLine = "";
            sTotalString = "";
            InputStream l_urlStream;
            l_urlStream = connection.getInputStream();
            BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
            while ((sCurrentLine = l_reader.readLine()) != null) {
                if (!sCurrentLine.equals(""))
                    sTotalString += sCurrentLine;
            }
            // System.out.println(sTotalString);
            sTotalString = sTotalString.substring(1, sTotalString.length() - 1);
            // System.out.println(sTotalString);
            String[] results = sTotalString.split("\\,");
            if (results.length == 3) {
                if (results[0].split("\\:")[1].equals("0")) {
                    String mapX = results[1].split("\\:")[1];
                    String mapY = results[2].split("\\:")[1];
                    mapX = mapX.substring(1, mapX.length() - 1);
                    mapY = mapY.substring(1, mapY.length() - 1);
                    mapX = new String(Base64.decode(mapX, Base64.DEFAULT));
                    mapY = new String(Base64.decode(mapY, Base64.DEFAULT));
                    // System.out.println(mapX);
                    // System.out.println(mapY);
                    latlng = new double[]{Double.parseDouble(mapX), Double.parseDouble(mapY)};
                } else {
                    System.out.println("error != 0");
                }
            } else {
                System.out.println("String invalid!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GPS转百度坐标异常！");
        }
        Log.d("MapUtil", "百度GPS==" + latlng[0] + " " + latlng[1]);
        return latlng;
    }

    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 对double类型数据保留小数点后多少位
     * 高德地图转码返回的就是 小数点后6位，为了统一封装一下
     *
     * @param digit 位数
     * @param in    输入
     * @return 保留小数位后的数
     */
    static double dataDigit(int digit, double in) {
        return new BigDecimal(in).setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();

    }
    static double dataDigit(double in){
        return new BigDecimal(in).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将火星坐标转变成百度坐标
     *
     * @param lngLat_gd 火星坐标（高德、腾讯地图坐标等）
     * @return 百度坐标
     */

    public static LngLat gaodeorTentxun2Baidu(LngLat lngLat_gd) {
        double x = lngLat_gd.getLongitude(), y = lngLat_gd.getLantitude();
        double z = sqrt(x * x + y * y) + 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) + 0.000003 * cos(x * x_pi);
        return new LngLat(dataDigit(6, z * cos(theta) + 0.0065), dataDigit(6, z * sin(theta) + 0.006));

    }

    /**
     * 将百度坐标转变成火星坐标
     *
     * @param lngLat_bd 百度坐标（百度地图坐标）
     * @return 火星坐标(高德 、 腾讯地图等)
     */
    public static LngLat baidu2GaodeorTentxun(LngLat lngLat_bd) {
        double x = lngLat_bd.getLongitude() - 0.0065, y = lngLat_bd.getLantitude() - 0.006;
        double z = sqrt(x * x + y * y) - 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) - 0.000003 * cos(x * x_pi);
        return new LngLat(dataDigit(6, z * cos(theta)), dataDigit(6, z * sin(theta)));

    }

    /**
     * 判断是否在国内，不在国内不做偏移
     *
     * @param lng
     * @param lat
     * @return
     */
    public static boolean out_of_china(BigDecimal lng, BigDecimal lat) {
        if (lng.compareTo(new BigDecimal("73.66")) < 0 || lng.compareTo(new BigDecimal("135.05")) > 0) {
            return true;
        } else if (lat.compareTo(new BigDecimal("3.86")) < 0 || lat.compareTo(new BigDecimal("53.55")) > 0) {
            return true;
        }
        return false;
    }

    /**
     * WGS坐标转百度坐标系(BD-09)
     *
     * @param lng WGS84坐标系的经度
     * @param lat WGS84坐标系的纬度
     * @return 百度坐标数组
     */
    public static double[] wgs84tobd09(double lng, double lat) {
        double[] gcj = wgs84togcj02(lng, lat);
        double[] bd09 = gcj02tobd09(gcj[0], gcj[1]);
        return bd09;
    }


    /**
     * 火星坐标系(GCJ-02)转百度坐标系(BD-09)
     *
     * @param lng 火星坐标经度
     * @param lat 火星坐标纬度
     * @return 百度坐标数组
     */
    public static double[] gcj02tobd09(double lng, double lat) {
        BigDecimal b_lng = new BigDecimal(lng);
        BigDecimal b_lat = new BigDecimal(lat);
        BigDecimal z = new BigDecimal(Math.sqrt(b_lng.multiply(b_lng).add(b_lat.multiply(b_lat)).doubleValue())).add(b_11.multiply(new BigDecimal(Math.sin(b_lat.multiply(x_pi_big).doubleValue()))));
        BigDecimal theta = new BigDecimal(Math.atan2(b_lat.doubleValue(), b_lng.doubleValue())).add(b_12.multiply(new BigDecimal(Math.cos(b_lng.multiply(x_pi_big).doubleValue()))));
        BigDecimal bd_lng = z.multiply(new BigDecimal(Math.cos(theta.doubleValue()))).add(b_13);
//        z * Math.cos(theta) + 0.0065;
        BigDecimal bd_lat = z.multiply(new BigDecimal(Math.sin(theta.doubleValue()))).add(b_14);
        return new double[]{bd_lng.doubleValue(), bd_lat.doubleValue()};
    }


    /**
     * WGS84转GCJ02(火星坐标系)
     *
     * @param lng WGS84坐标系的经度
     * @param lat WGS84坐标系的纬度
     * @return 火星坐标数组
     */
    public static double[] wgs84togcj02(double lng, double lat) {
        BigDecimal b_lng = new BigDecimal(lng + "");
        BigDecimal b_lat = new BigDecimal(lat + "");
        if (out_of_china(b_lng, b_lat)) {
            return new double[]{b_lng.doubleValue(), b_lat.doubleValue()};
        }
        BigDecimal dlat = transformlat(b_lng.subtract(b_4), b_lat.subtract(b_6));
        BigDecimal dlng = transformlng(b_lng.subtract(b_4), b_lat.subtract(b_6));
        BigDecimal radlat = b_lat.divide(b_10, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(pi);
        BigDecimal magic = new BigDecimal(Math.sin(radlat.doubleValue()));
        magic = b_8.subtract(ee.multiply(magic).multiply(magic));
        BigDecimal sqrtmagic = new BigDecimal(Math.sqrt(magic.doubleValue()));
        dlat = (dlat.multiply(b_10)).divide((a.multiply(b_8.subtract(ee))).divide((magic.multiply(sqrtmagic)), B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(pi), B_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        dlng = (dlng.multiply(b_10)).divide((a.divide(sqrtmagic, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(Math.cos(radlat.doubleValue())).multiply(pi))), B_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal mglat = b_lat.add(dlat);
        BigDecimal mglng = b_lng.add(dlng);
        return new double[]{dataDigit(mglng.doubleValue()), dataDigit(mglat.doubleValue())};
    }

    /**
     * 纬度转换
     *
     * @param lng
     * @param lat
     * @return
     */
    public static BigDecimal transformlat(BigDecimal lng, BigDecimal lat) {
        BigDecimal bet = b_1.multiply(b_2).multiply(b_2)
                .add(b_3.multiply(lng))
                .add(b_5.multiply(lat))
                .add(b_3.divide(b_2, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(lat).multiply(lat))
                .add(b_8.divide(b_2, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(lng).multiply(lat))
                .add(b_3.divide(b_2, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(Math.sqrt(Math.abs(lng.doubleValue())))));
        bet = bet.add((b_3.multiply(b_2).multiply(new BigDecimal(Math.sin(b_3.multiply(b_5).multiply(lng).multiply(pi).doubleValue())))
                .add(b_3.multiply(b_2).multiply(new BigDecimal(Math.sin(b_3.multiply(lng).multiply(pi).doubleValue()))))).multiply(b_3).divide(b_5, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP));
        bet = bet.add((b_3.multiply(b_2).multiply(new BigDecimal(Math.sin(lat.multiply(pi).doubleValue())))
                .add(b_3.multiply(b_3).multiply(b_2).multiply(new BigDecimal(Math.sin(lat.divide(b_5, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(pi).doubleValue()))))).multiply(b_3).divide(b_5, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP));
        bet = bet.add((b_7.multiply(new BigDecimal(Math.sin(lat.divide(b_5.multiply(b_3).multiply(b_3), B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(pi).doubleValue())))
                .add(b_7.multiply(b_3).multiply(new BigDecimal(Math.sin(lat.multiply(pi).divide(b_5.multiply(b_2), B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue()))))).multiply(b_3).divide(b_5, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP));
        return bet;
    }


    /**
     * 经度转换
     *
     * @param lng
     * @param lat
     * @return
     */
    public static BigDecimal transformlng(BigDecimal lng, BigDecimal lat) {
        BigDecimal bet = b_2.multiply(b_2).multiply(b_5).add(lng).add(b_3.multiply(lat)).add(b_8.divide(b_2, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(lng).multiply(lng))
                .add(b_8.divide(b_2, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(lng).multiply(lat)).add(b_8.divide(b_2, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(Math.sqrt(Math.abs(lng.doubleValue())))));
        bet = bet.add((b_3.multiply(b_2).multiply(new BigDecimal(Math.sin(b_3.multiply(b_5).multiply(lng).multiply(pi).doubleValue())))
                .add(b_3.multiply(b_2).multiply(new BigDecimal(Math.sin(b_3.multiply(lng).multiply(pi).doubleValue()))))).multiply(b_3).divide(b_5, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP));
        bet = bet.add((b_3.multiply(b_2).multiply(new BigDecimal(Math.sin(lng.multiply(pi).doubleValue())))
                .add(b_3.multiply(b_3).multiply(b_2).multiply(new BigDecimal(Math.sin(lng.divide(b_5, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(pi).doubleValue()))))).multiply(b_3).divide(b_5, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP));
        bet = bet.add((b_9.multiply(new BigDecimal(Math.sin(lng.divide(b_3.multiply(b_3).multiply(b_5), B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(pi).doubleValue())))
                .add(b_9.multiply(b_3).multiply(new BigDecimal(Math.sin(lng.divide(b_5.multiply(b_2), B_DIV_SCALE, BigDecimal.ROUND_HALF_UP).multiply(pi).doubleValue())))).multiply(b_3).divide(b_5, B_DIV_SCALE, BigDecimal.ROUND_HALF_UP)));
        return bet;
    }

    /**
     * * 火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
     */
    public static double[] gcj02_To_Gps84(double lon, double lat) {
        double[] gps = transform(lat, lon);
        double lontitude = lon * 2 - gps[1];
        double latitude = lat * 2 - gps[0];
        return new double[]{dataDigit(lontitude), dataDigit(latitude)};
    }

    public static double[] transform(double lat, double lon) {

        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - eeDouble* magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((aDouble * (1 - eeDouble)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (aDouble / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }

    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0
                * PI)) * 2.0 / 3.0;
        return ret;
    }

}
