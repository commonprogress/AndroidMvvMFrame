package com.jhonjson.androidmvvmframe

import android.os.Bundle
import android.view.LayoutInflater
import com.jhonjson.androidmvvmframe.databinding.ActivityMainBinding
import com.jhonjson.androidmvvmframe.viewmodel.LoginViewModel
import com.common.activity.BaseMvvmActivity
import com.base.commonality.ktx.clickDelay

class MainActivity : BaseMvvmActivity<ActivityMainBinding, LoginViewModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.tvUser.clickDelay {
            mViewModel.getUserInfo()
        }

        mViewModel.value.observe(this) {
            mBinding.tvShowUser.text = "显示用户数据：$it"
        }

    }

    override fun getViewBing(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}