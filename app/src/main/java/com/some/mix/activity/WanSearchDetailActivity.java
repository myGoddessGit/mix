package com.some.mix.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.some.mix.R;
import com.some.mix.adapter.WanArticleAdapter;
import com.some.mix.bean.wanandroid.Article;
import com.some.mix.callback.DataCallBack;
import com.some.mix.constans.Constant;
import com.some.mix.utils.ToolUtils;
import com.some.mix.wanandroidapi.HotKeyArticleApi;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/27
 */
public class WanSearchDetailActivity extends FragmentActivity implements View.OnClickListener, PullToRefreshListener{

    private String key = "";
    private PullToRefreshRecyclerView recyclerView;
    private WanArticleAdapter articleAdapter;
    private List<Article> list = new ArrayList<>();
    private int mPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        Intent intent = getIntent();
        if (intent != null){
            key = intent.getStringExtra("keyWord");
        }
        initView();
    }

    private void initView(){
        findViewById(R.id.iv_topBarBack).setOnClickListener(this);
        ((TextView)findViewById(R.id.tv_topBarText)).setText(key);
        findViewById(R.id.iv_topBarSearch).setVisibility(View.GONE);
        recyclerView = findViewById(R.id.searchRecyclerView);
        ToolUtils.setLayoutManager(recyclerView, WanSearchDetailActivity.this);
        articleAdapter = new WanArticleAdapter(WanSearchDetailActivity.this, R.layout.item_home_wan_article, list, 3);
        recyclerView.setAdapter(articleAdapter);
        ToolUtils.openPullRecyclerView(recyclerView, this);
        // 主动触发下拉刷新操作
        if (list.size() == 0){
            initData(0);
            recyclerView.onRefresh();
        }
    }

    private void initData(final int page){
        HotKeyArticleApi api = new HotKeyArticleApi();
        api.setKey(key);
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
        Log.i("HotKeyArticleApi", api.buildRealUrl());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_topBarBack){
            finish();
        }
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
    protected void onDestroy() {
        super.onDestroy();
        ToolUtils.onDestroyPullRecyclerView(recyclerView);
    }
}
