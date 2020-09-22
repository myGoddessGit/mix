package com.xcj.luck.mvp.contract

import com.xcj.luck.base.IBaseView
import com.xcj.luck.base.IPresenter
import com.xcj.luck.mvp.model.bean.HomeBean

/**
 * @author cyl
 * @date 2020/8/4
 */
interface SearchContract {

    interface View: IBaseView{
        /**
         * 设置热门关键词数据
         */
        fun setHotWordData(string: ArrayList<String>)

        /**
         * 设置搜索关键词返回的结果
         */
        fun setSearchResult(issue: HomeBean.Issue)

        /**
         * 关闭软键盘
         */
        fun closeSoftKeyboard()

        /**
         * 设置空View
         */
        fun setEmptyView()

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter: IPresenter<View> {
        /**
         * 获取热门关键词的数据
         */
        fun requestHotWordData()

        /**
         * 查询搜索
         */
        fun querySearchData(words: String)

        /**
         * 加载更多
         */
        fun loadMoreData()
    }
}