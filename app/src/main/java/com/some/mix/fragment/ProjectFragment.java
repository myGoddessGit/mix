package com.some.mix.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.some.mix.adapter.ProjectFragmentPagerAdapter;
import com.some.mix.base.BaseTabFragment;
import com.some.mix.bean.wanandroid.ProjectCate;
import com.some.mix.callback.DataCallBack;
import com.some.mix.wanandroidapi.ProjectApi;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/25
 */
public class ProjectFragment extends BaseTabFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        title = "项目";
        ProjectApi api = new ProjectApi();
        api.setCallBack(new DataCallBack<List<ProjectCate>>() {
            @Override
            public void onSuccess(List<ProjectCate> response) {
                ProjectFragmentPagerAdapter adapter = new ProjectFragmentPagerAdapter(getChildFragmentManager(),response);
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(0);
                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
        api.execute();
        Log.i("ProjectApi", api.buildRealUrl());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
