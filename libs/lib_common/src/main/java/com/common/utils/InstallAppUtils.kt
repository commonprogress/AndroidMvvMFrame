package com.common.utils

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import com.base.commonality.BaseAppliction


/**
 * Created by huangyayong
 * on 2021/9/7.
 * description：
 */
object InstallAppUtils {

    @SuppressLint("QueryPermissionsNeeded")
    /**是否安装微信*/
    fun isWeixinAvilible() : Boolean{
        /**
         * 一，通过判断手机中安装的应用的包名中，是否有符合微信的包名的。
        二，通过集成微信的SDK后，使用SDK里的api方法进行判断。
        经测试都有问题，即单独使用其中的一种方法都不能覆盖所有机型。
        如，使用微信SDK里提供的判断方法，在三星S7手机上始终返回false,不管你装没装微信。
        而使用包名的方法，则在华为的某一款手机上也始终返回false, 不管你装没装微信。
        所以采用二者结合的方式，先使用SDK里的方法判断一下，如果返回false，则继续使用判断包名的方法，直接扔代码如下：
         */
//        val api: IWXAPI = WXAPIFactory.createWXAPI(BaseAppliction.mContext, PayConstant.WX_APPID, false)
//
//        if(api.isWXAppInstalled) {
//            return true
//        }else{
//            val packageManager: PackageManager = BaseAppliction.mContext.packageManager // 获取packagemanager
//            val pinfo = packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
//
//            for (i in pinfo.indices) {
//                val pn = pinfo[i].packageName
//                if (pn == "com.tencent.mm") {
//                    return true
//                }
//            }
//        }
        return false
    }

    @SuppressLint("QueryPermissionsNeeded")
            /**是否安装QQ*/
    fun isQQClientAvailable() : Boolean{
        val packageManager: PackageManager = BaseAppliction.mContext.packageManager // 获取packagemanager
        val pinfo = packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
        for (i in pinfo.indices) {
            val pn = pinfo[i].packageName
            if (pn == "com.tencent.mobileqq") {
                return true
            }
        }
        return false
    }
}