package com.base.commonality.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.base.commonality.BaseAppliction

/**
 * 判断网络状态
 */
object NetworkUtil {

    /**
     * 判断网络是否连接
     */
    fun isNetworkConnected(): Boolean {
        val manager = BaseAppliction.application
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < 23) {
            val networkInfo = manager.activeNetworkInfo
            return networkInfo != null && networkInfo.isAvailable
        } else {
            val network = manager.activeNetwork ?: return false
            val nc = manager.getNetworkCapabilities(network) ?: return false
            return nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }
}