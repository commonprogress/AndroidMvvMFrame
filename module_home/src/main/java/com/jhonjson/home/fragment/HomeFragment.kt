package com.jhonjson.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.common.base.BaseMvvmFragment
import com.common.route.RouteHomeUtils
import com.jhonjson.home.databinding.FragmentHomeBinding
import com.jhonjson.home.viewmodel.HomeViewModel


@Route(path = RouteHomeUtils.Home_Fragment)
class HomeFragment : BaseMvvmFragment<FragmentHomeBinding, HomeViewModel>() {

    private val mHomeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun lazyData() {
    }

    override fun registerEvent() {

    }

    override fun getViewBing(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun showMainView() {
    }

    override fun initViews(view: View) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }


}