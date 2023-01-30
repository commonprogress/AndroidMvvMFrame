package com.base.commonality.manager

import android.app.Activity
import android.content.Context
import android.os.Process
import android.widget.Toast
import java.lang.Exception
import java.util.*
import android.view.Window

import android.view.WindowManager
import java.util.concurrent.CopyOnWriteArrayList


/**
 * @Author: jhosnjson
 * @Time: 2021/7/16
 * @Class: ActivityStackManager
 * @Remark: Activity 栈管理类  使用 CopyOnWriteArrayList  不能在多线程中操作
 */
object ActivityStackManager {

    // 管理栈
    val activityStack by lazy { CopyOnWriteArrayList<Activity>() }

    /**
     * 添加 Activity 到管理栈
     * @param activity Activity
     */
    fun addActivityToStack(activity: Activity) {
        activityStack.add(activity)
    }

    /**
     * 弹出栈内指定Activity 不finish
     * @param activity Activity
     */
    fun popActivityToStack(activity: Activity) {
        if (!activityStack.isEmpty()) {
            activityStack.forEach {
                if (it == activity) {
                    activityStack.remove(activity)
                    return
                }
            }
        }
    }

    /**
     * 返回到上一个 Activity 并结束当前 Activity
     */
    fun backToPreviousActivity() {
        if (!activityStack.isEmpty()) {
            val activity = activityStack.first()
            if (!activity.isFinishing) {
                activity.finish()
                activityStack.remove(activity)
            }
        }
    }

    /**
     * 判断activity 是否存在
     */
    fun getActivityIsInit(cls: Class<*>): Boolean {
        if (!activityStack.isEmpty()) {
            activityStack.forEachIndexed { index, activity ->
                if (cls == activity.javaClass && !activity.isFinishing) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 根据类名 判断是否是当前的 Activity
     * @param cls Class<*> 类名
     * @return Boolean
     */
    fun isCurrentActivity(cls: Class<*>): Boolean {
        val currentActivity = getCurrentActivity()
        return if (currentActivity != null) currentActivity.javaClass == cls else false
    }

    /**
     * 获取当前的 Activity
     */
    fun getCurrentActivity(): Activity? =
        if (!activityStack.isEmpty()) activityStack.last() else null

    /**
     * 结束指定的Activity
     *
     * @param clsName 包名+类名 MainActivity
     */
    fun finishActivitysAssign(clsName: String?) {
        if (activityStack == null || clsName == null) {
            return
        }
        val size = activityStack.size
        for (i in size - 1 downTo 0) {
            val activity = activityStack[i]
            if (null != activity && activity.javaClass.name != clsName) {
                finishActivity(activity.javaClass)
            }
        }
    }

    /**
     * 结束一个栈内所有Activity,除指定类名的 Activity
     * @param clsName 只需要类名 MainActivity
     */
    fun finishOtherActivity(vararg clsName: String) {
//        Log.e("DeBugLog", "activityStack.size ${activityStack.size}")
        activityStack.filter {
            !clsName.contains(it.javaClass.simpleName)
        }.forEach {
            if (!it.isFinishing) {
                finishActivityGone(it)
            }
            activityStack.remove(it)
//            Log.e("DeBugLog", "close activity ${it.componentName}")
        }
    }

    /**
     * 静默结束指定名的 Activity
     * @param cls Class<*>
     */
    fun finishActivityGone(activity: Activity) {
        val window: Window = activity.window
        val windowLayoutParams: WindowManager.LayoutParams = window.attributes
        windowLayoutParams.height = 1
        windowLayoutParams.width = 1
        windowLayoutParams.alpha = 0f
        window.attributes = windowLayoutParams
        activity.finish()
        activity.overridePendingTransition(0, 0)
    }

    /**
     * 结束一个栈内指定类名的 Activity
     * @param cls Class<*>
     */
    fun finishActivity(cls: Class<*>) {
        activityStack.forEach {
            if (it.javaClass == cls) {
                if (!it.isFinishing) {
                    it.finish()
                    activityStack.remove(it)
                }
                return
            }
        }
    }


    /**
     * 结束一个栈内除指定类名的 Activity
     * @param cls Class<*>
     */
    fun finishOtherActivity(cls: Class<*>) {
        activityStack.forEach {
            if (it.javaClass != cls) {
                if (!it.isFinishing) {
                    it.finish()
                    activityStack.remove(it)
                }
            }
        }
    }

    /**
     * 结束一个栈内指定类名的 多个Activity
     * @param clsName  AudienceLiveActivity
     */
    fun finishMultipleActivity(clsName: String) {
        activityStack.forEach {
            if (it.javaClass.simpleName == clsName) {
                if (!it.isFinishing) {
                    it.finish()
                    activityStack.remove(it)
                }
            }
        }
    }

    /**
     * 结束一个栈内指定类名的 多个Activity
     * @param cls Class<*>
     */
    fun finishMultipleActivity(cls: Class<*>) {
        activityStack.forEach {
            if (it.javaClass == cls) {
                if (!it.isFinishing) {
                    it.finish()
                    activityStack.remove(it)
                }
            }
        }
    }


    /**
     * 弹出其他 Activity
     */
    fun popOtherActivity() {
        val activityList = activityStack.toList()
        getCurrentActivity()?.run {
            activityList.forEach { activity ->
                if (this != activity) {
                    activityStack.remove(activity)
                    activity.finish()
                }
            }
        }
    }

    /**
     * 结束所有Activity
     * End all Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size: Int = activityStack.size
        while (i < size) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish()
            }
            i++
        }
        activityStack.clear()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     *
     * End the current Activity (the last one pushed in the stack)
     */
    fun finishActivity() {
        if (activityStack.isEmpty()) return
        val activity: Activity = activityStack.last()
        activityStack.remove(activity)
        activity.finish()
    }

    /**
     * 返回到指定 Activity
     */
    fun backToSpecifyActivity(activityClass: Class<*>) {
        val activityList = activityStack.toList()
        // 获取栈最上面的Activity
        val lastElement = activityStack.last()
        activityList.forEach {
            // 如果栈内存在该Activity就进行下一步操作
            if (it.javaClass == activityClass) {
                // 判断最上面的Activity是不是指定的Activity 不是就finish
                if (lastElement.javaClass == activityClass) {
                    return
                } else {
                    activityStack.remove(lastElement)
                    lastElement.finish()
                }
            }
        }
    }

    /**
     * 退出应用程序
     * Quit the application
     */
    private var exitTime: Long = 0

    fun safetyExitApp(context: Context) {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(
                context.applicationContext, "再按一次退出程序",
                Toast.LENGTH_SHORT
            ).show()
            exitTime = System.currentTimeMillis()
        } else {
            try {
                finishAllActivity()
                // 杀死该应用进程
                Process.killProcess(Process.myPid())
                System.exit(0)
            } catch (e: Exception) {
            }
        }
    }
}