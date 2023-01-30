package com.jhonjson.androidmvvmframe.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.jhonjson.androidmvvmframe.model.LoginModel
import com.jhonjson.common.utils.IOUtils
import com.jhonjson.lib_base.commonality.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

/**
 * 类描述: 闪屏页配置信息
 * 创建人: jhonjson
 * 创建时间: 2021/7/22
 */
class LoginViewModel(application: Application) : BaseViewModel(application) {
    var value = MutableLiveData<String>()

    fun initData(bundle: Bundle?) {

    }

    /**
     * 获取用户信息
     */
    fun getUserInfo() {
        IOUtils.ioScope.launch {
            value.value = LoginModel.singleton.getUserInfo()
        }

//        MainModel.singleton.upDateTip()?.onEach {
//            if (it.code == 0) {
//                versionUpdateBean.value = it.data!!
//            } else {
//                errorCode.value = 10001
//            }
//        }?.catch {
//            errorCode.value = 10001
//            log("upDateTip：更新版本 失败")
//        }?.launchIn(viewModelScope)
    }

}