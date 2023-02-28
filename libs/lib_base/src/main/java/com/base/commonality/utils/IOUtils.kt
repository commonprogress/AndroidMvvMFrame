package com.base.commonality.utils

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * 类描述: 全局Scope 在activity,fragment,viewmodel不建议使用，已经在application中销毁处理
 * 创建人: lz
 * 创建时间: 2021/8/10
 * 修改备注:
 */
object IOUtils {
    val ioScope = MainScope()

    fun cancel() {
        ioScope.cancel()
    }
}


