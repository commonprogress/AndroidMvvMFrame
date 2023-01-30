package com.jhonjson.common.activity

import android.os.Bundle
import android.view.*
import androidx.lifecycle.AndroidViewModel
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.jhonjson.lib_base.commonality.base.activity.BaseActivity

/**
 * 基类,做一些统一方法的处理 比如：im
 */

abstract class BaseMvvmActivity<T : ViewBinding, VM : AndroidViewModel> :
    BaseActivity<T, VM>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // 标题栏 必须实现
    override fun initView(savedInstanceState: Bundle?) {
        if (setTitleBar() != 0) {
            val titleBar: View = mBinding.root.findViewById(setTitleBar())
            ImmersionBar.setTitleBar(this, titleBar)
        }
    }

    // 标题栏 必须重写
    open fun setTitleBar(): Int {
        return 0
    }


}