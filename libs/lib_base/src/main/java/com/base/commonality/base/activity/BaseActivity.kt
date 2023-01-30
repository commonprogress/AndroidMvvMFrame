package com.base.commonality.base.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.jhonjson.lib_base.R
import com.base.commonality.base.ClassUtils
import com.base.commonality.base.IBinding
import com.base.commonality.utils.EventBusUtils
import com.base.commonality.utils.ViewRecreateHelper

/**
 * 基类，不掺杂业务，没有必要不建议修改
 */
abstract class BaseActivity<VB : ViewBinding, VM : AndroidViewModel> : AppCompatActivity(),
    IBinding<VB> {

    lateinit var  mBinding: VB

    lateinit var mViewModel: VM

    /**
     * activity页面重建帮助类
     */
    private var mStatusHelper: ActivityRecreateHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        mBinding= getViewBing(layoutInflater)
        setStatusBar()
        setContentView(mBinding.root)

        initView(savedInstanceState)
        initViewModel()
        initData()
    }

    /**
     * 设置状态栏
     * 子类需要自定义时重写该方法即可
     * @return Unit
     */
    @SuppressLint("ResourceAsColor")
    open fun setStatusBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.black).init()
    }

    abstract fun getViewBing(layoutInflater: LayoutInflater):VB

    /**
     * 初始化 view
     * 设置view监听
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化数据
     * 设置数据
     * 请求接口等
     */
    open fun initData() = Unit

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        val viewModelClass = ClassUtils.getViewModel<VM>(this) ?: return
        mViewModel = ViewModelProvider(this).get(viewModelClass)
    }



    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        if (mStatusHelper == null) {
            //仅当触发重建需要保存状态时创建对象
            mStatusHelper = ActivityRecreateHelper(outState)
        } else {
            mStatusHelper?.onSaveInstanceState(outState)
        }
        super.onSaveInstanceState(outState)
    }

    /**
     * - activity 重建帮助工具类
     */
    private class ActivityRecreateHelper(savedInstanceState: Bundle? = null) :
        ViewRecreateHelper(savedInstanceState)


    override fun onDestroy() {
        EventBusUtils.unRegister(this)
        super.onDestroy()
    }

}