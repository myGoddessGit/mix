package com.some.mix.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.some.mix.R;

/**
 * @author cyl
 * @date 2020/8/24
 */
public abstract class TreeTabActivity extends FragmentActivity implements View.OnClickListener{
    protected TabLayout tabLayout;
    protected ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_base_tab_layout);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        findViewById(R.id.iv_topBarBack).setOnClickListener(this);
        findViewById(R.id.iv_topBarSearch).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.tv_topBarText)).setText("知识体系详情");
        FragmentPagerAdapter adapter = createFragmentAdapter();
        if (adapter != null){
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setOffscreenPageLimit(0);
        }
    }

    protected abstract FragmentPagerAdapter createFragmentAdapter();

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_topBarBack){
            finish();
        }
    }
}
