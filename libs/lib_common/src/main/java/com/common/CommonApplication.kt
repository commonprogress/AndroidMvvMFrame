package com.common


import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import com.common.utils.IOUtils
import com.common.utils.ProcessUtils
import com.common.utils.log
import com.base.commonality.BaseAppliction
import com.base.commonality.lifecycle.ApplicationLifecycle
import com.base.commonality.lifecycle.InitDepend
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import kotlinx.coroutines.*

@AutoService(ApplicationLifecycle::class)
class CommonApplication : BaseAppliction(), ApplicationLifecycle {

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

    override fun onAttachBaseContext(context: Context) {

    }

    override fun onCreate(application: Application) {
    }

    /**
     * 同[BaseAppliction.onTerminate]
     * @param BaseAppliction Application
     */
    override fun onTerminate(application: Application) {
        IOUtils.cancel()
    }

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    override fun initByBackstage() {

    }

    /**
     * 需要立即初始化的放在这里
     */
    override fun initByFrontDesk(): InitDepend {

        val worker = mutableListOf<() -> String>()
        val main = mutableListOf<() -> String>()
        // 以下只需要在主进程当中初始化 按需要调整
        if (ProcessUtils.isMainProcess(mContext)) {
            main.add { initMMKV() }
            main.add { initARouter() }
            main.add { initFileDownloader() }
        }
        return InitDepend(main, worker)
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



}