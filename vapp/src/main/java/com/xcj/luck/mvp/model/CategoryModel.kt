package com.xcj.luck.mvp.model

import com.xcj.luck.mvp.model.bean.CategoryBean
import com.xcj.luck.net.RetrofitManager
import com.xcj.luck.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * @author cyl
 * @date 2020/8/4
 */
class CategoryModel{
    /**
     * 获取分类消息
     */
    fun getCategoryData(): Observable<ArrayList<CategoryBean>>{
        return RetrofitManager.service.getCategory()
            .compose(SchedulerUtils.ioToMain())
    }
}