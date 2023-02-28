package com.common.net

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val TAG = "okhttp"

object RetrofitUtil {

    private var mRetrofit: Retrofit? = null

    private val mOkClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .followRedirects(false)
        .cookieJar(LocalCookieJar())
        .addInterceptor(SignInterceptor())
        .addInterceptor(HttpLoggingInterceptor { message -> Log.d(TAG, "log: ${unicodeToString(message)}") }
        .setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()


    /**
     * unicode转string
     */
    private fun unicodeToString(str: String): String {
        var str = str
        val pattern: Pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))")
        val matcher: Matcher = pattern.matcher(str)
        var ch: Char
        while (matcher.find()) {
            ch = matcher.group(2).toInt(16).toChar()
            str = str.replace(matcher.group(1), ch.toString() + "")
        }
        return str
    }

   open fun initRetrofit(): RetrofitUtil {
        mRetrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .client(mOkClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return this
    }

    fun <T> getService(serviceClass: Class<T>): T {
        if (mRetrofit == null) {
            throw UninitializedPropertyAccessException("Retrofit必须初始化")
        } else {
            return mRetrofit?.create(serviceClass)!!
        }
    }
}