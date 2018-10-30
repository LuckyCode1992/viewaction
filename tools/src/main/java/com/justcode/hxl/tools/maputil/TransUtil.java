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

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class TransUtil {

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


}
