package com.base.commonality

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.base.commonality.lifecycle.ActivityLifecycleCallbacksImpl
import com.base.commonality.utils.MMKVSpUtils

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


/**
 * @Author: jhosnjson
 * @Time: 2023/1/30
 * @Class: BaseAppliction
 */
open class BaseAppliction : MultiDexApplication() {


    private val mCoroutineScope by lazy(mode = LazyThreadSafetyMode.NONE) { MainScope() }


    companion object {
        // 全局Context
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context

        @SuppressLint("StaticFieldLeak")
        lateinit var application: BaseAppliction
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        mContext = base
        application = this
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        // 全局监听 Activity 生命周期
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())

    }


    override fun onTerminate() {
        super.onTerminate()
        mCoroutineScope.cancel()
    }

    /**
     * 腾讯 MMKV 初始化
     */
    public fun initMMKV(): String {
        val result = MMKVSpUtils.initMMKV(mContext)
//        Log.e("initMMKV 初始化", "initMMKV 初始化")
        return "MMKV -->> $result"
    }

    /**
     * 阿里路由 ARouter 初始化
     */
    public fun initARouter(): String {
        // 测试环境下打开ARouter的日志和调试模式 正式环境需要关闭
//        Log.e("initARouter 初始化","initARouter 初始化")
        if (BuildConfig.DEBUG) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application)
        return "ARouter -->> init complete"
    }

}