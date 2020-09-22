package com.xcj.luck.ui.fragment

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.xcj.luck.R
import com.xcj.luck.base.BaseFragment
import com.xcj.luck.base.BaseFragmentAdapter
import com.xcj.luck.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_search.toolbar
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * @author cyl
 * @date 2020/8/8
 */
class DiscoveryFragment : BaseFragment() {

    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    private var mTitle: String? = null

    /**
     * 伴生对象
     */
    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            val fragment = DiscoveryFragment()
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
        return R.layout.fragment_hot
    }

    /**
     * 初始化View
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        // 状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
        tv_header_title.text = mTitle
        tabList.add("关注")
        tabList.add("分类")
        fragments.add(FollowFragment.getInstance("关注"))
        fragments.add(CategoryFragment.getInstance("分类"))
        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
        mTabLayout.setupWithViewPager(mViewPager)
        //TabLayoutHelper.setUpIndicatorWidth(mTabLayout)
    }

    override fun lazyLoad() {

    }
}