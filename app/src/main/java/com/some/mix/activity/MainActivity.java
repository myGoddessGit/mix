package com.some.mix.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.some.mix.R;
import com.some.mix.event.ScrollEvent;
import com.some.mix.fragment.GankMainFragment;
import com.some.mix.fragment.GankMainFragmentKt;
import com.some.mix.fragment.WanMainFragment;
import de.greenrobot.event.EventBus;

/**
 * @author cyl
 * @date 2020/8/20
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private RadioButton[] radioButtons;
    private Fragment[]  fragments;
    private int currentPosition;
    private int index;
    private TextView tvText;
    private ImageView imgSearch;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        initViews();
        initFragments();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    private void initViews(){
        findViewById(R.id.iv_topBarBack).setVisibility(View.GONE);
        tvText = findViewById(R.id.tv_topBarText);
        tvText.setText("干货集中营");
        imgSearch = findViewById(R.id.iv_topBarSearch);
        imgSearch.setOnClickListener(this);
        imgSearch.setVisibility(View.GONE);
        radioButtons = new RadioButton[2];
        radioGroup = findViewById(R.id.rg_main);
        radioButtons[0] = findViewById(R.id.rb_gankIo);
        radioButtons[1] = findViewById(R.id.rb_wanAndroid);
        radioButtons[0].setSelected(true);
        for (RadioButton radioButton : radioButtons) {
            radioButton.setOnClickListener(this);
        }
    }

    private void setCurrentText(){
        if (currentPosition == 0){
            tvText.setText("干货集中营");
        } else if (currentPosition == 1){
            tvText.setText("玩安卓");
        }
    }

    private void initFragments(){
        fragments = new Fragment[]{new GankMainFragmentKt(),new WanMainFragment()};
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, fragments[0]).show(fragments[0]).commitNowAllowingStateLoss();
    }

    private void showCurrentFragment(int index){
        if (currentPosition != index){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments[currentPosition]);
            if (!fragments[index].isAdded()){
                ft.add(R.id.container, fragments[index]);
            }
            ft.show(fragments[index]).commit();
            radioButtons[currentPosition].setSelected(false);
            radioButtons[index].setSelected(true);
            currentPosition = index;
            setCurrentText();
        }
    }

    public void onEvent(ScrollEvent event){
        if (event.isUp()){
            radioGroup.setVisibility(View.GONE);
        } else  {
            radioGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_gankIo:
                imgSearch.setVisibility(View.GONE);
                index = 0;
                break;
            case R.id.rb_wanAndroid:
                imgSearch.setVisibility(View.VISIBLE);
                index = 1;
                break;
            case R.id.iv_topBarSearch:
                if (index == 1){
                    startActivity(new Intent(MainActivity.this, WanSearchActivity.class));
                }
                break;
            default:
                break;
        }
        showCurrentFragment(index);
    }

    private long mExitTime = 0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
