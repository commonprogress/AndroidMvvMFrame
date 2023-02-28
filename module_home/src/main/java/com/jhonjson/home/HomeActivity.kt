package com.jhonjson.home

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.commonality.base.viewmodel.BaseViewModel
import com.common.base.BaseMvvmActivity
import com.common.route.RouteHomeUtils
import com.jhonjson.home.databinding.ActivityHomeBinding
import com.jhonjson.home.viewmodel.HomeViewModel

@Route(path = RouteHomeUtils.Home_HomeActivity)
class HomeActivity : BaseMvvmActivity<ActivityHomeBinding>() {
    override fun getViewBing(layoutInflater: LayoutInflater): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }


    override fun getViewModel(): HomeViewModel {
        return HomeViewModel()
    }

    override fun init() {
    }


}