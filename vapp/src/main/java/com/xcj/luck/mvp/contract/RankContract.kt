package com.xcj.luck.mvp.contract

import com.xcj.luck.base.IBaseView
import com.xcj.luck.base.IPresenter
import com.xcj.luck.mvp.model.bean.HomeBean

/**
 * @author cyl
 * @date 2020/8/4
 */
interface RankContract {

    interface View: IBaseView {

        /**
         * 设置排行榜的数据
         */
        fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter: IPresenter<View>{
        /**
         * 获取 TabInfo
         */
        fun requestRankList(apiUrl: String)
    }
}