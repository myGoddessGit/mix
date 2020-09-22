package com.some.mix.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.some.mix.bean.wanandroid.Chapter;
import com.some.mix.fragment.WxNoPublicDetailFragment;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/24
 */
public class ChapterFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Chapter> list;

    public ChapterFragmentPageAdapter(@NonNull FragmentManager fm, List<Chapter> mList) {
        super(fm);
        this.list = mList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return WxNoPublicDetailFragment.instance(list.get(position).getId());
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}
