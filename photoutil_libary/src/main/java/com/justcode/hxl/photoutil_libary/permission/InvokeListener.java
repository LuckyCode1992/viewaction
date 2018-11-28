package com.justcode.hxl.photoutil_libary.permission;


import com.justcode.hxl.photoutil_libary.model.InvokeParam;

/**
 * 授权管理回调
 */
public interface InvokeListener {
    PermissionManager.TPermissionType invoke(InvokeParam invokeParam);
}
