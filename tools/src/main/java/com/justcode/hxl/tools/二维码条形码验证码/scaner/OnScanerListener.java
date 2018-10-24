package com.justcode.hxl.tools.二维码条形码验证码.scaner;


import com.google.zxing.Result;

/**
 * @author Vondear
 * @date 2017/9/22
 */

public interface OnScanerListener {
    void onSuccess(String type, Result result);

    void onFail(String type, String message);
}
