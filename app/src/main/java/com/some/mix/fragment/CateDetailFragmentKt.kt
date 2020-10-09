package com.some.mix.fragment

import android.os.Bundle
import android.view.View
import com.androidkun.PullToRefreshRecyclerView
import com.androidkun.callback.PullToRefreshListener
import com.some.mix.R
import com.some.mix.adapter.GankDetailAdapter
import com.some.mix.base.BaseFragment
import com.some.mix.bean.gank.Detail
import com.some.mix.callback.DataCallBack
import com.some.mix.constans.Constant
import com.some.mix.gankapi.CateDetailApi
import com.some.mix.utils.ToolUtils


/**
 * @author cyl
 * @date 2020/9/14
 */
class CateDetailFragmentKt : BaseFragment(), PullToRefreshListener{

    private var cate : String? = null
    private var type : String? = null
    private var recyclerView: PullToRefreshRecyclerView? = null
    private var beans = ArrayList<Detail.DataBean>()
    private var adapter: GankDetailAdapter? = null
    private var mPage: Int = 1
    companion object {
        const val CATE = "CATE"
        const val TYPE = "TYPE"
        fun instance(cate: String?, type: String?): CateDetailFragmentKt{
            val instance = CateDetailFragmentKt()
            val bundle = Bundle()
            bundle.putString(CATE, cate)
            bundle.putString(TYPE, type)
            instance.arguments = bundle
            return instance
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_detail_layout
    }

    override fun initView(view: View?) {
        val bundle = arguments
        if (bundle != null){
            cate = bundle.getString(CATE)
            type = bundle.getString(TYPE)
        }
        recyclerView = view!!.findViewById(R.id.recyclerView)
        ToolUtils.setLayoutManager(recyclerView, mAttachActivity)
        adapter = GankDetailAdapter(mAttachActivity, R.layout.gank_item_detail, beans)
        recyclerView!!.adapter = adapter
        ToolUtils.openPullRecyclerView(recyclerView, this)
        if (beans.size == 0){
            initData(1)
            recyclerView!!.onRefresh()
        }
    }

    fun initData(page: Int?){
        val api = CateDetailApi()
        api.setCate(cate)
        api.setType(type)
        api.setPage(page!!)
        api.setCount(api.defaultCount())
        api.setCallBack(object : DataCallBack<List<Detail.DataBean>>{
            override fun onSuccess(response: List<Detail.DataBean>?) {
                if (response != null && response.isNotEmpty()){
                    if (mPage == 1){
                        beans.clear()
                        beans.addAll(response)
                    } else {
                        beans.addAll(response)
                        adapter!!.notifyDataSetChanged()
                    }
                } else {
                    if (mPage > 1){
                        mPage -= 1
                    }
                }
            }

            override fun onFail(errorMsg: String?) {

            }
        })
        api.execute()
    }

    override fun onRefresh() {
       recyclerView!!.postDelayed({
           recyclerView!!.setRefreshComplete()
           if (mPage != 1) initData(1)
       },Constant.DELAYMILLiIS)
    }

    override fun onLoadMore() {
       recyclerView!!.postDelayed({
           mPage += 1
           initData(mPage)
           recyclerView!!.setLoadMoreComplete()
           adapter?.notifyDataSetChanged()
       },Constant.DELAYMILLiIS)
    }

    override fun onDestroy() {
        super.onDestroy()
        ToolUtils.onDestroyPullRecyclerView(recyclerView)
    }
}