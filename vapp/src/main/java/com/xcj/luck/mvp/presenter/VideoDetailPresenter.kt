package com.xcj.luck.mvp.presenter

import com.xcj.luck.MyApplication
import com.xcj.luck.base.BasePresenter
import com.xcj.luck.mvp.contract.VideoDetailContract
import com.xcj.luck.mvp.model.VideoDetailModel
import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.net.exception.ExceptionHandle
import com.xcj.luck.utils.DisplayManager
import com.xcj.luck.utils.NetworkUtil

/**
 * @author cyl
 * @date 2020/8/4
 */
class VideoDetailPresenter : BasePresenter<VideoDetailContract.View>(), VideoDetailContract.Presenter {

    private val videoDetailModel : VideoDetailModel by lazy {
        VideoDetailModel()
    }

    /**
     * 加载视频数据
     */
    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {
        val playInfo = itemInfo.data?.playInfo
        val netType = NetworkUtil.isWifi(MyApplication.context)
        checkViewAttached()
        if (playInfo!!.size > 1){
            if (netType) {
                for (i in playInfo){
                    if (i.type == "high"){
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        break
                    }
                }
            } else {
                for (i in playInfo){
                    if (i.type == "normal"){
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        break
                    }
                }
            }
        } else {
            mRootView?.setVideo(itemInfo.data.playUrl)
        }
//        val backgroundUrl = itemInfo.data.cover.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2x(250f)!!}x${DisplayManager.getScreenWidth()}"
//        backgroundUrl.let { mRootView?.setBackground(it) }
        mRootView?.setVideoInfo(itemInfo)
    }

    override fun requestRelatedVideo(id: Long) {
        mRootView?.showLoading()
        val disposable = videoDetailModel.requestRelatedData(id)
            .subscribe({
                issue ->
                mRootView?.apply {
                    dismissLoading()
                    setRecentRelatedVideo(issue.itemList)
                }
            }, {
                t ->
                mRootView?.apply {
                    dismissLoading()
                    setErrorMsg(ExceptionHandle.handleException(t))
                }
            })
        addSubscription(disposable)
    }

}