package com.xcj.luck.mvp.model

import com.xcj.luck.mvp.model.bean.TabInfoBean
import com.xcj.luck.net.RetrofitManager
import com.xcj.luck.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * @author cyl
 * @date 2020/8/4
 */
class HotTabModel {

    /**
     * 获取TabInfo
     */
    fun getTabInfo(): Observable<TabInfoBean>{
        return RetrofitManager.service.getRankList()
            .compose(SchedulerUtils.ioToMain())
    }
}