package com.jhonjson.home.service


import com.base.commonality.base.bean.BaseData
import com.common.serve.ComServerApi
import com.jhonjson.home.model.RankBean
import com.jhonjson.home.model.WanBean
import okhttp3.RequestBody
import retrofit2.http.*

interface HomeService {

    @POST(ComServerApi.API_BANNER)
    suspend fun getBanner(@Body requestBody: RequestBody): BaseData<List<WanBean>>

    @GET(ComServerApi.API_COIN_RANK)
    suspend fun getRankList(): BaseData<RankBean>
}