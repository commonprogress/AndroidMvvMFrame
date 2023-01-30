package com.base.commonality.interfaces


/**
 * @Author: jhosnjson
 * @Time: 2023/1/30
 * @Remark: 权限回调
 */
interface XxPermissionCallback {
    /**
     * @param permissions
     * @param all      是否全部授予
     */
    fun onGranted(permissions: List<String?>, all: Boolean)

    /**
     * @param permissions
     * @param isGranted      是否拒绝
     */
    fun onDenied(permissions: List<String?>, isDenied: Boolean)
}