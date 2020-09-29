package com.some.mix.fragment;

import android.annotation.SuppressLint;
import android.icu.text.UnicodeSetSpanner;
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
import com.some.mix.utils.ToolUtils;
import com.some.mix.wanandroidapi.TreeArticleApi;
import com.some.mix.wanandroidapi.WxArticleApi;
import com.some.mix.wanandroidapi.WxChapterApi;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/24
 */
public class WxNoPublicDetailFragment extends BaseFragment implements PullToRefreshListener {

    public static final String CID = "chapterId";

    private int chapterId;
    private int mPage = 1;
    private WanArticleAdapter articleAdapter;
    private PullToRefreshRecyclerView recyclerView;
    private List<Article> list = new ArrayList<>();

    public static WxNoPublicDetailFragment instance(int chapterId){
        WxNoPublicDetailFragment instance = new WxNoPublicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CID, chapterId);
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
            chapterId = bundle.getInt(CID);
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        ToolUtils.setLayoutManager(recyclerView, getContext());
        articleAdapter = new WanArticleAdapter(getContext(), R.layout.item_home_wan_article, list, 0);
        recyclerView.setAdapter(articleAdapter);
        ToolUtils.openPullRecyclerView(recyclerView, this);
        // 主动触发下拉刷新操作
        if (list.size() == 0){
            initData(1);
            recyclerView.onRefresh();
        }
    }

    private void initData(final int page){
        final WxArticleApi api = new WxArticleApi();
        api.setChapterId(chapterId);
        api.setPage(page);
        api.setCallBack(new DataCallBack<List<Article>>() {
            @Override
            public void onSuccess(List<Article> response) {
                if (response != null){
                    if (mPage == 1){
                        list.clear();
                        list.addAll(response);
                    } else {
                        list.addAll(response);
                        if (articleAdapter != null){
                            articleAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    if (mPage > 1){
                        mPage = mPage - 1;
                    }
                }
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
        api.execute();
        Log.i("WxArticleApi", api.buildRealUrl());
    }

    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshComplete();
                if (mPage != 1){
                    initData(1);
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
        ToolUtils.onDestroyPullRecyclerView(recyclerView);
    }
}
