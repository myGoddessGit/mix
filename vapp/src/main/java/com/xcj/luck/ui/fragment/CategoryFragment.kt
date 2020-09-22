package com.xcj.luck.ui.fragment


import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xcj.luck.R
import com.xcj.luck.base.BaseFragment
import com.xcj.luck.mvp.contract.CategoryContract
import com.xcj.luck.mvp.model.bean.CategoryBean
import com.xcj.luck.mvp.presenter.CategoryPresenter
import com.xcj.luck.net.exception.ErrorStatus
import com.xcj.luck.showToast
import com.xcj.luck.ui.adapter.CategoryAdapter
import com.xcj.luck.utils.DisplayManager
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 * @author cyl
 * @date 2020/8/8
 */
class CategoryFragment : BaseFragment(), CategoryContract.View {

    private val mPresenter by lazy { CategoryPresenter() }

    private val mAdapter by lazy { activity?.let { CategoryAdapter(it, mCategoryList, R.layout.item_category) } }

    private var mTitle: String? = null

    private var mCategoryList = ArrayList<CategoryBean>()

    companion object {
        fun getInstance(title: String): CategoryFragment {
            val fragment = CategoryFragment()
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
        return R.layout.fragment_category
    }

    /**
     * 初始化View
     */
    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildLayoutPosition(view)
                val offset = DisplayManager.dip2px(2f)!!
                outRect.set(if (position % 2 == 0) 0 else offset, offset, if (position % 2 == 0) offset else 0, offset)
            }
        })
    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
        mPresenter.getCategoryData() // 获取分类信息
    }

    override fun showLoading() {
        multipleStatusView?.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView?.showContent()
    }

    /**
     * 显示分类信息
     */
    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
        mCategoryList = categoryList
        mAdapter?.setData(mCategoryList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if(errorCode == ErrorStatus.NETWORK_ERROR){
            multipleStatusView?.showNoNetwork()
        } else {
            multipleStatusView?.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}