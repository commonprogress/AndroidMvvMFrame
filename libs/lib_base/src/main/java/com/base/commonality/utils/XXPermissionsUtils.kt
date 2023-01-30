package com.base.commonality.utils

import android.content.Context
import com.hjq.permissions.OnPermissionCallback

import com.hjq.permissions.XXPermissions
import com.base.commonality.interfaces.XxPermissionCallback


/**
 * XXPermissions封装
 * @Author: jhosnjson
 * @Time: 2023/1/30
 */
object XXPermissionsUtils {

    /**
     * @param context
     * @param permission    权限
     * @param xPermissionCallback    权限回调
     * @param isGoSetting    是否跳转到设置界面  默认跳转
     */
    fun requestPermissions(
        context: Context?,
        xPermissionCallback: XxPermissionCallback,
        isGoSetting: Boolean = true,
        vararg permission: String,
    ) {
        XXPermissions.with(context)
            // 申请多个权限
            .permission(permission)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: List<String?>, all: Boolean) {
                    xPermissionCallback.onGranted(permissions, all)
                }

                override fun onDenied(permissions: List<String?>, never: Boolean) {
                    xPermissionCallback.onDenied(permissions, never)
                    if (never && isGoSetting) {
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(context, permissions)
                    }
                }
            })
    }

    /**
     * @param context
     * @param permission    权限
     * @param xPermissionCallback    权限回调
     * @param isGoSetting    是否跳转到设置界面  默认跳转
     */
    @JvmName("requestPermissions1")
    fun requestPermissions(
        context: Context?,
        xPermissionCallback: XxPermissionCallback,
        isGoSetting: Boolean = true,
        permission: Array<String>,
    ) {
        XXPermissions.with(context)
            // 申请多个权限
            .permission(permission)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: List<String?>, all: Boolean) {
                    xPermissionCallback.onGranted(permissions, all)
                }

                override fun onDenied(permissions: List<String?>, never: Boolean) {
                    xPermissionCallback.onDenied(permissions, never)
                    if (never && isGoSetting) {
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(context, permissions)
                    }
                }
            })
    }
}