package com.xcj.luck.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.RuntimeException

/**
 * @author cyl
 * @date 2020/8/4
 */
open class BasePresenter <T: IBaseView> : IPresenter<T> {

    var mRootView: T? = null
        private set
    private var compositeDisposable = CompositeDisposable()

    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    override fun detachView() {
        mRootView = null
        // Activity结束销毁时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed){
            compositeDisposable.clear()
        }
    }
    private val isViewAttached: Boolean
        get() = mRootView != null

    fun checkViewAttached(){
        if (!isViewAttached) throw RuntimeException("Please call IPresenter.attachView(IBaseView) before +  requesting data to the IPresenter")
    }

    fun addSubscription(disposable: Disposable){
        compositeDisposable.add(disposable)
    }
}