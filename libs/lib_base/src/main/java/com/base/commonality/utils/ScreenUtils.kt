package com.base.commonality.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.View
import android.view.ViewConfiguration
/**
 * @Author: jhosnjson
 * @Time: 2023/1/30
 * @describe 屏幕相关
 */
@Suppress("DEPRECATION")
object ScreenUtils {
    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 获取系统栏的高度
     *
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        val res = context.resources
        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    /**
     * 课程banner高度
     *
     * @param context
     * @return
     */
    fun getCourseBannerHeight(context: Context): Long {
        val width = getScreenWidth(context)
        return getCourseBannerHeight(width)
    }

    fun getCourseBannerHeight(width: Int): Long {
        return getHeight(width, 0.337f).toLong()
    }

    fun getBookListHeight(width: Int): Long {
        return getHeight(width, 0.406f).toLong()
    }

    /**
     * 弹窗广告位高度
     * @param width
     * @return
     */
    fun getDialogHeight(width: Int): Long {
        return getHeight(width, 1.25f).toLong()
    }


    /**
     * 获取必修课最新发布高度
     *
     * @param width
     * @return
     */
    fun getCourseTopHeight(width: Int): Long {
        val scale = 580f / 1242f
        return getHeight(width, scale).toLong()
    }

    fun getMineBannerHeight(width: Int): Long {
        return getHeight(width, 0.238f).toLong()
    }


    /**
     * 获取限时免费高度
     *
     * @param width
     * @return
     */
    fun getFreeHeight(width: Int): Long {
        val scale = 160f / 335f
        return getHeight(width, scale).toLong()
    }

    /**
     * @param width
     * @param radio height/width
     * @return
     */
    fun getHeight(width: Int, radio: Float): Int {
        return (width * radio).toInt()
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }


    /**
     * 根据屏幕宽度获取相应高度
     *
     * @param context
     * @return
     */
    fun getHeightByWidth(
        context: Context,
        height: Int,
        width: Int,
        rootViewW: Int,
    ): Long {
        var screenWidth = 0
        screenWidth = if (rootViewW == 0) {
            getScreenWidth(context)
        } else {
            rootViewW
        }
        return screenWidth * height / width.toLong()
    }

    fun setLayoutWidth(view: View, width: Int) {
        val lp = view.layoutParams
        lp.width = width
        view.layoutParams = lp
    }

    fun setLayoutWidthHeight(view: View, width: Int) {
        val lp = view.layoutParams
        lp.width = width
        lp.height = width
        view.layoutParams = lp
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    fun snapShotWithStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
        view.destroyDrawingCache()
        return bp
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(
            bmp, 0, statusBarHeight, width, height
                    - statusBarHeight
        )
        view.destroyDrawingCache()
        return bp
    }

    /**
     * 判断是否虚拟键
     *
     * @return
     */
    fun getNavigationBarHeight(context: Context): Int {
        val hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey()
        val hasBackKey =
            KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        return if (checkDeviceHasNavigationBar(context)) {
            val resources = context.resources
            val resourceId =
                resources.getIdentifier("navigation_bar_height", "dimen", "android")
            //获取NavigationBar的高度
            resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

    /**
     * 获取是否存在NavigationBar
     *
     * @param context
     * @return
     */
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        val rs = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass =
                Class.forName("android.os.SystemProperties")
            val m =
                systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride =
                m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {
        }
        return hasNavigationBar
    }


    /**
     * 虚拟操作拦（home等）是否显示
     */
    fun isNavigationBarShow(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            val realSize = Point()
            display.getSize(size)
            display.getRealSize(realSize)
            realSize.y != size.y
        } else {
            val menu = ViewConfiguration.get(activity).hasPermanentMenuKey()
            val back =
                KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            !(menu || back)
        }
    }
}