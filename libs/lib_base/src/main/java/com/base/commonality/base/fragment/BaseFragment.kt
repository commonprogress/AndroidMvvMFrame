package com.base.commonality.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.base.commonality.base.IBinding

/**
 * 基类，不掺杂业务，如没有必要，不建议修改
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment(), IBinding<VB> {
    private var _binding: VB? = null

    lateinit var mBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = getViewBing(inflater, container)
        initView(savedInstanceState)
        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    /**
     * 初始化 view
     *
     * view监听事件 等
     *
     * 注：ViewModel还没有初始化
     */
    abstract fun initView(savedInstanceState: Bundle?)


    /**
     * 布局文件
     */
    abstract fun getViewBing(layoutInflater: LayoutInflater, container: ViewGroup?): VB

    /**
     * 初始化数据
     * 设置数据
     * 请求接口等
     * 懒加载不不建议使用此方法
     */
    protected open fun initData() = Unit


}