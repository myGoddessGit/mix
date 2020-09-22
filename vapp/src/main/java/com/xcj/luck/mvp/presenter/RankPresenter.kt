package com.xcj.luck.mvp.presenter

import com.xcj.luck.base.BasePresenter
import com.xcj.luck.mvp.contract.RankContract
import com.xcj.luck.mvp.model.RankModel
import com.xcj.luck.net.exception.ExceptionHandle

/**
 * @author cyl
 * @date 2020/8/4
 */
class RankPresenter : BasePresenter<RankContract.View>(), RankContract.Presenter {

    private val rankModel by lazy {
        RankModel()
    }

    override fun requestRankList(apiUrl: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = rankModel.requestRankList(apiUrl)
            .subscribe({
                issue ->
                mRootView?.apply {
                    dismissLoading()
                    setRankList(issue.itemList)
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