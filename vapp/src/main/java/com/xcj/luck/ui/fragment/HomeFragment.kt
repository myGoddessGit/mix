package com.xcj.luck.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.xcj.luck.R
import com.xcj.luck.base.BaseFragment
import com.xcj.luck.mvp.contract.HomeContract
import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.mvp.presenter.HomePresenter
import com.xcj.luck.net.exception.ErrorStatus
import com.xcj.luck.showToast
import com.xcj.luck.ui.activity.SearchActivity
import com.xcj.luck.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author cyl
 * @date 2020/8/8
 */
@SuppressLint("WrongConstant")
class HomeFragment : BaseFragment(), HomeContract.View {

    private val mPresenter by lazy { HomePresenter() }

    private var mTitle: String? = null

    private var num: Int = 1

    private var mHomeAdapter: HomeAdapter? = null

    /**
     * 是否加载更多
     */
    private var loadingMore = false

    private var isRefresh = false

    private var mMaterialHeader: MaterialHeader? = null

    /**
     * 伴生对象
     */
    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }
    /**
     * 布局
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    /**
     * 初始化View
     */
    override fun initView() {
        mPresenter.attachView(this)
        // 内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(num)
        }
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader?
        // 打开下拉刷新区域块背景
        mMaterialHeader?.setShowBezierWave(true)
        // 设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    val childCount = mRecyclerView.childCount
                    val itemCount = mRecyclerView.layoutManager!!.itemCount
                    val firstVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount){
                        if (!loadingMore){
                            loadingMore = true
                            mPresenter.loadMoreData() // 加载更多数据
                        }
                    }
                }
            }
            // RecyclerView滚动时调用
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0){
                    // 背景设置为透明
                    toolbar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mHomeAdapter?.mData!!.size > 1){
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mHomeAdapter!!.mData
                        val item = itemList[currentVisibleItemPosition + mHomeAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader"){
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }
            }
        })
        iv_search.setOnClickListener{openSearchActivity()}
    }

    private fun openSearchActivity(){
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            val options = activity?.let { ActivityOptionsCompat.makeSceneTransitionAnimation(it, iv_search, iv_search.transitionName) }
            startActivity(Intent(activity, SearchActivity::class.java), options?.toBundle())
        } else {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
        mPresenter.requestHomeData(num)
    }

    /**
     * 显示Loading (下拉刷新的时候不需要显示Loading)
     */
    override fun showLoading() {
        if (!isRefresh){
            isRefresh = false
            mLayoutStatusView?.showLoading()
        }
    }

    /**
     * 隐藏Loading
     */
    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }

    /**
     * 设置首页数据
     */
    override fun setHomeData(homeBean: HomeBean) {
        mLayoutStatusView?.showContent()
        Logger.d(homeBean)
        Log.i("HEAD_DATA", homeBean.toString())
        // Adapter
        mHomeAdapter = activity?.let { HomeAdapter(it, homeBean.issueList[0].itemList) }
        // 设置 banner 大小
        mHomeAdapter?.setBannerSize(homeBean.issueList[0].count)
        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    /**
     * 更多数据
     */
    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mHomeAdapter?.addItemData(itemList)
    }

    /**
     * 显示错误信息
     */
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
        if (errorCode == ErrorStatus.NETWORK_ERROR){
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }

}