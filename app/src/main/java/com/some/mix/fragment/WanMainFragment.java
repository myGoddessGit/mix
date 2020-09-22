package com.some.mix.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.some.mix.R;
import com.some.mix.activity.NaviActivity;
import com.some.mix.activity.ProjectActivity;
import com.some.mix.activity.TreeActivity;
import com.some.mix.activity.WxNoPublicActivity;
import com.some.mix.adapter.WanArticleAdapter;
import com.some.mix.adapter.WanBannerAdapter;
import com.some.mix.base.BaseFragment;
import com.some.mix.bean.wanandroid.Article;
import com.some.mix.bean.wanandroid.Banner;
import com.some.mix.callback.DataCallBack;
import com.some.mix.constans.Constant;
import com.some.mix.wanandroidapi.BannerApi;
import com.some.mix.wanandroidapi.HomeArticleApi;
import com.some.mix.wanandroidapi.TopArticleApi;
import com.some.mix.widget.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/24
 */
public class WanMainFragment extends BaseFragment implements PullToRefreshListener, View.OnClickListener {

    private int mPage = 0; // 分页
    private PullToRefreshRecyclerView recyclerView;
    private WanBannerAdapter bannerAdapter;
    private WanArticleAdapter articleAdapter;
    private BannerViewPager viewPager;
    private List<Article> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wan;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.wanRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        View bannerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_wan_header, recyclerView, false);
        viewPager = bannerView.findViewById(R.id.viewPager);
        bannerView.findViewById(R.id.tvTree).setOnClickListener(this);
        bannerView.findViewById(R.id.tvWx).setOnClickListener(this);
        bannerView.findViewById(R.id.tvProject).setOnClickListener(this);
        bannerView.findViewById(R.id.tvNavi).setOnClickListener(this);
        initBanner();
        recyclerView.addHeaderView(bannerView);
        articleAdapter = new WanArticleAdapter(getContext(), R.layout.item_home_wan_article, list, Constant.LIST_TYPE.HOME);
        recyclerView.setAdapter(articleAdapter);
        //设置是否开启上拉加载
        recyclerView.setLoadingMoreEnabled(true);
        //设置是否开启下拉刷新
        recyclerView.setPullRefreshEnabled(true);
        //设置是否显示上次刷新的时间
        recyclerView.displayLastRefreshTime(true);
        //设置刷新回调
        recyclerView.setPullToRefreshListener(this);
        // 主动触发下拉刷新操作
        if (list.size() == 0){
            initData(0);
            recyclerView.onRefresh();
        }
    }

    private void initBanner(){
        BannerApi bannerApi = new BannerApi();
        bannerApi.setCallBack(new DataCallBack<List<Banner>>() {
            @Override
            public void onSuccess(List<Banner> response) {
                bannerAdapter = new WanBannerAdapter(response, getContext());
                viewPager.setAdapter(bannerAdapter);
                viewPager.setOffscreenPageLimit(response.size());
                viewPager.setCurrentItem(1000 * response.size());
                bannerAdapter.notifyDatas(response);
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
        bannerApi.execute();
        Log.i("WanBannerApi", bannerApi.buildRealUrl());
    }

    private void initData(final int page){
        TopArticleApi topApi = new TopArticleApi();
        topApi.setCallBack(new DataCallBack<List<Article>>() {
            @Override
            public void onSuccess(List<Article> response) {
                if (response != null){
                    if (mPage == 0){
                        for (Article article : response){
                            article.setTop(true);
                        }
                        list.clear();
                        list.addAll(response);
//                        if (articleAdapter != null){
//                            articleAdapter.notifyDataSetChanged();
//                        }
                    }
                }
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
        topApi.execute();
        Log.i("WanTopApi", topApi.buildRealUrl());

        HomeArticleApi api = new HomeArticleApi();
        api.setPage(page);
        api.setCallBack(new DataCallBack<List<Article>>() {
            @Override
            public void onSuccess(List<Article> response) {
                if (response != null){
                   list.addAll(response);
                   if (articleAdapter != null){
                         articleAdapter.notifyDataSetChanged();
                   }
                }else {
                    if (mPage > 0){
                        mPage = mPage - 1;
                    }
                }
            }
            @Override
            public void onFail(String errorMsg) {

            }
        });
        api.execute();
        Log.i("WanHomeApi", api.buildRealUrl());
    }

    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshComplete();
                if (mPage != 0){
                    initData(0);
                }
            }
        }, 2 * 1000);
    }

    @Override
    public void onLoadMore() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPage = mPage + 1;
                initData(mPage);
                recyclerView.setLoadMoreComplete();
                if (articleAdapter != null){
                    articleAdapter.notifyDataSetChanged();
                }
            }
        }, 2 * 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerView.setRefreshComplete();
        recyclerView.setLoadMoreComplete();
        recyclerView.loadMoreEnd();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        //Log.i("WanHidden", hidden + "");
        if (!hidden){
           viewPager.start();
        } else {
           viewPager.stop();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvTree:
                startActivity(new Intent(getActivity(), TreeActivity.class));
                break;

            case R.id.tvWx:
                startActivity(new Intent(getActivity(), WxNoPublicActivity.class));
                break;

            case R.id.tvProject:
                startActivity(new Intent(getActivity(), ProjectActivity.class));
                break;

            case R.id.tvNavi:
                startActivity(new Intent(getActivity(), NaviActivity.class));
                break;

            default:
                break;
        }
    }
}
