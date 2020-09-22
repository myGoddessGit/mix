package com.some.mix.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.some.mix.bean.wanandroid.Tree;
import com.some.mix.fragment.TreeDetailListFragment;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/24
 */
public class TreeDetailPageAdapter extends FragmentPagerAdapter {

    private List<Tree.ChildrenBean> childrenBeans;

    public TreeDetailPageAdapter(@NonNull FragmentManager fm, List<Tree.ChildrenBean> mList) {
        super(fm);
        this.childrenBeans = mList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return TreeDetailListFragment.instance(childrenBeans.get(position).getId());
    }

    @Override
    public int getCount() {
        return childrenBeans != null ? childrenBeans.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return childrenBeans.get(position).getName();
    }
}
