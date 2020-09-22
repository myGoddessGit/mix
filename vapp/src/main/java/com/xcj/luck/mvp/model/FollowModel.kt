package com.xcj.luck.mvp.model

import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.net.RetrofitManager
import com.xcj.luck.rx.SchedulerUtils
import io.reactivex.Observable


/**
 * @author cyl
 * @date 2020/8/4
 */
class FollowModel {

    /**
     * 获取关注信息
     */
    fun requestFollowList(): Observable<HomeBean.Issue>{

        return RetrofitManager.service.getFollowInfo()
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue>{
        return RetrofitManager.service.getIssueData(url)
            .compose(SchedulerUtils.ioToMain())
    }
}