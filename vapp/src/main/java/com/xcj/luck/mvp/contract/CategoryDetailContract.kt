package com.xcj.luck.mvp.contract

import com.xcj.luck.base.IBaseView
import com.xcj.luck.base.IPresenter
import com.xcj.luck.mvp.model.bean.HomeBean

/**
 * @author cyl
 * @date 2020/8/4
 * 分类详情的锲约类
 */
interface CategoryDetailContract {

    interface View: IBaseView {

        /**
         * 设置列表数据
         */
        fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg: String)
    }

    interface Presenter: IPresenter<View>{

        fun getCategoryDetailList(id: Long)

        fun loadMoreData()
    }
}