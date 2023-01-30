
package com.jhonjson.androidmvvmframe.model


/**
 * 类描述: 主界面
 * 创建人: jhonjson
 * 创建时间: 2021/7/23
 */
class UserModel {

    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = UserModel()
    }


    /**
     * 获取用户信息
     */
    fun getUserInfo(): String {
        return "zhangsan"
    }


}