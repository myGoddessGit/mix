package com.xcj.luck

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.multidex.MultiDex
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.RefWatcher
import com.xcj.luck.utils.DisplayManager
import kotlin.properties.Delegates

/**
 * @author cyl
 * @date 2020/7/31
 */
open class MyApplication : Application(){

    private var refWatcher: RefWatcher? = null

    companion object {
        private val TAG = "Myapplication"
        var context: Context by Delegates.notNull()

        fun getRefWatcher(context: Context): RefWatcher? {
            val myApplication = context.applicationContext as MyApplication
            return myApplication.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        DisplayManager.init(this)
        initConfig()
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        MultiDex.install(this)
    }

    private fun initConfig(){
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // 隐藏线程信息
            .methodCount(0)
            .methodOffset(7)
            .tag("luck")
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private val mActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks{

        override fun onActivityPaused(activity: Activity?) {

        }

        override fun onActivityResumed(activity: Activity?) {

        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

        }

        override fun onActivityStopped(activity: Activity?) {

        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
        }

    }
}