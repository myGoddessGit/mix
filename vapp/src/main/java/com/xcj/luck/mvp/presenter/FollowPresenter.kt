package com.xcj.luck.mvp.presenter

import com.xcj.luck.base.BasePresenter
import com.xcj.luck.mvp.contract.FollowContract
import com.xcj.luck.mvp.model.FollowModel
import com.xcj.luck.net.exception.ExceptionHandle

/**
 * @author cyl
 * @date 2020/8/4
 */
class FollowPresenter : BasePresenter<FollowContract.View>(), FollowContract.Presenter {

    private val followModel by lazy {
        FollowModel()
    }

    private var nextPageUrl: String? = null
    /**
     * 请求关注数据
     */
    override fun requestFollowList() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = followModel.requestFollowList()
            .subscribe({issue ->
                mRootView?.apply {
                    dismissLoading()
                    nextPageUrl = issue.nextPageUrl
                    setFollowInfo(issue)
                }
            }, {
                t ->
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    /**
     * 加载更多
     */
    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            followModel.loadMoreData(it)
                .subscribe({issue ->
                    mRootView?.apply {
                        nextPageUrl = issue.nextPageUrl
                        setFollowInfo(issue)
                    }
                },{
                    t ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })
        }
        if (disposable != null){
            addSubscription(disposable)
        }
    }

}