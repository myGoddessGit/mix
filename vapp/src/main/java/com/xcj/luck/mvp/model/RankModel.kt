package com.xcj.luck.mvp.model

import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.net.RetrofitManager
import com.xcj.luck.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * @author cyl
 * @date 2020/8/4
 */
class RankModel {

    fun requestRankList(apiUrl: String): Observable<HomeBean.Issue>{

        return RetrofitManager.service.getIssueData(apiUrl)
            .compose(SchedulerUtils.ioToMain())
    }
}