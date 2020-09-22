package com.xcj.luck.mvp.presenter

import com.xcj.luck.base.BasePresenter
import com.xcj.luck.mvp.contract.HomeContract
import com.xcj.luck.mvp.model.HomeModel
import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.net.exception.ExceptionHandle

/**
 * @author cyl
 * @date 2020/8/4
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private var bannerHomeBean: HomeBean? = null

    private var nextPageUrl: String? = null

    private val homeModel: HomeModel by lazy {
        HomeModel()
    }

    /**
     * 获取首页精选数据 banner 加一页数据
     */
    override fun requestHomeData(num: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = homeModel.requestHomeData(num)
            .flatMap {
                homeBean ->
                // 过滤 Banner2
                val bannerItemList = homeBean.issueList[0].itemList
                bannerItemList.filter {
                    item ->
                    item.type == "banner2" || item.type == "horizontalScrollCard"
                }.forEach{
                    item ->
                    bannerItemList.remove(item) // 移除item
                }
                bannerHomeBean  = homeBean // 记录第一页时banner的数据
                // 根据nextPageUrl 请求下一页数据
                homeModel.loadMoreData(homeBean.nextPageUrl)
            }.subscribe({
                homeBean ->
                mRootView?.apply {
                    dismissLoading() // 关闭加载
                    nextPageUrl = homeBean.nextPageUrl
                    val newBannerItemList = homeBean.issueList[0].itemList
                    newBannerItemList.filter {
                        item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach {
                        item ->
                        newBannerItemList.remove(item) // 移除 item
                    }
                    // update Banner 长度
                    bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size
                    bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)
                    setHomeData(bannerHomeBean!!)
                }
            }, {
                t ->
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            homeModel.loadMoreData(it)
                .subscribe({ homeBean ->
                    mRootView?.apply {
                        val newItemList = homeBean.issueList[0].itemList
                        newItemList.filter {
                            item ->
                            item.type == "banner2" || item.type=="horizontalScrollCard"
                        }.forEach {
                            item ->
                            newItemList.remove(item)
                        }
                        nextPageUrl = homeBean.nextPageUrl
                        setMoreData(newItemList)
                    }
                }, {
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