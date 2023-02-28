package com.jhonjson.user.model

data class WanBean(
    var desc: String,
    var imagePath: String,
    var title: String,
    var url: String,
)

data class RankBean(
    var curPage: Int,
    val datas: List<RankItemBean>,
)

data class RankItemBean(
    val coinCount: Int,
    val level: Int,
    val nickname: String,
    val rank: Int,
    val username: String,
)