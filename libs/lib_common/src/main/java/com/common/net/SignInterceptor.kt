package com.common.net

import android.os.Build
import com.base.commonality.utils.MMKVSpUtils
import com.chad.library.BuildConfig
import okhttp3.*
import java.io.IOException

/**
 * 自定义拦截器
 * 添加header头
 */
class SignInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var keyChannel = MMKVSpUtils.getString("key_Channel", "").toString()
        var requestBuilder = chain.request().newBuilder()
            .header("Access-Token", MMKVSpUtils.getString("token", "").toString())
            .header("reg-platform", "ANDROID") // 平台
            .header("reg-device", "${Build.BRAND} ${Build.MODEL}") // 测试手机品牌型号
            .header("versionName", BuildConfig.VERSION_NAME) // 版本名称
            .header("versionCode", BuildConfig.VERSION_CODE.toString()) // 版本号

        return chain.proceed(requestBuilder.build())
    }
}