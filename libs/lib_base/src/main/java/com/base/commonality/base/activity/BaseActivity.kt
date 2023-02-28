package com.base.commonality.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.base.commonality.base.IBinding

/**
 * 基类，不掺杂业务，没有必要不建议修改
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(),
    IBinding<VB> {

    lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        mBinding = getViewBing(layoutInflater)
        setContentView(mBinding.root)
        initData()
    }


    abstract fun getViewBing(layoutInflater: LayoutInflater): VB

    /**
     * 初始化数据
     * 设置数据
     * 请求接口等
     */
    open fun initData() = Unit

}