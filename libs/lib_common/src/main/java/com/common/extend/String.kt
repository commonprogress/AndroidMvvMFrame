package com.common.extend

import android.content.Context
import android.widget.Toast
import com.base.commonality.BaseAppliction

/**
 * 类描述:String扩展函数
 * 创建人: lz
 * 创建时间: 2021/7/23
 * 修改备注:
 */
fun String.toast(duration: Int = Toast.LENGTH_SHORT, gravity: Int? = null) {
    toast(BaseAppliction.application, duration, gravity)
}

fun String.toast(context: Context, duration: Int = Toast.LENGTH_SHORT, gravity: Int? = null): Toast {
    return Toast.makeText(context, this, duration).apply {
        gravity?.let { setGravity(it, 0, 0) }
        show()
    }
}

/**
 * 格式化%s
 *
 * 例：String.format(R.string.test, "cd")
 * 同
 * "cd".format(R.string.test)
 * 注释：R.string.test 是"测试%s"
 */
fun String.format(resId: Int): String {
    return String.format(BaseAppliction.application.getString(resId), this)
}

/**
 * 格式化%d
 */
fun Int.format(resId: Int): String {
    return String.format(BaseAppliction.application.getString(resId), this)
}