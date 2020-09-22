package com.xcj.luck.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.lang.Error

/**
 * @author cyl
 * @date 2020/7/31
 */
class AppUtils private constructor(){

    init {
        throw Error("DO NOT NEED INSTANTIATE!")
    }
    companion object {
        fun getVerName(context: Context): String {
            var verName = ""
            try{
                val packageName = context.packageName
                verName = context.packageManager.getPackageInfo(packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException){
                e.printStackTrace()
            }
            return verName
        }

        fun getMobileModel(): String {
            var model: String? = Build.MODEL
            model = model?.trim {it <= ' '} ?: ""
            return model
        }
    }
}