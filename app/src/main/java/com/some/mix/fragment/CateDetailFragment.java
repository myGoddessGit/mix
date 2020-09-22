package com.some.mix.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.some.mix.R;
import com.some.mix.adapter.GankDetailAdapter;
import com.some.mix.base.BaseFragment;
import com.some.mix.bean.gank.Detail;
import com.some.mix.callback.DataCallBack;
import com.some.mix.gankapi.CateDetailApi;
import com.some.mix.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/21
 */
public class CateDetailFragment extends BaseFragment implements PullToRefreshListener {

    public static final String CATE = "CATE";
    public static final String TYPE = "TYPE";
    private String cate;
    private String type;
    private PullToRefreshRecyclerView recyclerView;
    private List<Detail.DataBean> beans = new ArrayList<>();
    private GankDetailAdapter adapter;
    private int mPage = 1; // 每次下拉刷新自增 1

    public static CateDetailFragmentKt instance(String cate, String type){
        CateDetailFragmentKt instance = new CateDetailFragmentKt();
        Bundle bundle = new Bundle();
        bundle.putString(CATE, cate);
        bundle.putString(TYPE, type);
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
            cate = bundle.getString(CATE);
            type = bundle.getString(TYPE);
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        ToolUtils.setLayoutManager(recyclerView, getActivity());
        adapter = new GankDetailAdapter(getActivity(), R.layout.gank_item_detail, beans);
        recyclerView.setAdapter(adapter);
        ToolUtils.openPullRecyclerView(recyclerView, this);
        // 主动触发下拉刷新操作
        if (beans.size() == 0){
            initData(1);
            recyclerView.onRefresh();
        }
    }

    private void initData(final int page){
        CateDetailApi api = new CateDetailApi();
        api.setCate(cate);
        api.setType(type);
        api.setPage(page);
        api.setCount(api.defaultCount());
        api.setCallBack(new DataCallBack<List<Detail.DataBean>>() {
            @Override
            public void onSuccess(List<Detail.DataBean> response) {
                if (response != null && response.size() != 0){
                    if (mPage == 1){
                        beans.clear();
                        beans.addAll(response);
                    } else {
                        beans.addAll(response);
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
        Log.i("CateRealApi", api.buildRealUrl());
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
                adapter.notifyDataSetChanged();
            }
        }, 2 * 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToolUtils.onDestroyPullRecyclerView(recyclerView);
    }
}
