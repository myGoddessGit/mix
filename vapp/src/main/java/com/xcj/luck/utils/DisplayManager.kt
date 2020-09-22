package com.xcj.luck.utils

import android.content.Context
import android.util.DisplayMetrics

/**
 * @author cyl
 * @date 2020/7/31
 */
object DisplayManager {
    init {

    }
    private var displayMetrics : DisplayMetrics? = null
    private var screenWidth: Int? = null
    private var screenHeight: Int? = null
    private var screenDpi: Int? = null

    fun init(context: Context){
        displayMetrics = context.resources.displayMetrics
        screenWidth = displayMetrics?.widthPixels
        screenHeight = displayMetrics?.heightPixels
        screenDpi = displayMetrics?.densityDpi
    }
    private const val STANDARD_WIDTH = 1080
    private const val STANDARD_HEIGHT = 1920

    fun getScreenWidth(): Int? {
        return screenWidth
    }

    fun getScreenHeight(): Int? {
        return screenHeight
    }
    /**
     * dip转px
     */
    fun dip2px(dipValue: Float): Int? {
        val scale = displayMetrics?.density
        return (dipValue * scale!! + 0.5f).toInt()
    }
}