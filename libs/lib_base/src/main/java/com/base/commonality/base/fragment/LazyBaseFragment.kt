package com.base.commonality.base.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.jhonjson.lib_base.R

/**
 * 懒加载基类
 * 新的方式进行懒加载
 */
abstract class LazyBaseFragment<T : ViewBinding, VM : AndroidViewModel> : SimpleImmersionFragment<T, VM>() {
    private var isFirst = true

    /**
     * 请求加在数据
     *
     * 加载第一次请求数据
     */
    abstract fun lazyData()


    // 标题栏 必须实现
    override fun initView(savedInstanceState: Bundle?) {
        if (setTitleBar() != 0) {
            val titleBar: View = mBinding.root.findViewById(setTitleBar())
            if (isShow()) {
                ImmersionBar.setTitleBar(this.getBaseActivity(), titleBar)
            } else {
                ImmersionBar.setTitleBar(this.getBaseActivity(), 0, titleBar)
            }
        }
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.black).init()
    }

    // 标题栏 必须重写
    open fun setTitleBar(): Int {
        return 0
    }

    /**
     * 要显示标题栏，但是不需要适配全面屏的可以重写此方法 返回false
     *
     * 默认返回true
     */
    open fun isShow(): Boolean {
        return true
    }

    fun setIsFirst(isFirst : Boolean){
        this.isFirst = isFirst
    }

    override fun onResume() {
        super.onResume()
        if (isFirst) {
            isFirst = false
            lazyData()
        }
    }
}