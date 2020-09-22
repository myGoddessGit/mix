package com.some.mix.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.some.mix.R;
import com.some.mix.fragment.CateFragment;

/**
 * @author cyl
 * @date 2020/8/21
 */
public class CateActivity extends FragmentActivity {

    public static final String TYPE = "type";
    private String type;
    private CateFragment fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layout);
        type = getIntent().getStringExtra(TYPE);
        fragment = new CateFragment();
        fragment.update(type);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commitAllowingStateLoss();
    }

    public static void startCateDetailActivity(Context context, String type){
        Intent intent = new Intent(context, CateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.with(getApplicationContext()).pauseRequests();
    }
}
