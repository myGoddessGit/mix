package com.xcj.luck.ui.activity

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.xcj.luck.Constants
import com.xcj.luck.MyApplication
import com.xcj.luck.R
import com.xcj.luck.base.BaseActivity
import com.xcj.luck.glide.GlideApp
import com.xcj.luck.mvp.contract.VideoDetailContract
import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.mvp.presenter.VideoDetailPresenter
import com.xcj.luck.showToast
import com.xcj.luck.ui.adapter.VideoDetailAdapter
import com.xcj.luck.utils.CleanLeakUtil
import com.xcj.luck.utils.StatusBarUtil
import com.xcj.luck.utils.WatchHistoryUtils
import com.xcj.luck.view.VideoListener
import kotlinx.android.synthetic.main.activity_video_detail.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author cyl
 * @date 2020/8/5
 * 视频的详情
 */
@SuppressLint("SimpleDateFormat")
class VideoDetailActivity : BaseActivity(), VideoDetailContract.View{

    private var TAG = "VideoDetailActivity"
    companion object {
        const val IMG_TRANSITION = "IMG_TRANSITION"
        const val TRANSITION = "TRANSITION"
    }

    private val mPresenter by lazy { VideoDetailPresenter() }

    private val mAdapter by lazy { VideoDetailAdapter(this, itemList) }

    private val mFormat by lazy { SimpleDateFormat("yyyyMMddHHmmss") }
    /**
     * Item的详细数据
     */
    private lateinit var itemData: HomeBean.Issue.Item

    private var orientationUtils: OrientationUtils? = null
    private var itemList = ArrayList<HomeBean.Issue.Item>()
    private var isPlay: Boolean = false // 是否播放
    private var isPause: Boolean = false // 是否暂停

    private var isTransition: Boolean = false
    private var transition: Transition? = null
    private var mMaterialHeader: MaterialHeader? = null // 刷新的头部

    override fun layoutId(): Int {
        return R.layout.activity_video_detail
    }

    /**
     * 初始化View
     */
    override fun initView() {
        mPresenter.attachView(this)
        // 初始化过度动画
        initTransition()
        initVideoViewConfig()

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter // 设置Adapter
        // 相关视频Item的点击事件
        mAdapter.setOnItemDetailClick { mPresenter.loadVideoInfo(it) }
        // 状态栏透明和间处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, mVideoView)
        /**
         * 下拉刷新
         */
        // 内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {loadVideoInfo()}
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader?
        // 打开刷新区域的背景
        mMaterialHeader?.setShowBezierWave(true)
        // 设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)
    }

    /**
     * 初始化VideoView的相关配置
     */
    private fun initVideoViewConfig(){
        // 设置旋转 (旋转工具类)
        orientationUtils = OrientationUtils(this, mVideoView)
        // 是否旋转
        mVideoView.isRotateViewAuto = false
        // 是否可以滑动调整
        mVideoView.setIsTouchWiget(true)
        // 添加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        GlideApp.with(this)
            .load(itemData.data?.cover?.feed)
            .centerCrop()
            .into(imageView)
        mVideoView.thumbImageView = imageView
        mVideoView.setStandardVideoAllCallBack(object : VideoListener {
            // 准备播放
            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                // 当开始播放了，才能旋转和全屏
                orientationUtils?.isEnable = true
                isPlay = true
            }
            // 播放完成
            override fun onAutoComplete(url: String, vararg objects: Any) {
                super.onAutoComplete(url, *objects)
                Logger.d("$TAG : onAutoPlayComplete")
            }
            // 播放失败
            override fun onPlayError(url: String, vararg objects: Any) {
                super.onPlayError(url, *objects)
                showToast("播放失败")
            }
            // 进入全屏模式
            override fun onEnterFullscreen(url: String, vararg objects: Any) {
                super.onEnterFullscreen(url, *objects)
                Logger.d("$TAG : onEnterFullscreen")
            }
            // 退出全屏模式
            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                Logger.d("$TAG : onQuitFullscreen")
                orientationUtils?.backToProtVideo() // 列表返回样式判断
            }
        })
        // 设置返回按键功能
        mVideoView.backButton.setOnClickListener { onBackPressed() }
        // 设置全屏按键功能
        mVideoView.fullscreenButton.setOnClickListener{
            // 直接设置横屏
            orientationUtils?.resolveByClick()
            // 参数二的true 是隐藏actionBar， 参数三的true是隐藏statusBar
            mVideoView.startWindowFullscreen(this, true, true)
        }
        // 锁屏事件
        mVideoView.setLockClickListener{ _, lock ->
            orientationUtils?.isEnable = !lock
        }
    }

    override fun start() {

    }
    /**
     * 初始化数据
     */
    override fun initData() {
        itemData = intent.getSerializableExtra(Constants.BUNDLE_VIDEO_DATA) as HomeBean.Issue.Item
        isTransition = intent.getBooleanExtra(TRANSITION, false)
        saveWatchVideoHistoryInfo(itemData)
    }
    /**
     * 保存观看记录
     */
    private fun saveWatchVideoHistoryInfo(watchItem: HomeBean.Issue.Item) {
        // 保存之前要提前查询sp中是否有该值的记录, 存在就删除, 这样就可以保证搜索历史记录不会出现重复的值
        val historyMap = WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME, MyApplication.context) as Map<*,*>
        for ((key, _) in historyMap){
            if (watchItem == WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME, MyApplication.context, key as String)){
                WatchHistoryUtils.remove(Constants.FILE_WATCH_HISTORY_NAME, MyApplication.context, key)
            }
        }
        WatchHistoryUtils.putObject(Constants.FILE_WATCH_HISTORY_NAME, MyApplication.context, watchItem, "" + mFormat.format(Date()))
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        // 刷新完成
        mRefreshLayout.finishRefresh()
    }

    /**
     * 设置视频的URL
     */
    override fun setVideo(url: String) {
        Logger.d("$TAG : playUrl : $url")
        // 设置播放源
        mVideoView.setUp(url, false, "")
        // 开始自动播放
        mVideoView.startPlayLogic()
    }

    /**
     * 设置视频信息
     */
    override fun setVideoInfo(itemInfo: HomeBean.Issue.Item) {
        itemData = itemInfo
        mAdapter.addData(itemInfo)
        // 请求相关的最新等视频
        mPresenter.requestRelatedVideo(itemInfo.data?.id?: 0)
    }

    /**
     * 设置相关的视频数据
     */
    override fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {
        mAdapter.addData(itemList)
        this.itemList = itemList
    }

    /**
     * 设置背景颜色
     */
    override fun setBackground(url: String) {
//        GlideApp.with(this)
//            .load(url)
//            .centerCrop()
//            .format(DecodeFormat.PREFER_ARGB_8888)
//            .transition(DrawableTransitionOptions().crossFade())
//            .into(mVideoBackground)
    }

    override fun setErrorMsg(errorMsg: String) {

    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause){
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtils)
        }
    }

    /**
     * 加载视频信息
     */
    fun loadVideoInfo(){
        mPresenter.loadVideoInfo(itemData)
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this))
            return
        // 释放所有
        mVideoView.setStandardVideoAllCallBack(null)
        GSYVideoPlayer.releaseAllVideos()
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) run {
            super.onBackPressed()
        } else {
            finish()
            overridePendingTransition(R.anim.anim_out, R.anim.anim_in)
        }
    }

    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    override fun onDestroy() {
        CleanLeakUtil.fixInputMethodManagerLeak(this)
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
        mPresenter.detachView()
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null){
            mVideoView.fullWindowPlayer
        } else mVideoView
    }

    private fun initTransition(){
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
            loadVideoInfo()
        }
    }

    /**
     * 添加动画的监听
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener(){
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener{
            override fun onTransitionEnd(transition: Transition?) {
                Logger.e("$TAG : onTransitionEnd")
                loadVideoInfo()
                transition?.removeListener(this)
            }

            override fun onTransitionResume(transition: Transition?) {

            }

            override fun onTransitionPause(transition: Transition?) {

            }

            override fun onTransitionCancel(transition: Transition?) {

            }

            override fun onTransitionStart(transition: Transition?) {

            }

        })
    }

}