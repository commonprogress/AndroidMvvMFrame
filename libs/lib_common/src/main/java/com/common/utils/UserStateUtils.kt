package com.common.utils

import com.tencent.bugly.crashreport.biz.UserInfoBean

/**
 * 类描述:用户缓存
 * 创建人: jhonjson
 * 创建时间: 2021/7/21
 */
object UserStateUtils {

    private var mUserInfo: UserInfoBean? = null

    /**
     * 退出登录
     */
    fun logout() {
//        clearUserInfo()
//        clearToken()
        mUserInfo = null
    }

}