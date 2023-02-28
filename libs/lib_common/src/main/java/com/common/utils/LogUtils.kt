package com.common.utils

import android.os.Environment
import android.util.Log
import androidx.viewbinding.BuildConfig
import com.common.utils.LogUtils.logEnable
import com.base.commonality.BaseAppliction
import com.base.commonality.utils.IOUtils
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.*

/**
 * 类描述: 日志工具类
 * 功能：1.release版本没有日志输出
 * 2.日志显示类名，方法名，代码行数等信息
 * 3.捕捉到崩溃日志保存在本地，每天一个文件，方便查看
 * 创建人: lz
 * 创建时间: 2016/11/22
 * 修改备注:
 */
object LogUtils {
    private const val SEPARATOR = ","

    private var path = "/log"
    private var filePath: String? = null

    /**
     * Get default tag name
     */
    const val defaultTag = "DeBugLog"

    /**
     * 是否打印日志，全局设置
     * 默认debug模式下打印
     */
    var logEnable: Boolean = false

    /**
     * Get default tag name
     */
    fun getDefaultTag(stackTraceElement: StackTraceElement): String {
        val fileName = stackTraceElement.fileName
        val stringArray = fileName.split("\\.").toTypedArray()
        val tag = stringArray[0]
        return "$defaultTag-$tag"
    }

    /**
     * get stack info
     */
    fun getLogInfo(stackTraceElement: StackTraceElement): String {
        val logInfoStringBuilder = StringBuilder()
        // class name
        val fileName = stackTraceElement.fileName
        // code line
        val lineNumber = stackTraceElement.lineNumber
        logInfoStringBuilder.append("(").append(fileName).append(":")/*.append(SEPARATOR)
        logInfoStringBuilder.append("line:")*/
        logInfoStringBuilder.append(lineNumber)
        logInfoStringBuilder.append(") ")
        return logInfoStringBuilder.toString()
    }

    /**
     * 日志写入文件
     */
    fun writeTxtToFile(content: String) {
        try {
            if (filePath == null) {
                filePath = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    BaseAppliction.mContext.getExternalFilesDir(null)?.path + path
                } else {// 如果SD卡不存在，就保存到本应用的目录下
                    BaseAppliction.mContext.filesDir.path + path
                }
            }

            if (filePath != null) {
                val fileName = DateUtils.getDateYearMonthDay() + ".txt"
                val file = File(filePath)
                if (!file.exists()) {
                    file.mkdirs()
                }
                val file1 = File(filePath + File.separator + fileName)
                if (!file1.exists()) {
                    file1.createNewFile()
                }
                IOUtils.ioScope.launch {
                    var raf: FileOutputStream? = null
                    var out: OutputStreamWriter? = null
                    try {
                        raf = FileOutputStream(file1, true)
                        out = OutputStreamWriter(raf, "GBK")
                        out.write(content + "\r\n")
                    } catch (e: Exception) {
                        log("写入文件报错:${e.message}")
                    } finally {
                        out?.close()
                        raf?.close()
                    }
                }

            }
        } catch (e: Exception) {
            log("写入文件报错")
        }
    }
}

/**
 * @param tag
 * @param message
 */
fun logV(tag: String = LogUtils.defaultTag, message: String) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[4]
        val mess = LogUtils.getLogInfo(stackTraceElement) + message
        Log.v(tag, mess)
    }
}


/**
 * @param tag
 * @param message
 */
fun logD(message: String) {
    logD(LogUtils.defaultTag, message)
}

/**
 * @param tag
 * @param message
 */
fun logD(tag: String = LogUtils.defaultTag, message: String) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[4]
        val mess = LogUtils.getLogInfo(stackTraceElement) + message
        Log.d(tag, mess)
    }
}

/**
 * @param tag
 * @param message
 */
fun logI(tag: String = LogUtils.defaultTag, message: String) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[4]
        val mess = LogUtils.getLogInfo(stackTraceElement) + message
//        Log.i(tag, mess)
    }
}

/**
 * @param tag
 * @param message
 */
fun logW(tag: String = LogUtils.defaultTag, message: String) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[4]
        val mess = LogUtils.getLogInfo(stackTraceElement) + message
//        Log.w(tag, mess)
    }
}

/**
 * @param tag
 * @param message
 */
fun logE(tag: String = LogUtils.defaultTag, message: String) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[4]
        val mess = LogUtils.getLogInfo(stackTraceElement) + message
        Log.e(tag, mess)
    }
}

/**
 * 平时调试使用
 *
 * @param message
 */
fun log(message: String) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[4]
        val mess = LogUtils.getLogInfo(stackTraceElement) + message
        val tag = "DeBugLog"
        Log.e(tag, mess)
    }
}

/**
 * 平时调试使用
 * 把日志写入文件
 *
 * @param message
 */
fun logWrite(message: String) {
    val stackTraceElement = Thread.currentThread().stackTrace[4]
    val mess = LogUtils.getLogInfo(stackTraceElement) + message
    val tag = "DeBugLog"
    val time = DateUtils.getCurrentDateFormatString(DateUtils.FORMAT1)
    LogUtils.writeTxtToFile("$time:$tag:$mess")
    if (BuildConfig.DEBUG || logEnable) {
        Log.e(tag, mess)
    }
}


/**
 * 平时调试使用
 * 把日志写入文件
 *
 * @param message
 */
fun logDWrite(message: String) {
    val stackTraceElement = Thread.currentThread().stackTrace[4]
    val mess = LogUtils.getLogInfo(stackTraceElement) + message
    val tag = "DeBugLog"
    val time = DateUtils.getCurrentDateFormatString(DateUtils.FORMAT1)
    LogUtils.writeTxtToFile("$time:$tag:$mess")
    if (BuildConfig.DEBUG || logEnable) {
        Log.d(tag, mess)
    }
}


