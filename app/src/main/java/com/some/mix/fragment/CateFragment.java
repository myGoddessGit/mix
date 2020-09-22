package com.some.mix.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.some.mix.R;
import com.some.mix.adapter.DetailFragmentPageAdapter;
import com.some.mix.base.BaseTabFragment;
import com.some.mix.bean.gank.Category;
import com.some.mix.callback.DataCallBack;
import com.some.mix.gankapi.ArticleCateApi;
import com.some.mix.gankapi.GanHuoCateApi;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/21
 */
public class CateFragment extends BaseTabFragment{

    private String cate;

    public void update(String mCate){
        this.cate = mCate;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        title = cate;
        GanHuoCateApi api = new GanHuoCateApi();
        api.setCallBack(new DataCallBack<List<Category.DataBean>>() {
            @Override
            public void onSuccess(List<Category.DataBean> response) {
                if ("GanHuo".equals(cate)){
                    initViewPager(response, cate);
                }
            }

            @Override
            public void onFail(String errorMsg) {

            }

        });
        if (cate.equals("GanHuo")){
            api.execute();
            Log.i("GanHuoCateApi", api.buildRealUrl());
        }

        ArticleCateApi api1 = new ArticleCateApi();
        api1.setCallBack(new DataCallBack<List<Category.DataBean>>() {
            @Override
            public void onSuccess(List<Category.DataBean> response) {
                if ("Article".equals(cate)){
                    initViewPager(response, cate);
                }
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
        if (cate.equals("Article")){
            api1.execute();
            Log.i("ArticleCateApi", api.buildRealUrl());
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    private void initViewPager(List<Category.DataBean> response, String type){
        DetailFragmentPageAdapter adapter = new DetailFragmentPageAdapter(getChildFragmentManager(), response, type);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);
    }

}
