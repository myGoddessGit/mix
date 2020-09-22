package com.xcj.luck.rx

/**
 * @author cyl
 * @date 2020/8/4
 */
object SchedulerUtils {
    fun <T> ioToMain(): IoMainScheduler<T>{
        return IoMainScheduler()
    }
}