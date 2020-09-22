package com.some.mix.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.some.mix.R;

/**
 * @author cyl
 * @date 2020/8/20
 */
public abstract class BaseTabFragment extends Fragment implements View.OnClickListener{

    protected TabLayout tabLayout;
    protected ViewPager viewPager;
    protected String title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_tab_layout, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        view.findViewById(R.id.iv_topBarBack).setOnClickListener(this);
        view.findViewById(R.id.iv_topBarSearch).setVisibility(View.GONE);
        ((TextView)view.findViewById(R.id.tv_topBarText)).setText(title);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_topBarBack){
            getActivity().finish();
        }
    }
}
