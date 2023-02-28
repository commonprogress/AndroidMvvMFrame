package com.jhonjson.androidmvvmframe.repo

import com.base.commonality.base.bean.BaseData
import com.base.commonality.base.bean.BaseRepository
import com.base.commonality.ktx.log
import com.common.net.RetrofitUtil
import com.jhonjson.androidmvvmframe.service.MainService
import com.jhonjson.home.model.RankBean
import com.jhonjson.home.model.WanBean


class MainRepository : BaseRepository() {
    val service = RetrofitUtil.getService(MainService::class.java)

    suspend fun requestWanData(drinkId: String): BaseData<List<WanBean>> {
        log("requestWanData  requestWanData  001 ")
        val requestBody = jsonRequest()
            .p("drinkId", drinkId)
            .body()
        return executeRequest { service.getBanner(requestBody) }
    }

    suspend fun requestRankData(): BaseData<RankBean> {
        return executeRequest { service.getRankList() }
    }
}