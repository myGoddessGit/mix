package com.xcj.luck.ui.activity

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xcj.luck.utils.StatusBarUtil
import com.xcj.luck.Constants
import com.xcj.luck.R
import com.xcj.luck.base.BaseActivity
import com.xcj.luck.glide.GlideApp
import com.xcj.luck.mvp.contract.CategoryDetailContract
import com.xcj.luck.mvp.model.bean.CategoryBean
import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.mvp.presenter.CategoryDetailPresenter
import com.xcj.luck.ui.adapter.CategoryDetailAdapter
import kotlinx.android.synthetic.main.activity_category_detail.*
import kotlinx.android.synthetic.main.fragment_hot.*
import kotlinx.android.synthetic.main.fragment_hot.toolbar

/**
 * @author cyl
 * @date 2020/8/5
 */
class CategoryDetailActivity : BaseActivity(), CategoryDetailContract.View {

    private val mPresenter by lazy { CategoryDetailPresenter() }

    private val mAdapter by lazy { CategoryDetailAdapter(this, itemList, R.layout.item_category_detail) }

    private var categoryData: CategoryBean? = null

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    init {
        mPresenter.attachView(this)
    }

    /**
     * 是否加载更多
     */
    private var loadingMore = false

    override fun initData() {
        categoryData = intent.getSerializableExtra(Constants.BUNDLE_CATEGORY_DATA) as CategoryBean?
    }

    override fun layoutId(): Int {
        return R.layout.activity_category_detail
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        //setActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        GlideApp.with(this)
            .load(categoryData?.headerImage)
            .placeholder(R.color.color_darker_gray)
            .into(imageView)
        tv_category_desc.text = "#${categoryData?.description}#"
        collapsing_toolbar_layout.title = categoryData?.name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE)
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager!!.itemCount
                val lastVisibleItem  = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)){
                    loadingMore = true
                    mPresenter.loadMoreData()
                }
            }
        })
        // 状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
    }

    override fun start() {
        // 获取当前的分类列表
        categoryData?.id?.let { mPresenter.getCategoryDetailList(it) }
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String) {
        multipleStatusViewDetail.showError()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 取消绑定
        mPresenter.detachView()
    }

}