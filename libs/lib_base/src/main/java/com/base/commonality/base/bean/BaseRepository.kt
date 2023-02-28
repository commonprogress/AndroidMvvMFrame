package com.base.commonality.base.bean

import com.base.commonality.interfaces.Callback
import com.base.commonality.ktx.log
import com.base.commonality.utils.IOUtils
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.HashMap

open class BaseRepository {

    suspend fun <T : Any> executeRequest(
        block: suspend () -> BaseData<T>
    ): BaseData<T> {
        val baseData = block.invoke()
        log("requestWanData  requestWanData  002 ")
        if (baseData.code == 0) {
            //正确
            baseData.state = ReqState.Success
        } else {
            //错误
            baseData.state = ReqState.Error
        }
        return baseData
    }


    /**
     * Post请求构建表单参数提交
     *
     * @return
     */
    protected fun jsonRequest(): JsonMap {
        return JsonMap()
    }

    /**
     * Post请求构建表单参数提交
     *
     * @return
     */
    inner class JsonMap : HashMap<Any, Any>() {

        fun p(key: Any?, value: Any?): JsonMap {
            if (key == null || value == null) {
                return this
            }
            put(key, value)
            return this
        }

        //最终生成表单参数
        suspend fun body(): RequestBody {
            return jsonRequestBody(this)
        }

        //最终生成表单参数，且带回调的方法
        fun body(callback: Callback<RequestBody>?) {
            IOUtils.ioScope.launch(Dispatchers.IO) {
                val requestBody = body()
                withContext(Dispatchers.Main) {
                    callback?.call(requestBody)
                }
            }
        }
    }

    protected fun jsonRequestBody(map: Map<Any, Any>?): RequestBody {
        if (map == null || map.isEmpty()) {
            return "".toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())
        }
        val string = GsonBuilder().create().toJson(map)
        return string.toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())
    }

}