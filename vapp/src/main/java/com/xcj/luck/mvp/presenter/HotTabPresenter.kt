package com.xcj.luck.mvp.presenter

import com.xcj.luck.base.BasePresenter
import com.xcj.luck.mvp.contract.HotTabContract
import com.xcj.luck.mvp.model.HotTabModel
import com.xcj.luck.net.exception.ExceptionHandle

/**
 * @author cyl
 * @date 2020/8/4
 */
class HotTabPresenter : BasePresenter<HotTabContract.View>(), HotTabContract.Presenter {

    private val hotTabModel by lazy {
        HotTabModel()
    }

    override fun getTabInfo() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = hotTabModel.getTabInfo()
            .subscribe({
                tabInfo ->
                mRootView?.setTabInfo(tabInfo)
            }, {
                t ->
                mRootView?.showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
            })
        addSubscription(disposable)
    }

}