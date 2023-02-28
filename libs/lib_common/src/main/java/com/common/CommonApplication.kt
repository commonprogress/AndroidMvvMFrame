package com.common


import android.app.Application
import android.content.Context
import android.util.Log
import com.google.auto.service.AutoService
import com.base.commonality.utils.IOUtils
import com.common.utils.ProcessUtils
import com.common.utils.log
import com.base.commonality.BaseAppliction
import com.common.net.RetrofitUtil
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import kotlinx.coroutines.*

class CommonApplication : BaseAppliction() {

    companion object {
        /**
         * 当前手机时间和服务器时间的偏移量
         *
         * 直播PK用到
         */
        var currentTimeDeviation: Long = 0
    }

    init {
//        //设置全局的Header构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
//            LoadingRefreshHeader(context)
//        }
//        //设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
//            LoadingRefreshFooter(context)
//        }
    }

    override fun onCreate() {
        super.onCreate()
        initMainDesk()
    }

    override fun onTerminate() {
        super.onTerminate()
        IOUtils.cancel()
    }

    /**
     * 需要立即初始化的放在这里
     */
    private fun initMainDesk() {
        initMMKV()
        initARouter()
        initRetrofitUtil()
        initFileDownloader()
        Log.e("initMainDesk", "initMainDesk -->> init complete")
    }


    /**
     * 文件下载引擎 初始化
     */
    private fun initFileDownloader(): String {
//        Log.e("文件下载引擎 初始化", "文件下载引擎 初始化")
        FileDownloader.setupOnApplicationOnCreate(mContext as Application)
            .connectionCreator(
                FileDownloadUrlConnection.Creator(
                    FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15000) // set connection timeout.
                        .readTimeout(15000) // set read timeout.
                )
            )
            .commit()
        log("CApplication 文件下载引擎 -->> init complete")
        return "CApplication 文件下载引擎 -->> init complete"
    }


    private fun initRetrofitUtil(): String {
//        Log.e("文件下载引擎 初始化", "文件下载引擎 初始化")
        RetrofitUtil.initRetrofit()
        return "CApplication 文件下载引擎 -->> init complete"
    }


}