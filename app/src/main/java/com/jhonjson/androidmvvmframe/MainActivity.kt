package com.jhonjson.androidmvvmframe

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.jhonjson.androidmvvmframe.databinding.ActivityMainBinding
import com.jhonjson.androidmvvmframe.viewmodel.MainViewModel
import com.jhonjson.common.activity.BaseMvvmActivity
import com.jhonjson.lib_base.commonality.ktx.clickDelay

class MainActivity : BaseMvvmActivity<ActivityMainBinding, MainViewModel>() {


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