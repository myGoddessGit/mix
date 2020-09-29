package com.some.mix.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.some.mix.R;
import com.some.mix.adapter.GankGirlAdapter;
import com.some.mix.bean.gank.Detail;
import com.some.mix.callback.DataCallBack;
import com.some.mix.constans.Constant;
import com.some.mix.gankapi.GirlApi;
import com.some.mix.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/22
 */
public class GirlActivity extends FragmentActivity implements View.OnClickListener, PullToRefreshListener {

    private PullToRefreshRecyclerView recyclerView;
    private GankGirlAdapter adapter;
    private List<Detail.DataBean> list = new ArrayList<>();
    private int mPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);
        initView();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @SuppressLint("WrongConstant")
    private void initView(){
        recyclerView = findViewById(R.id.gRecyclerView);
        findViewById(R.id.iv_topBarBack).setOnClickListener(this);
        ((TextView)findViewById(R.id.tv_topBarText)).setText("YearningLight");
        findViewById(R.id.iv_topBarSearch).setVisibility(View.GONE);
        ToolUtils.setLayoutManager(recyclerView, this);
        adapter = new GankGirlAdapter(GirlActivity.this, list);
        recyclerView.setAdapter(adapter);
        ToolUtils.openPullRecyclerView(recyclerView, this);
        // 主动触发下拉刷新操作
        if (list.size() == 0){
            initData(1);
            recyclerView.onRefresh();
        }
    }
    private void initData(final int page){
        GirlApi api = new GirlApi();
        api.setPage(page);
        api.setCount(33);
        api.setCallBack(new DataCallBack<List<Detail.DataBean>>() {
            @Override
            public void onSuccess(List<Detail.DataBean> response) {
                if (response != null && response.size() != 0){
                    if (mPage == 1){
                        list.clear();
                        list.addAll(response);
                    } else {
                        list.addAll(response);
                        adapter.notifyDataSetChanged();
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
        Log.i("GirlApi", api.buildRealUrl());
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
                if (mPage <= 4){
                    initData(mPage);
                } else {
                    recyclerView.setLoadMoreFail();
                }
                recyclerView.setLoadMoreComplete();
                adapter.notifyDataSetChanged();
            }
        },Constant.DELAYMILLiIS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToolUtils.onDestroyPullRecyclerView(recyclerView);
    }
}
