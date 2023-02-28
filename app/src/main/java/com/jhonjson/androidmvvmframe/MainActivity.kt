package com.jhonjson.androidmvvmframe

import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import com.base.commonality.base.viewmodel.BaseViewModel
import com.base.commonality.ktx.clickDelay
import com.base.commonality.ktx.flowWithLifecycle2
import com.base.commonality.ktx.log
import com.jhonjson.androidmvvmframe.databinding.ActivityMainBinding
import com.common.base.BaseMvvmActivity
import com.google.gson.Gson
import com.jhonjson.androidmvvmframe.viewmodel.MainViewModel

class MainActivity : BaseMvvmActivity<ActivityMainBinding>() {

    private val mViewModel: MainViewModel by viewModels()

    override fun getViewBing(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getViewModel(): BaseViewModel = mViewModel

    override fun init() {
        retryRequest()
        mBinding.tvUser.clickDelay {
            log("MainActivity  init  00 ")
//            mViewModel.getWanInfo()
//            mViewModel.getWanInfoByFlow()
            mViewModel.getWanInfoByChannel()
        }
    }

    override fun retryRequest() {
        /**
         * Flow方式订阅数据
         * 如果使用封装的flowSingleWithLifecycle2来接收数据，那么不会在前后台切换时重复订阅数据
         */
//        mViewModel.mHomeFlow.flowWithLifecycle2(this) { list ->
//            log("retryRequest  retryRequest list 00  ${Gson().toJson(list)}")
//            mBinding.tvShowUser.text = Gson().toJson(list).toString()
//        }
//
//        /**
//         * Channel方式订阅数据 只能一对一
//         */
        mViewModel.channelFlow.flowWithLifecycle2(this) { list ->
            log("retryRequest  retryRequest list 11  ${Gson().toJson(list)}")
            mBinding.tvShowUser.text = Gson().toJson(list).toString()
        }

        mViewModel.mHomeLiveData.observe(this) { list ->
            mBinding.tvShowUser.text = Gson().toJson(list).toString()
            log("retryRequest  retryRequest list   ${Gson().toJson(list)}")
        }
    }


}