package com.jhonjson.user

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.commonality.base.viewmodel.BaseViewModel
import com.common.base.BaseMvvmActivity
import com.common.route.RouteUserUtils
import com.base.commonality.ktx.clickDelay
import com.jhonjson.user.databinding.ActivityUserBinding
import com.jhonjson.user.viewmodel.UserViewModel

@Route(path = RouteUserUtils.User_UserActivity)
class UserActivity : BaseMvvmActivity<ActivityUserBinding>() {
    override fun getViewBing(layoutInflater: LayoutInflater): ActivityUserBinding {
        return ActivityUserBinding.inflate(layoutInflater)
    }

    override fun getViewModel(): UserViewModel {
        return UserViewModel()
    }

    override fun init() {

    }

}