package com.some.mix.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.some.mix.R;
import com.some.mix.fragment.WxNoPublicFragment;

/**
 * @author cyl
 * @date 2020/8/24
 */
public class WxNoPublicActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layout);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new WxNoPublicFragment());
        transaction.commit();
    }
}
