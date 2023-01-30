package com.base.commonality.lifecycle

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.base.commonality.constant.BaseConstants
import com.base.commonality.manager.ActivityStackManager


/**
 * Activity生命周期监听
 * @Author: jhosnjson
 * @Time: 2023/1/30
 */
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {

    private val TAG = "ActivityLifecycle"
    private var mCount : Int = 0

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        ActivityStackManager.addActivityToStack(activity)
        Log.d(TAG, "${activity.javaClass.simpleName} --> onActivityCreated")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d(TAG, "${activity.javaClass.simpleName} --> onActivityStarted")

        mCount++
        if(mCount == 1 && BaseConstants.isBackground){
            //说明应用重新进入了前台
            BaseConstants.isBackground = false
        }
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d(TAG, "${activity.javaClass.simpleName} --> onActivityResumed")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.d(TAG, "${activity.javaClass.simpleName} --> onActivityPaused")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.d(TAG, "${activity.javaClass.simpleName} --> onActivityStopped")

        mCount--
        if(mCount <= 0 && !BaseConstants.isBackground && isRun(activity)){
            //说明应用进入了后台
            BaseConstants.isBackground = true
            Toast.makeText(activity, "心符短视频进入后台", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.d(TAG, "${activity.javaClass.simpleName} --> onActivitySaveInstanceState")
    }

    override fun onActivityDestroyed(activity: Activity) {
        ActivityStackManager.popActivityToStack(activity)
        Log.d(TAG, "${activity.javaClass.simpleName} --> onActivityDestroyed")
    }

    private fun isRun(context: Context) : Boolean{
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val list = am.getRunningTasks(100)
        var isAppRunning = false
        val packageName = "com.xinlang.daijiezou"
        //100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
        for (info in list) {
            if (info.topActivity!!.packageName == packageName || info.baseActivity!!.packageName == packageName) {
                isAppRunning = true
                break
            }
        }
        return isAppRunning
    }
}