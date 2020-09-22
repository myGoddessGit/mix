package com.xcj.luck.mvp.contract

import com.xcj.luck.base.IBaseView
import com.xcj.luck.base.IPresenter
import com.xcj.luck.mvp.model.bean.CategoryBean

/**
 * @author cyl
 * @date 2020/8/4
 *   分类模块 的锲约类
 */
interface CategoryContract {

    interface View: IBaseView {

        /**
         * 显示分类的信息
         */
        fun showCategory(categoryList: ArrayList<CategoryBean>)

        /**
         * 显示错误的信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter: IPresenter<View>{

        /**
         * 获取分类的信息
         */
        fun getCategoryData()
    }
}