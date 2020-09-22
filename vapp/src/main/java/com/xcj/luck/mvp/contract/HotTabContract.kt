package com.xcj.luck.mvp.contract

import com.xcj.luck.base.IBaseView
import com.xcj.luck.base.IPresenter
import com.xcj.luck.mvp.model.bean.TabInfoBean

/**
 * @author cyl
 * @date 2020/8/4
 */
interface HotTabContract {

    interface View: IBaseView {
        /**
         * 设置 TabInfo
         */
        fun setTabInfo(tabInfoBean: TabInfoBean)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter: IPresenter<View> {
        /**
         * 获取 TabInfo
         */
        fun getTabInfo()
    }
}