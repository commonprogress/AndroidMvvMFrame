package com.common.base

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.base.commonality.base.activity.BaseActivity
import com.base.commonality.base.viewmodel.BaseViewModel
import com.base.commonality.ktx.flowWithLifecycle2
import com.base.commonality.ktx.log
import com.common.widget.StatusViewOwner

/**
 * 基类,做一些统一方法的处理 比如：im
 */

abstract class BaseMvvmActivity<T : ViewBinding> : BaseActivity<T>() {

    private lateinit var mStatusViewUtil: StatusViewOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStatusViewUtil = StatusViewOwner(this, getStatusOwnerView()) {
            retryRequest()
        }
        init()
        registerEvent()
    }

    abstract fun getViewModel(): BaseViewModel

    private fun registerEvent() {
        //接收错误信息
        getViewModel().errorFlow.flowWithLifecycle2(this, Lifecycle.State.STARTED) { errMsg ->
            log("registerEvent   errorFlow ")
            val errStr = if (!TextUtils.isEmpty(errMsg)) errMsg else "出错了"
            mStatusViewUtil.showErrorView(errStr)
        }
        //接收Loading信息
        getViewModel().loadingFlow.flowWithLifecycle2(this, Lifecycle.State.STARTED) { isShow ->
            log("registerEvent   loadingFlow ")
            mStatusViewUtil.showLoadingView(isShow)
        }
        //接收正常信息
        getViewModel().normalFlow.flowWithLifecycle2(this) {
            log("registerEvent   normalFlow ")
            mStatusViewUtil.showMainView()
        }
    }

    protected abstract fun init()

    /**
     * 重新请求
     */
    open fun retryRequest() {}

    /**
     * 展示Loading、Empty、Error视图等
     */
    open fun getStatusOwnerView(): View? {
        return null
    }

}