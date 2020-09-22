package com.xcj.luck.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.xcj.luck.R
import com.xcj.luck.base.BaseFragment
import com.xcj.luck.base.BaseFragmentAdapter
import com.xcj.luck.mvp.contract.HotTabContract
import com.xcj.luck.mvp.model.bean.TabInfoBean
import com.xcj.luck.mvp.presenter.HotTabPresenter
import com.xcj.luck.net.exception.ErrorStatus
import com.xcj.luck.showToast
import com.xcj.luck.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.multipleStatusView
import kotlinx.android.synthetic.main.activity_search.toolbar
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * @author cyl
 * @date 2020/8/8
 */
class HotFragment : BaseFragment(), HotTabContract.View {

    private val mPresenter by lazy { HotTabPresenter() }

    private var mTitle: String? = null

    /**
     * 存放tab的标题
     */
    private val mTabTitleList = ArrayList<String>()

    private val mFragmentList = ArrayList<Fragment>()

    /**
     * 伴生对象
     */
    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    // 绑定该presenter
    init {
        mPresenter.attachView(this)
    }
    /**
     * 布局
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_hot
    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }

    /**
     * 初始化View
     */
    override fun initView() {
        mLayoutStatusView = multipleStatusView
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {

    }
    /**
     * 设置TabInfo数据
     */
    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        multipleStatusView.showContent()
        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList){it.name}
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) {RankFragment.getInstance(it.apiUrl)}
        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, mFragmentList, mTabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)
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
        mPresenter.detachView() //解绑
    }

}