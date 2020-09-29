package com.some.mix.fragment

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidkun.PullToRefreshRecyclerView
import com.androidkun.callback.PullToRefreshListener
import com.some.mix.R
import com.some.mix.activity.CateActivity
import com.some.mix.activity.GirlActivity
import com.some.mix.adapter.GankBannerAdapter
import com.some.mix.adapter.GankDetailAdapter
import com.some.mix.base.BaseFragment
import com.some.mix.bean.gank.Banner
import com.some.mix.bean.gank.Detail
import com.some.mix.callback.DataCallBack
import com.some.mix.event.ScrollEvent
import com.some.mix.gankapi.BannerApi
import com.some.mix.gankapi.CateDetailApi
import com.some.mix.utils.ToolUtils
import com.some.mix.widget.BannerViewPager
import com.xcj.luck.ui.activity.MainActivity
import de.greenrobot.event.EventBus

/**
 * @author cyl
 * @date 2020/9/14
 */
class GankMainFragmentKt : BaseFragment(), PullToRefreshListener, View.OnClickListener {

    private var cateS : String? = null
    private var dataBeans = ArrayList<Detail.DataBean>()
    private var gAdapter: GankDetailAdapter? = null
    private var bannerAdapter: GankBannerAdapter? = null
    private var viewPager : BannerViewPager? = null
    private var gankRecyclerView: PullToRefreshRecyclerView? = null

    init {
        cateS = "GanHuo"
        randomCate()
    }
    override fun getLayoutId(): Int = R.layout.fragment_gank

    override fun initView(view: View) {
        gankRecyclerView = view.findViewById(R.id.ganKRecyclerView)
        ToolUtils.setLayoutManager(gankRecyclerView, context)
        val headView = LayoutInflater.from(activity).inflate(R.layout.layout_gank_header, gankRecyclerView, false)
        viewPager = headView.findViewById(R.id.viewPager)
        headView.findViewById<TextView>(R.id.tvGanHuo).setOnClickListener(this)
        headView.findViewById<TextView>(R.id.tvArticle).setOnClickListener(this)
        headView.findViewById<TextView>(R.id.tvGirl).setOnClickListener(this)
        headView.findViewById<TextView>(R.id.tvMvp).setOnClickListener(this)
        initBanner()
        gankRecyclerView!!.addHeaderView(headView)
        gAdapter = GankDetailAdapter(activity, R.layout.gank_item_detail, dataBeans)
        gankRecyclerView!!.adapter = gAdapter
        //设置是否开启上拉加载
        gankRecyclerView!!.setLoadingMoreEnabled(false)
        //设置是否开启下拉刷新
        gankRecyclerView!!.displayLastRefreshTime(true)
        //设置刷新回调
        gankRecyclerView!!.setPullToRefreshListener(this)

        gankRecyclerView!!.onRefresh()
        gankRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 5){
                    EventBus.getDefault().post(ScrollEvent(true))
                } else if (dy < -5) {
                    EventBus.getDefault().post(ScrollEvent(false))
                }
            }
        })
    }

    private fun initBanner(){
        val api = BannerApi()
        api.setCallBack(object : DataCallBack<List<Banner.DataBean>>{
            override fun onSuccess(response: List<Banner.DataBean>?) {
                bannerAdapter = GankBannerAdapter(activity, response)
                viewPager!!.adapter = bannerAdapter
                viewPager!!.offscreenPageLimit = response!!.size
                viewPager!!.currentItem = 1000 * response.size
                bannerAdapter!!.notifyDatas(response)
            }

            override fun onFail(errorMsg: String?) {

            }
        })
        api.execute()
    }

    fun initData(){
        val api = CateDetailApi()
        api.setCateApi("random")
        api.setCate(cateS)
        api.setType("All")
        api.setCount(50)
        api.setCallBack(object : DataCallBack<List<Detail.DataBean>>{
            override fun onSuccess(response: List<Detail.DataBean>?) {
                dataBeans.clear()
                dataBeans.addAll(response!!)
                if (gAdapter != null){
                    gAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onFail(errorMsg: String?) {

            }
        })
        api.execute()
    }

    private fun randomCate(){
        val cate = (0..1).random()
        cateS = when (cate) {
            0 -> "GanHuo"
            1 -> "Article"
            else -> "GanHuo"
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden){
            viewPager!!.start()
        } else {
            viewPager!!.stop()
        }
        super.onHiddenChanged(hidden)
    }

    override fun onRefresh() {
        gankRecyclerView!!.postDelayed({
            gankRecyclerView!!.setRefreshComplete()
            initData()
        }, 2000)
    }

    override fun onLoadMore() {

    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.tvGanHuo -> toDetailFragment("GanHuo")
            R.id.tvArticle -> toDetailFragment("Article")
            R.id.tvGirl -> startActivity(Intent(activity, GirlActivity::class.java))
            R.id.tvMvp -> startActivity(Intent(activity, MainActivity::class.java))
        }
    }

   private fun toDetailFragment(type : String){
        CateActivity.startCateDetailActivity(activity, type)
    }

}