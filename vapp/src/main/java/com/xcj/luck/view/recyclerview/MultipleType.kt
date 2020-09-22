package com.xcj.luck.view.recyclerview

/**
 * @author cyl
 * @date 2020/8/3
 */
interface MultipleType<in T>{
    fun getLayoutId(item: T, position: Int): Int
}
