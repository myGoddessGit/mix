package com.xcj.luck.ui.activity

import android.graphics.Typeface
import android.os.Build
import android.transition.Fade
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.xcj.luck.MyApplication
import com.xcj.luck.R
import com.xcj.luck.base.BaseActivity
import com.xcj.luck.mvp.contract.SearchContract
import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.mvp.presenter.SearchPresenter
import com.xcj.luck.net.exception.ErrorStatus
import com.xcj.luck.showToast
import com.xcj.luck.ui.adapter.CategoryDetailAdapter
import com.xcj.luck.ui.adapter.HotKeywordsAdapter
import com.xcj.luck.utils.CleanLeakUtil
import com.xcj.luck.utils.StatusBarUtil
import com.xcj.luck.view.ViewAnimUtils
import kotlinx.android.synthetic.main.activity_search.*

/**
 * @author cyl
 * @date 2020/8/7
 * 搜索功能
 */
class SearchActivity : BaseActivity(), SearchContract.View {

    /**
     * 搜索模块的Presenter
     */
    private val mPresenter by lazy { SearchPresenter() }
    // 搜索结果的Adapter
    private val mResultAdapter by lazy { CategoryDetailAdapter(this, itemList, R.layout.item_category_detail) }
    // 热门关键词的Adapter
    private var mHotKeywordsAdapter: HotKeywordsAdapter? = null
    // 数据源
    private var itemList = ArrayList<HomeBean.Issue.Item>()
    // 字体
    private var mTextTypeface: Typeface? = null
    // 搜索关键字
    private var keyWords: String? = null
    /**
     * 是否加载更多
     */
    private var loadingMore = false
    init {
        // 绑定 => this
        mPresenter.attachView(this)
        // 细黑简体字体
        mTextTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    /**
     * 布局
     */
    override fun layoutId(): Int {
        return R.layout.activity_search
    }

    /**
     * 初始化数据
     * 进入页面的动画
     */
    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            setUpEnterAnimation() // 入场动画
            setUpExitAnimation()  // 退场动画
        } else {
            setUpView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        tv_title_tip.typeface = mTextTypeface  // 设置字体
        tv_hot_search_words.typeface = mTextTypeface  // 设置字体
        // 初始化查询结果的 RecyclerView
        mRecyclerView_result.layoutManager = LinearLayoutManager(this)
        mRecyclerView_result.adapter = mResultAdapter

        // 实现自动加载
        mRecyclerView_result.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView_result.layoutManager!!.itemCount
                val lastVisibleItem = (mRecyclerView_result.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)){
                    loadingMore = true
                    mPresenter.loadMoreData() // 加载更多item
                }
            }
        })
        tv_cancel.setOnClickListener { onBackPressed() }
        // 键盘的搜索按钮
        et_search_view.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                closeSoftKeyboard()
                keyWords = et_search_view.text.toString().trim()
                if (keyWords.isNullOrEmpty()){
                    showToast("请输入感兴趣的关键字")
                } else {
                    mPresenter.querySearchData(keyWords!!)
                }
            }
            false
        }
        mLayoutStatusView = multipleStatusView
        // 状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
    }

    /**
     * 关闭软件盘
     */
    override fun closeSoftKeyboard() {
        closeKeyBord(et_search_view, applicationContext)
    }

    override fun start() {
        // 请求热门关键词
        mPresenter.requestHotWordData()
    }

    /**
     * 加载中
     */
    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    /**
     * 加载完成, 隐藏加载状态之类的控件
     */
    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    /**
     * 设置热门关键字
     */
    override fun setHotWordData(string: ArrayList<String>) {
        showHotWordView()
        mHotKeywordsAdapter = HotKeywordsAdapter(this, string, R.layout.item_flow_text)
        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP  // 按正常方向换行
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW // 主轴为水平方向, 起点在左端
        flexBoxLayoutManager.alignItems = AlignItems.CENTER  // 定义项目在副轴轴上如何对齐
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START // 多个轴对齐方式
        mRecyclerView_hot.layoutManager = flexBoxLayoutManager
        mRecyclerView_hot.adapter = mHotKeywordsAdapter
        // 设置 Tag 的点击事件
        mHotKeywordsAdapter?.setOnTagItemClickListener {
            closeSoftKeyboard()
            keyWords = it
            mPresenter.querySearchData(it) // 搜索
        }
    }

    /**
     * 设置搜索结果
     */
    override fun setSearchResult(issue: HomeBean.Issue) {
        loadingMore = false
        hideHotWordView()
        tv_search_count.visibility = View.VISIBLE
        tv_search_count.text = String.format(resources.getString(R.string.search_result_count), keyWords, issue.total)
        itemList = issue.itemList // 搜索结果的itemList => this.itemList
        mResultAdapter.addData(issue.itemList) // 添加数据

    }

    /**
     * 显示错误
     */
    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR){
            mLayoutStatusView?.showNoNetwork() // 显示无网络布局
        } else {
            mLayoutStatusView?.showError()
        }
    }

    /**
     * 没有找到相匹配的内容
     */
    override fun setEmptyView() {
        showToast("SORRY, DO NOT FIND THE MARRY OF THE CONTENT ")
        hideHotWordView()
        tv_search_count.visibility = View.GONE
        mLayoutStatusView?.showEmpty()
   }
    /**
     * 隐藏热门关键字的View
     */
    private fun hideHotWordView(){
        layout_hot_words.visibility = View.GONE
        layout_content_result.visibility = View.VISIBLE
    }

    /**
     * 显示热门关键字 使用流式布局
     */
    private fun showHotWordView(){
        layout_hot_words.visibility = View.VISIBLE // 设置该布局可见
        layout_content_result.visibility = View.GONE // 设置该布局隐藏
    }

    /**
     * 退场动画
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpExitAnimation(){
        val fade = Fade()
        window.returnTransition = fade
        fade.duration = 300
    }
    /**
     * 进场动画
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpEnterAnimation(){
        val transition = TransitionInflater.from(this)
            .inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener{
            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionResume(transition: Transition) {

            }

            override fun onTransitionPause(transition: Transition) {

            }

            override fun onTransitionCancel(transition: Transition) {

            }

            override fun onTransitionStart(transition: Transition) {

            }

        })
    }

    /**
     * 设置View
     */
    private fun setUpView(){
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.duration = 300 // 过度时长
        rel_container.startAnimation(animation) // 开始动画
        rel_container.visibility = View.VISIBLE // 设置该控件可见
        openKeyBord(et_search_view, applicationContext) // 打开软键盘
    }

    /**
     * 展示动画
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateRevealShow(){
        ViewAnimUtils.animateRevealShow(
            this, rel_frame,
            fab_circle.width / 2, R.color.backgroundColor,
            object : ViewAnimUtils.OnRevealAnimationListener {
                override fun onRevealHide() {

                }

                override fun onRevealShow() {
                    setUpView()
                }
            }
        )
    }

    /**
     * 返回时事件的处理
     */
    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            ViewAnimUtils.animateRevealHide(
                this, rel_frame,
                fab_circle.width / 2, R.color.backgroundColor,
                object : ViewAnimUtils.OnRevealAnimationListener {
                    override fun onRevealHide() {
                        defaultBackPressed()
                    }

                    override fun onRevealShow() {

                    }
                }
            )
        } else {
            defaultBackPressed()
        }
    }

    /**
     * 默认的回退
     */
    private fun defaultBackPressed(){
        closeSoftKeyboard()
        super.onBackPressed()
    }

    override fun onDestroy() {
        CleanLeakUtil.fixInputMethodManagerLeak(this)
        super.onDestroy()
        mPresenter.detachView()
        mTextTypeface = null
    }
}