package com.xcj.luck.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.reflect.Field

/**
 * @author cyl
 * @date 2020/7/31
 */
object CleanLeakUtil {
    fun fixInputMethodManagerLeak(desContext: Context?){
        if (desContext == null){
            return
        }
        val inputMethodManager = desContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val viewArray = arrayOf("mCurRootView", "mServedView", "mNextServedView");
        var field: Field
        var fieldObject: Any?
        for (view in viewArray){
            try {
                field = inputMethodManager.javaClass.getDeclaredField(view)
                if (!field.isAccessible){
                    field.isAccessible = true
                }
                fieldObject = field.get(inputMethodManager)
                if (fieldObject != null && fieldObject is View){
                    val fileView = fieldObject as View?
                    if (fileView!!.context  === desContext){ // 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null) // 置空 破坏掉path to gc 节点
                    } else {
                        break
                    }
                }
            } catch (t: Throwable){
                t.printStackTrace()
            }
        }
    }
}