package com.some.mix.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.some.mix.R;
import com.some.mix.adapter.ProjectListAdapter;
import com.some.mix.base.BaseFragment;
import com.some.mix.bean.wanandroid.Article;
import com.some.mix.callback.DataCallBack;
import com.some.mix.utils.ToolUtils;
import com.some.mix.wanandroidapi.ProjectArticleApi;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/25
 */
public class ProjectListFragment extends BaseFragment implements PullToRefreshListener {

    private PullToRefreshRecyclerView recyclerView;
    private int mPage = 1;
    private ProjectListAdapter listAdapter;
    public static final String KEY = "cidKey";
    private int cid;
    private List<Article> list = new ArrayList<>();

    public static ProjectListFragment instance(int mCid){
        ProjectListFragment instance = new ProjectListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, mCid);
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
            cid = bundle.getInt(KEY);
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        ToolUtils.setLayoutManager(recyclerView, getContext());
        listAdapter = new ProjectListAdapter(getContext(), R.layout.item_project_list, list);
        recyclerView.setAdapter(listAdapter);
        ToolUtils.openPullRecyclerView(recyclerView, this);
        //主动触发下拉刷新操作
        if (list.size() == 0){
            initData(1);
            recyclerView.onRefresh();
        }
    }

    private void initData(final int page){
        ProjectArticleApi api = new ProjectArticleApi();
        api.setCid(cid);
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
                        if (listAdapter != null){
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                }else {
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
        Log.i("ProjectArticleApi", api.buildRealUrl());
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
                if (listAdapter != null){
                    listAdapter.notifyDataSetChanged();
                }
            }
        }, 2 * 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToolUtils.onDestroyPullRecyclerView(recyclerView);
    }
}
