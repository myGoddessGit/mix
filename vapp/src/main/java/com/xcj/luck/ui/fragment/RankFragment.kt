package com.xcj.luck.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcj.luck.R
import com.xcj.luck.base.BaseFragment
import com.xcj.luck.mvp.contract.RankContract
import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.mvp.presenter.RankPresenter
import com.xcj.luck.net.exception.ErrorStatus
import com.xcj.luck.showToast
import com.xcj.luck.ui.adapter.CategoryDetailAdapter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author cyl
 * @date 2020/8/8
 */
class RankFragment : BaseFragment(), RankContract.View {

    private val mPresenter by lazy { RankPresenter() }

    /**
     * Adapter
     */
    private val mAdapter by lazy { activity?.let { CategoryDetailAdapter(it, itemList, R.layout.item_category_detail) } }
    /**
     * 数据源
     */
    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private var apiUrl: String? = null

    companion object {
        fun getInstance(apiUrl: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }

    /**
     * 布局
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_rank
    }

    /**
     * 初始化View
     */
    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
        mLayoutStatusView = multipleStatusView
    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()){
            mPresenter.requestRankList(apiUrl!!)
        }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {

    }

    /**
     * 设置数据
     */
    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        multipleStatusView.showContent()
        mAdapter?.addData(itemList)
    }

    /**
     * 显示错误
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