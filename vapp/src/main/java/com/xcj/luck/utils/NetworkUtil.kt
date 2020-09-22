package com.xcj.luck.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

/**
 * @author cyl
 * @date 2020/7/31
 */
class NetworkUtil {

    companion object {
        private val TIMEOUT = 3000
        @SuppressLint("MissingPermission")
        @JvmStatic
        fun isNetWorkAvailable(context: Context) : Boolean {
            val manager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return !(null == info || !info.isAvailable)
        }
        @SuppressLint("MissingPermission")
        fun isWifi(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
        }
    }
}