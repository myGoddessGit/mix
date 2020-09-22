package com.some.mix.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.some.mix.bean.wanandroid.ProjectCate;
import com.some.mix.fragment.ProjectListFragment;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/25
 */
public class ProjectFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<ProjectCate> cateList;

    public ProjectFragmentPagerAdapter(@NonNull FragmentManager fm, List<ProjectCate> mList) {
        super(fm);
        this.cateList = mList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ProjectListFragment.instance(cateList.get(position).getId());
    }

    @Override
    public int getCount() {
        return cateList != null ? cateList.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return cateList.get(position).getName();
    }
}
