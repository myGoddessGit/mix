package com.some.mix.activity;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentPagerAdapter;
import com.some.mix.adapter.TreeDetailPageAdapter;
import com.some.mix.bean.wanandroid.Tree;

/**
 * @author cyl
 * @date 2020/8/24
 */
public class TreeDetailActivity extends TreeTabActivity {

    private Tree tree;

    @Override
    protected FragmentPagerAdapter createFragmentAdapter() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            tree = (Tree) bundle.getSerializable("ccid");
        }
        return new TreeDetailPageAdapter(getSupportFragmentManager(),tree.getChildren());
    }
}
