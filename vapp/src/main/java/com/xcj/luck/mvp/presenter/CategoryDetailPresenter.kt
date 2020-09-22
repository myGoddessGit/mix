package com.xcj.luck.mvp.presenter

import com.xcj.luck.base.BasePresenter
import com.xcj.luck.mvp.contract.CategoryDetailContract
import com.xcj.luck.mvp.model.CategoryDetailModel

/**
 * @author cyl
 * @date 2020/8/4
 */
class CategoryDetailPresenter : BasePresenter<CategoryDetailContract.View>(), CategoryDetailContract.Presenter {

    private val categoryDetailModel by lazy {
        CategoryDetailModel()
    }

    private var nextPageUrl: String? = null

    /**
     * 获取分类详情的列表信息
     */
    override fun getCategoryDetailList(id: Long) {
        checkViewAttached()
        val disposable = categoryDetailModel.getCategoryDetailList(id)
            .subscribe({
                issue ->
                mRootView?.apply {
                    nextPageUrl = issue.nextPageUrl
                    setCateDetailList(issue.itemList)
                }
            }, {
                throwable ->
                mRootView?.apply {
                    showError(throwable.toString())
                }
            })
        addSubscription(disposable)
    }

    /**
     * 加载更多数据
     */
    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            categoryDetailModel.loadMoreData(it)
                .subscribe({ issue ->
                    mRootView?.apply {
                        nextPageUrl = issue.nextPageUrl
                        setCateDetailList(issue.itemList)
                    }
                }, {
                    throwable ->
                        mRootView?.apply {
                            showError(throwable.toString())
                      }
                })
        }
        disposable?.let { addSubscription(it) }
    }
}