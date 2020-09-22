package com.xcj.luck.mvp.contract

import com.xcj.luck.base.IBaseView
import com.xcj.luck.base.IPresenter
import com.xcj.luck.mvp.model.bean.HomeBean

/**
 * @author cyl
 * @date 2020/8/4
 */
interface FollowContract {

    interface View: IBaseView {
        /**
         * 设置关注信息数据
         */
        fun setFollowInfo(issue: HomeBean.Issue)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter: IPresenter<View> {

        /**
         * 获取List
         */
        fun requestFollowList()

        /**
         * 加载更多
         */
        fun loadMoreData()
    }
}