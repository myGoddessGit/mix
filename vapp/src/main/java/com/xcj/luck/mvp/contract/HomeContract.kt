package com.xcj.luck.mvp.contract

import com.xcj.luck.base.IBaseView
import com.xcj.luck.mvp.model.bean.HomeBean

/**
 * @author cyl
 * @date 2020/8/4
 */
interface HomeContract {

    interface View: IBaseView{
        /**
         * 设置第一次请求的数据
         */
        fun setHomeData(homeBean: HomeBean)

        /**
         * 设置加载更多数据
         */
        fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>)

        /**
         * 显示错误信息
         */
        fun showError(msg: String, errorCode: Int)
    }
    interface Presenter {
        /**
         * 获取首页精选数据
         */
        fun requestHomeData(num: Int)

        /**
         * 加载更多数据
         */
        fun loadMoreData()
    }
}