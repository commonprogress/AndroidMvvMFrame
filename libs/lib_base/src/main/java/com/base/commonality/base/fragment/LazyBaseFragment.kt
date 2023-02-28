package com.base.commonality.base.fragment

import androidx.viewbinding.ViewBinding

/**
 * 懒加载基类
 * 新的方式进行懒加载
 */
abstract class LazyBaseFragment<T : ViewBinding> : BaseFragment<T>() {
    private var isFirst = true

    /**
     * 请求加在数据
     *
     * 加载第一次请求数据
     */
    abstract fun lazyData()


    fun setIsFirst(isFirst: Boolean) {
        this.isFirst = isFirst
    }

    override fun onResume() {
        super.onResume()
        if (isFirst) {
            isFirst = false
            lazyData()
        }
    }

    /**
     * 请求 返回数据注册
     */
    open fun registerEvent() {}
}