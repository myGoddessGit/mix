package com.some.mix.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.some.mix.adapter.ChapterFragmentPageAdapter;
import com.some.mix.base.BaseTabFragment;
import com.some.mix.bean.wanandroid.Chapter;
import com.some.mix.callback.DataCallBack;
import com.some.mix.wanandroidapi.WxChapterApi;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/24
 */
public class WxNoPublicFragment extends BaseTabFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        title = "公众号";
        WxChapterApi api = new WxChapterApi();
        api.setCallBack(new DataCallBack<List<Chapter>>() {
            @Override
            public void onSuccess(List<Chapter> response) {
                ChapterFragmentPageAdapter adapter = new ChapterFragmentPageAdapter(getChildFragmentManager(), response);
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(response.size());
                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
        api.execute();
        Log.i("WxChapterApi", api.buildRealUrl());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
