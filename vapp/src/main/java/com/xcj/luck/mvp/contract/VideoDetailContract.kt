package com.xcj.luck.mvp.contract

import com.xcj.luck.base.IBaseView
import com.xcj.luck.base.IPresenter
import com.xcj.luck.mvp.model.bean.HomeBean

/**
 * @author cyl
 * @date 2020/8/4
 */
interface VideoDetailContract {

    interface View: IBaseView {

        fun setVideo(url: String)

        fun setVideoInfo(itemInfo: HomeBean.Issue.Item)

        fun setBackground(url: String)

        fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>)

        fun setErrorMsg(errorMsg: String)
    }

    interface Presenter: IPresenter<View>{

        /**
         * 加载视频信息
         */
        fun loadVideoInfo(itemInfo: HomeBean.Issue.Item)

        /**
         * 请求相关的视频数据
         */
        fun requestRelatedVideo(id: Long)
    }
}