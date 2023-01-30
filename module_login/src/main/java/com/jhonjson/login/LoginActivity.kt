package com.jhonjson.login

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.jhonjson.androidmvvmframe.viewmodel.LoginViewModel
import com.jhonjson.common.activity.BaseMvvmActivity
import com.jhonjson.common.route.RouteLoginUtils
import com.jhonjson.lib_base.commonality.ktx.clickDelay
import com.jhonjson.login.databinding.ActivityLoginBinding

@Route(path = RouteLoginUtils.Login_LoginActivity)
class LoginActivity : BaseMvvmActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.tvUser.clickDelay {
            mViewModel.getUserInfo()
        }

        mViewModel.value.observe(this) {
            mBinding.tvShowUser.text = "显示登陆用户数据：$it"
        }

    }

    override fun getViewBing(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }
}