package com.some.mix.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.some.mix.R;
import com.some.mix.adapter.WanArticleAdapter;
import com.some.mix.base.BaseFragment;
import com.some.mix.bean.wanandroid.Article;
import com.some.mix.callback.DataCallBack;
import com.some.mix.constans.Constant;
import com.some.mix.wanandroidapi.TreeArticleApi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/24
 */
public class TreeDetailListFragment extends BaseFragment implements PullToRefreshListener{

    public static String CIDT = "cidT";
    private int cid;
    private int mPage = 0; // 分页
    private PullToRefreshRecyclerView recyclerView;
    private WanArticleAdapter articleAdapter;
    private List<Article> list = new ArrayList<>();

    public static TreeDetailListFragment instance(int cid){
        TreeDetailListFragment instance = new TreeDetailListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CIDT, cid);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_layout;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null){
            cid = bundle.getInt(CIDT);
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        articleAdapter = new WanArticleAdapter(getContext(), R.layout.item_home_wan_article, list, Constant.LIST_TYPE.TREE);
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

    private void initData(final int page){
        final TreeArticleApi api = new TreeArticleApi();
        api.setCid(cid);
        api.setPage(page);
        api.setCallBack(new DataCallBack<List<Article>>() {
            @Override
            public void onSuccess(List<Article> response) {
                if (response != null){
                    if (mPage == 0){
                        list.clear();
                        list.addAll(response);
                    } else {
                        list.addAll(response);
                        if (articleAdapter != null){
                            articleAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
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
        Log.i("TreeDetailApi", api.buildRealUrl());
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
        }, Constant.DELAYMILLiIS);
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
        }, Constant.DELAYMILLiIS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerView.setRefreshComplete();
        recyclerView.setLoadMoreComplete();
        recyclerView.loadMoreEnd();
    }
}
