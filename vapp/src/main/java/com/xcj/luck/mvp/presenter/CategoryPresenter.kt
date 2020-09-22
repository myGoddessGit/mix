package com.xcj.luck.mvp.presenter

import com.xcj.luck.base.BasePresenter
import com.xcj.luck.mvp.contract.CategoryContract
import com.xcj.luck.mvp.model.CategoryModel
import com.xcj.luck.net.exception.ExceptionHandle

/**
 * @author cyl
 * @date 2020/8/4
 */
class CategoryPresenter : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter{

    private val categoryModel: CategoryModel by lazy {
        CategoryModel()
    }

    /**
     * 获取分类
     */
    override fun getCategoryData() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = categoryModel.getCategoryData()
            .subscribe({categoryList ->
                mRootView?.apply {
                    dismissLoading()
                    showCategory(categoryList)
                }
            }, {
                t ->
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

}