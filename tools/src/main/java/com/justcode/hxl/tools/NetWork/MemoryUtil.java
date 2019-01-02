package com.justcode.hxl.tools.NetWork;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import static android.content.Context.ACTIVITY_SERVICE;

public class MemoryUtil {
    public static String getMemory(Context context){
        String memoryStr = "未获取好内存";
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            //最大分配内存
            int memory = activityManager.getMemoryClass();
            Log.d("memory_memory","memory"+memory);
            //最大分配内存获取方法2
            float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0/ (1024 * 1024));
            Log.d("memory_memory","maxMemory"+maxMemory);
            //当前分配的总内存
            float totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0/ (1024 * 1024));
            Log.d("memory_memory","totalMemory"+totalMemory);
            //剩余内存
            float freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0/ (1024 * 1024));
            Log.d("memory_memory","freeMemory"+freeMemory);
            float baifenbi = (totalMemory)/maxMemory;
            memoryStr = baifenbi*100+"%";
        }catch (Exception e){
            e.printStackTrace();
        }
      return memoryStr;

    }
}
