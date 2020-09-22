package com.xcj.luck.mvp.model

import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.net.RetrofitManager
import com.xcj.luck.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * @author cyl
 * @date 2020/8/4
 */
class SearchModel {

    /**
     * 请求热门关键词的数据
     */
    fun requestHotWordData(): Observable<ArrayList<String>>{
        return RetrofitManager.service.getHotWord()
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 搜素关键词返回的结果
     */
    fun getSearchResult(words: String): Observable<HomeBean.Issue>{
        return RetrofitManager.service.getSearchData(words)
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多的数据
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue>{
        return RetrofitManager.service.getIssueData(url)
            .compose(SchedulerUtils.ioToMain())
    }
}