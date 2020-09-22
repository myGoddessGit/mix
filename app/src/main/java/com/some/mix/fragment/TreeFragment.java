package com.some.mix.fragment;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.some.mix.R;
import com.some.mix.adapter.TreeAdapter;
import com.some.mix.base.BaseFragment;
import com.some.mix.bean.wanandroid.Tree;
import com.some.mix.callback.DataCallBack;
import com.some.mix.utils.ToolUtils;
import com.some.mix.wanandroidapi.TreeApi;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/24
 */
public class TreeFragment extends BaseFragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private TreeAdapter treeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tree;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView(View view) {
        view.findViewById(R.id.iv_topBarBack).setOnClickListener(this);
        view.findViewById(R.id.iv_topBarSearch).setVisibility(View.GONE);
        ((TextView)view.findViewById(R.id.tv_topBarText)).setText("知识体系");
        recyclerView = view.findViewById(R.id.treeRecyclerView);
        ToolUtils.setLayoutManager(recyclerView, getContext());
        TreeApi api = new TreeApi();
        api.setCallBack(new DataCallBack<List<Tree>>() {
            @Override
            public void onSuccess(List<Tree> response) {
                treeAdapter = new TreeAdapter(getContext(), response);
                recyclerView.setAdapter(treeAdapter);
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
        api.execute();
        Log.i("TreeApi", api.buildRealUrl());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_topBarBack){
            getActivity().finish();
        }
    }
}
