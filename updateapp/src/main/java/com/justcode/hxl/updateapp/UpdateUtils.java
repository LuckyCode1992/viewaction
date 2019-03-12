package com.justcode.hxl.updateapp;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.justcode.hxl.updateapp.core.DownloadService;
import com.justcode.hxl.updateapp.core.UpdateAppBean;
import com.justcode.hxl.updateapp.core.UpdateAppHttpUtil;
import com.justcode.hxl.updateapp.core.UpdateAppManager;

import java.io.File;

public class UpdateUtils {

    public UpdateUtils() {
    }

    private UpdateListener listener;

    public void downLoad(final Context context, String apkUrl) {
        UpdateAppBean updateAppBean = new UpdateAppBean();
        updateAppBean.setApkFileUrl(apkUrl);

        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {
            try {
                path = context.getExternalCacheDir().getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(path)) {
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            }
        } else {
            path = context.getCacheDir().getAbsolutePath();
        }

        //设置apk 的保存路径
        updateAppBean.setTargetPath(path);
        //实现网络接口，只实现下载就可以
        updateAppBean.setHttpManager(new UpdateAppHttpUtil());
        UpdateAppManager.download(context, updateAppBean, new DownloadService.DownloadCallback() {
            @Override
            public void onStart() {
                if (listener != null)
                    listener.onStart();
                //HProgressDialogUtils.showHorizontalProgressDialog((Activity) context, "下载进度", false);
                Log.d("UpdateUtils", "onStart() called");
            }

            @Override
            public void onProgress(float progress, long totalSize) {
                if (listener != null)
                listener.onProgress(progress, totalSize);
                //HProgressDialogUtils.setProgress(Math.round(progress * 100));
                Log.d("UpdateUtils", "onProgress() called with: progress = [" + progress + "], totalSize = [" + totalSize + "]");

            }

            @Override
            public void setMax(long totalSize) {
                if (listener != null)
                    listener.setMax(totalSize);
                Log.d("UpdateUtils", "setMax() called with: totalSize = [" + totalSize + "]");
            }

            @Override
            public boolean onFinish(File file) {
                if (listener != null)
                    listener.onFinish(file);
                //HProgressDialogUtils.cancel();
                Log.d("UpdateUtils", "onFinish() called with: file = [" + file.getAbsolutePath() + "]");
                return true;
            }

            @Override
            public void onError(String msg) {
                if (listener != null)
                    listener.onError(msg);
                HProgressDialogUtils.cancel();
                Log.e("UpdateUtils", "onError() called with: msg = [" + msg + "]");
            }

            @Override
            public boolean onInstallAppAndAppOnForeground(File file) {
                Log.d("UpdateUtils", "onInstallAppAndAppOnForeground() called with: file = [" + file + "]");
                return false;
            }
        });
    }

    public UpdateUtils setOnUpdateListerner(UpdateListener listerner) {
        this.listener = listerner;
        return this;
    }

    public interface UpdateListener {

        void onStart();

        void onProgress(float progress, long totalSize);

        void setMax(long totalSize);

        void onFinish(File file);

        void onError(String msg);
    }

}
