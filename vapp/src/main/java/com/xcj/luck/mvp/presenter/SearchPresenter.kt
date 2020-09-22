package com.xcj.luck.mvp.presenter

import com.xcj.luck.base.BasePresenter
import com.xcj.luck.mvp.contract.SearchContract
import com.xcj.luck.mvp.model.SearchModel
import com.xcj.luck.net.exception.ExceptionHandle

/**
 * @author cyl
 * @date 2020/8/4
 */
class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {

    private var nextPageUrl: String? = null

    private val searchModel by lazy {
        SearchModel()
    }
    /**
     * 搜索热门关键词
     */
    override fun requestHotWordData() {
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(disposable = searchModel.requestHotWordData()
            .subscribe({string ->
                mRootView?.apply {
                    setHotWordData(string)
                }
            }, {
                t ->
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            }))
    }

    /**
     * 查询关键词
     */
    override fun querySearchData(words: String) {
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(disposable = searchModel.getSearchResult(words)
            .subscribe({
                issue ->
                mRootView?.apply {
                    dismissLoading()
                    if (issue.count > 0 && issue.itemList.size > 0){
                        nextPageUrl = issue.nextPageUrl
                        setSearchResult(issue)
                    } else {
                        setEmptyView()
                    }
                }
            }, {
                t ->
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            }))
    }

    /**
     * 加载更多数据
     */
    override fun loadMoreData() {
        checkViewAttached()
        nextPageUrl?.let {
            addSubscription(disposable = searchModel.loadMoreData(it)
                .subscribe({
                    issue ->
                    mRootView?.apply {
                        nextPageUrl = issue.nextPageUrl
                        setSearchResult(issue)
                    }
                },{
                    t ->
                    mRootView?.apply {
                        // 异常处理
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                }))
        }
    }

}