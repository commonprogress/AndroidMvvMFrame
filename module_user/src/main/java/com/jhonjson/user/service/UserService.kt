package com.jhonjson.user.service


import com.base.commonality.base.bean.BaseData
import com.common.serve.ComServerApi
import com.jhonjson.user.model.RankBean
import com.jhonjson.user.model.WanBean
import okhttp3.RequestBody
import retrofit2.http.*

interface UserService {

    @POST(ComServerApi.API_BANNER)
    suspend fun getBanner(@Body requestBody: RequestBody): BaseData<List<WanBean>>

    @GET(ComServerApi.API_COIN_RANK)
    suspend fun getRankList(): BaseData<RankBean>
}