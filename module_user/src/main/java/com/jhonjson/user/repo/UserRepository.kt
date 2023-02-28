package com.jhonjson.user.repo

import com.base.commonality.base.bean.BaseData
import com.base.commonality.base.bean.BaseRepository
import com.common.net.RetrofitUtil
import com.jhonjson.user.model.RankBean
import com.jhonjson.user.model.WanBean
import com.jhonjson.user.service.UserService


class UserRepository : BaseRepository() {
    val service = RetrofitUtil.getService(UserService::class.java)

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