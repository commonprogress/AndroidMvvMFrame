package com.jhonjson.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.commonality.base.viewmodel.BaseViewModel
import com.jhonjson.home.model.WanBean
import com.jhonjson.home.repo.HomeRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * 类描述: 闪屏页配置信息
 * 创建人: jhonjson
 * 创建时间: 2021/7/22
 */
class HomeViewModel : BaseViewModel() {
    //StateFlow UI层通过该引用观察数据变化
    private val _homeFlow = MutableStateFlow<List<WanBean>>(ArrayList())
    val mHomeFlow = _homeFlow

    /**
     * 使用场景：一次性消费场景，比如弹窗，需求是在UI层只弹一次，即使App切到后台再切回来，也不会重复订阅(不会多次弹窗)；
     * 如果使用SharedFlow/StateFlow，UI层使用的lifecycle.repeatOnLifecycle、Flow.flowWithLifecycle，则在App切换前后台时，UI层会重复订阅
     * Channel使用特点：
     * 1、每个消息只有一个订阅者可以收到，用于一对一的通信
     * 2、第一个订阅者可以收到collect之前的事件，即粘性事件
     */
    private val _channel = Channel<List<WanBean>>()
    val channelFlow = _channel.receiveAsFlow()

    //LiveData UI层通过该引用观察数据变化
    val mHomeLiveData = MutableLiveData<List<WanBean>>()

    //Repository中间层 管理所有数据来源 包括本地的及网络的
    private val mHomeRepo = HomeRepository()


    /**
     * Flow方式
     */
    fun getWanInfoByFlow(wanId: String = "") = requestDataWithFlow(modelFlow = _homeFlow) {
        mHomeRepo.requestWanData(wanId)
    }

    /**
     * Channel方式 一对一
     */
    fun getWanInfoByChannel(wanId: String = "") = requestDataWithSingleFlow(channel = _channel) {
        mHomeRepo.requestWanData(wanId)
    }


    /**
     * LiveData方式
     */
    fun getWanInfo(wanId: String = "") {
        launchRequest(liveData = mHomeLiveData) { mHomeRepo.requestWanData(wanId) }
    }

}