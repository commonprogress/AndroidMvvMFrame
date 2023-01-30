package com.jhonjson.user

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.jhonjson.androidmvvmframe.viewmodel.UserViewModel
import com.jhonjson.common.activity.BaseMvvmActivity
import com.jhonjson.common.route.RouteUserUtils
import com.jhonjson.lib_base.commonality.ktx.clickDelay
import com.jhonjson.user.databinding.ActivityUserBinding

@Route(path = RouteUserUtils.User_UserActivity)
class UserActivity : BaseMvvmActivity<ActivityUserBinding, UserViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.tvUser.clickDelay {
            mViewModel.getUserInfo()
        }

        mViewModel.value.observe(this) {
            mBinding.tvShowUser.text = "显示我的用户数据：$it"
        }

    }

    override fun getViewBing(layoutInflater: LayoutInflater): ActivityUserBinding {
        return ActivityUserBinding.inflate(layoutInflater)
    }
}