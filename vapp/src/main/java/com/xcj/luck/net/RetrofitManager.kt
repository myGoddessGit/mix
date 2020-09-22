package com.xcj.luck.net

import com.xcj.luck.MyApplication
import com.xcj.luck.api.ApiService
import com.xcj.luck.api.UrlConstant
import com.xcj.luck.utils.AppUtils
import com.xcj.luck.utils.NetworkUtil
import com.xcj.luck.utils.Preference
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author cyl
 * @date 2020/8/4
 */
object RetrofitManager {

    val service: ApiService by lazy (LazyThreadSafetyMode.SYNCHRONIZED) {
        getRetrofit().create(ApiService::class.java)
    }

    private var token: String by Preference("token", "")

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
                .addQueryParameter("deviceModel", AppUtils.getMobileModel())
                .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .header("token", token)
                .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * 设置缓存
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isNetWorkAvailable(MyApplication.context)){
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isNetWorkAvailable(MyApplication.context)){
                val maxAge = 0
                response.newBuilder()
                    .header("Cache-Control", "public, max-age" + maxAge)
                    .removeHeader("Retrofit")
                    .build()
            } else {
                val maxState = 60 * 60 * 24 * 28
                response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxState)
                    .removeHeader("nyn")
                    .build()
            }
            response
        }
    }

    private fun getRetrofit(): Retrofit {
        // 获取retrofit的实例
        return Retrofit.Builder()
            .baseUrl(UrlConstant.BASE_URL)
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val cacheFile = File(MyApplication.context.cacheDir, "cache")
        val cache  = Cache(cacheFile, 1024 * 1024 * 50) // 50MB 缓存大小

        return OkHttpClient.Builder()
            .addInterceptor(addQueryParameterInterceptor()) // 参数添加
            .addInterceptor(addHeaderInterceptor()) // token过滤
            .addInterceptor(httpLoggingInterceptor) // 日志
            .cache(cache) // 添加缓存
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L,TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()
    }
}