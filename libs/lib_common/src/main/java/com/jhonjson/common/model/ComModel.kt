package com.jhonjson.common.model

/**
 * 类描述: 通用
 * 创建人: jhonjson
 * 创建时间: 2023/1/30
 */
class ComModel {

    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = ComModel()
    }


    /**
     * 上传图片
     *
     * @param name live 直播, video 短视频, avatar 头像
     */
//    fun getUploadCover(name: String = "avatar"): Flow<BaseHttpResult<AliUpLoadBean>>? {
//        return onBody(ComServerApi.APP_UPLOAD_COVER, hashMapOf<String, String>().apply {
//            put("name", name)
//        })
//    }

}