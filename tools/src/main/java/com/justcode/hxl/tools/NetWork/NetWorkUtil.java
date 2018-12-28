package com.justcode.hxl.tools.NetWork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.content.Context.WIFI_SERVICE;
import static android.telephony.CellSignalStrength.SIGNAL_STRENGTH_GOOD;
import static android.telephony.CellSignalStrength.SIGNAL_STRENGTH_GREAT;
import static android.telephony.CellSignalStrength.SIGNAL_STRENGTH_MODERATE;
import static android.telephony.CellSignalStrength.SIGNAL_STRENGTH_NONE_OR_UNKNOWN;
import static android.telephony.CellSignalStrength.SIGNAL_STRENGTH_POOR;

public class NetWorkUtil {
    /**
     * 网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /* @author suncat
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     * @return
     */
    public static final boolean ping() {

        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.d("----result---", "result = " + result);
        }
        return false;
    }

    public static void getNetWorkSignal(final Context context, final OnNetWorkSignal onNetWorkSignal) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo.State gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (gprs == NetworkInfo.State.CONNECTED || gprs == NetworkInfo.State.CONNECTING) {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            mTelephonyManager.listen(new PhoneStateListener() {
                @Override
                public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                    super.onSignalStrengthsChanged(signalStrength);
                    //获取网络信号强度
                    int level = 0;
                    int gsmSignalStrength = signalStrength.getGsmSignalStrength();
                    int asu = gsmSignalStrength;
                    if (asu <= 2 || asu == 99) level = SIGNAL_STRENGTH_NONE_OR_UNKNOWN;//0 未知，没有
                    else if (asu >= 12) level = SIGNAL_STRENGTH_GREAT;// 最好 4
                    else if (asu >= 8) level = SIGNAL_STRENGTH_GOOD;//好 3
                    else if (asu >= 5) level = SIGNAL_STRENGTH_MODERATE;//一般2
                    else level = SIGNAL_STRENGTH_POOR;//差 1
                    onNetWorkSignal.call(level);
                }
            }, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        }
        //判断为wifi状态下才加载广告，如果是GPRS手机网络则不加载！
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            WifiManager wifi_service = (WifiManager) context.getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifi_service.getConnectionInfo();

            //其中wifiInfo有以下的方法：

            wifiInfo.getBSSID();

            wifiInfo.getSSID();

            wifiInfo.getIpAddress();//获取IP地址。

            wifiInfo.getMacAddress();//获取MAC地址。

            wifiInfo.getNetworkId();//获取网络ID。

            wifiInfo.getLinkSpeed();//获取连接速度，可以让用户获知这一信息。
            int le = 0;
            int level = wifiInfo.getRssi();//获取RSSI，RSSI就是接受信号强度指示。在这可以直 接和华为提供的Wi-Fi信号阈值进行比较来提供给用户，让用户对网络或地理位置做出调整来获得最好的连接效果。
            if (level > -50 && level < 0) {//最强
                le = 4;
            } else if (level > -70 && level < -50) {//较强
                le = 3;
            } else if (level > -80 && level < -70) {//较弱
                le = 2;
            } else if (level > -100 && level < -80) {//微弱
                le = 1;
            } else {//未知
                le = 0;
            }
            onNetWorkSignal.call(le);
        }
        if (!isNetworkConnected(context)) {
            onNetWorkSignal.call(0);
        }
    }

    public interface OnNetWorkSignal {
        void call(int signal);
    }

}

