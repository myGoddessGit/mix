package com.xcj.luck.base

/**
 * @author cyl
 * @date 2020/8/4
 */
interface IPresenter <in V: IBaseView>{

    fun attachView(mRootView: V)

    fun detachView()
}