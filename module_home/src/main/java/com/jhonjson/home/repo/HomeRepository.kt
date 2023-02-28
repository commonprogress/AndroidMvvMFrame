package com.jhonjson.home.repo

import com.base.commonality.base.bean.BaseData
import com.base.commonality.base.bean.BaseRepository
import com.common.net.RetrofitUtil
import com.jhonjson.home.model.RankBean
import com.jhonjson.home.model.WanBean
import com.jhonjson.home.service.HomeService


class HomeRepository : BaseRepository() {
    val service = RetrofitUtil.getService(HomeService::class.java)

    suspend fun requestWanData(drinkId: String): BaseData<List<WanBean>> {
        val requestBody = jsonRequest()
            .p("drinkId", drinkId)
            .body()
        return executeRequest { service.getBanner(requestBody) }
    }

    suspend fun requestRankData(): BaseData<RankBean> {
        return executeRequest { service.getRankList() }
    }
}