package com.some.mix.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.some.mix.bean.gank.Category;
import com.some.mix.fragment.CateDetailFragment;
import com.some.mix.fragment.CateDetailFragmentKt;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/21
 */
public class DetailFragmentPageAdapter extends FragmentPagerAdapter {
    private String type;
    private List<Category.DataBean> list;
    public DetailFragmentPageAdapter(@NonNull FragmentManager fm, List<Category.DataBean> mList, String mType) {
        super(fm);
        this.list = mList;
        this.type = mType;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return CateDetailFragmentKt.Companion.instance(type, list.get(position).getType());
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getType();
    }
}
