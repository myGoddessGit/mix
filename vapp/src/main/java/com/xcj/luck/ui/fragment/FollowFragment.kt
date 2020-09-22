package com.xcj.luck.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xcj.luck.R
import com.xcj.luck.base.BaseFragment
import com.xcj.luck.mvp.contract.FollowContract
import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.mvp.presenter.FollowPresenter
import com.xcj.luck.net.exception.ErrorStatus
import com.xcj.luck.showToast
import com.xcj.luck.ui.adapter.FollowAdapter
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 * @author cyl
 * @date 2020/8/8
 */
class FollowFragment : BaseFragment(), FollowContract.View {

    private var mTitle: String? = null

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private val mPresenter by lazy { FollowPresenter() }

    /**
     * adapter
     */
    private val mFollowAdapter by lazy { activity?.let{FollowAdapter(it, itemList)} }

    private var loadingMore = false // 是否加载更多

    /**
     * 绑定
     */
    init {
        mPresenter.attachView(this)
    }

    companion object {
        fun getInstance(title: String): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    /**
     * 布局
     */
    override fun getLayoutId(): Int {
        return R.layout.layout_recyclerview
    }

    /**
     * 初始化View
     */
    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mFollowAdapter
        // 实现自动加载
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager!!.itemCount
                val lastVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)){
                    loadingMore = true
                    mPresenter.loadMoreData() // 加载更多数据
                }
            }
        })
        mLayoutStatusView = multipleStatusView
    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
        mPresenter.requestFollowList()
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }

    /**
     * 设置FollowInfo
     */
    override fun setFollowInfo(issue: HomeBean.Issue) {
        loadingMore = false
        itemList = issue.itemList
        mFollowAdapter?.addData(itemList)
    }

    /**
     * 显示错误信息
     */
    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR){
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}