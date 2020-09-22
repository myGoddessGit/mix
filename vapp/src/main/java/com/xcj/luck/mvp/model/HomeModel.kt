package com.xcj.luck.mvp.model

import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.net.RetrofitManager
import com.xcj.luck.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * @author cyl
 * @date 2020/8/4
 */
class HomeModel {

    /**
     * 获取首页banner数据
     */
    fun requestHomeData(num: Int): Observable<HomeBean>{
        return RetrofitManager.service.getFirstHomeData(num)
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url: String): Observable<HomeBean>{
        return RetrofitManager.service.getMoreHomeData(url)
            .compose(SchedulerUtils.ioToMain())
    }
}