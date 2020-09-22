package com.xcj.luck.mvp.model

import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.net.RetrofitManager
import com.xcj.luck.rx.SchedulerUtils
import io.reactivex.Observable


/**
 * @author cyl
 * @date 2020/8/4
 * 分类详情的Model
 */
class CategoryDetailModel {

    /**
     * 获取分类下的List数据
     */
    fun getCategoryDetailList(id: Long): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getCategoryDetailList(id)
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue>{
        return RetrofitManager.service.getIssueData(url)
            .compose(SchedulerUtils.ioToMain())
    }
}