package com.common.extend

import android.view.View
import android.widget.Checkable

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2021/12/29
 * 修改备注:
 */

/**
 * 添加点击去重方法
 */
inline fun <T : View> T.doSingleClickListener(time: Long = 800, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}

//兼容点击事件设置为this的情况
fun <T : View> T.doSingleClickListener(onClickListener: View.OnClickListener, time: Long = 800) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            onClickListener.onClick(this)
        }
    }
}

var <T : View> T.lastClickTime: Long
    set(value) = setTag(this.id, value)
    get() = getTag(this.id) as? Long ?: 0