package com.jhonjson.lib_base.commonality.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.jhonjson.lib_base.commonality.base.ClassUtils
import com.jhonjson.lib_base.commonality.base.IBinding
import com.jhonjson.lib_base.commonality.base.activity.BaseActivity

/**
 * 基类，不掺杂业务，如没有必要，不建议修改
 */
abstract class BaseFragment<VB : ViewBinding, VM : AndroidViewModel> : Fragment(), IBinding<VB> {
    private var _binding: VB? = null

    lateinit var mBinding: VB
    lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = getViewBing(inflater,container)
        initView(savedInstanceState)
        initViewModel()
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
     * 是否通过ViewModel共享数据
     * 默认不共享
     */
    open fun isShareViewModel(): Boolean {
        return false
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        val viewModelClass = ClassUtils.getViewModel<VM>(this) ?: return
        //  getBaseActivity() 让viewModel生命周期和activity绑定，实现activity和fragment利用viewModel共享数据
        mViewModel = ViewModelProvider(if (isShareViewModel()) getBaseActivity() else this).get(viewModelClass)
    }

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

    fun getBaseActivity(): BaseActivity<*, *> {
        return activity as BaseActivity<*, *>
    }

    /**
     * 判断mBinding是否有初始化
     */
    fun isInitializedBinding(): Boolean {
        return this::mBinding.isInitialized
    }

    /**
     * 判断mViewModel是否有初始化
     */
    fun isInitializedViewModel(): Boolean {
        return this::mViewModel.isInitialized
    }


    /**fragment拦截返回键返回true，不拦截返回false*/
    open fun onBackPressed(){
    }

}