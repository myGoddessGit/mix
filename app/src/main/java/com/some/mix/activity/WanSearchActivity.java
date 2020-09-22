package com.some.mix.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.some.mix.R;
import com.some.mix.bean.wanandroid.Article;
import com.some.mix.bean.wanandroid.Friend;
import com.some.mix.bean.wanandroid.Hotword;
import com.some.mix.callback.DataCallBack;
import com.some.mix.utils.ToolUtils;
import com.some.mix.wanandroidapi.FriendApi;
import com.some.mix.wanandroidapi.HotKeyApi;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/27
 */
public class WanSearchActivity extends FragmentActivity implements View.OnClickListener{

    private TagFlowLayout mKeyLayout, mFriendLayout;
    private EditText edContent;
    private String keyWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_m);
        initView();
        initData();
    }

    private void initView(){
        findViewById(R.id.ivBack1).setOnClickListener(this);
        mKeyLayout = findViewById(R.id.keywordTaglayout);
        mFriendLayout = findViewById(R.id.friendTaglayout);
        findViewById(R.id.tvSearch).setOnClickListener(this);
        edContent = findViewById(R.id.etSearch);
    }

    private void initData(){
        final HotKeyApi api = new HotKeyApi();
        api.setCallBack(new DataCallBack<List<Hotword>>() {
            @Override
            public void onSuccess(final List<Hotword> response) {
                mKeyLayout.setAdapter(new TagAdapter<Hotword>(response) {
                    @Override
                    public View getView(FlowLayout parent, int position, Hotword bean) {
                        TextView tagView = (TextView) LayoutInflater.from(WanSearchActivity.this).inflate(R.layout.item_search_tag, mKeyLayout, false);
                        tagView.setText(bean.getName());
                        ToolUtils.setTagTextColor(tagView);
                        return tagView;
                    }
                });
                mKeyLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        keyWord = response.get(position).getName();
                        edContent.setText(keyWord);
                        edContent.setSelection(keyWord.length());
                        return false;
                    }
                });
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
        api.execute();
        Log.i("HotKeyApi", api.buildRealUrl());

        FriendApi apiF = new FriendApi();
        apiF.setCallBack(new DataCallBack<List<Friend>>() {
            @Override
            public void onSuccess(final List<Friend> response) {
                mFriendLayout.setAdapter(new TagAdapter<Friend>(response) {
                    @Override
                    public View getView(FlowLayout parent, int position, Friend bean) {
                        TextView tagView = (TextView) LayoutInflater.from(WanSearchActivity.this).inflate(R.layout.item_search_tag, mFriendLayout, false);
                        tagView.setText(bean.getName());
                        ToolUtils.setTagTextColor(tagView);
                        return tagView;
                    }
                });
                mFriendLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        Friend list = response.get(position);
                        Article article = new Article();
                        article.setTitle(list.getName());
                        article.setLink(list.getLink());
                        Intent intent = new Intent(WanSearchActivity.this, WebViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("obj", article);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return false;
                    }
                });
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
        apiF.execute();
        Log.i("FriendApi", apiF.buildRealUrl());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack1){
            finish();
        }
        if (v.getId() == R.id.tvSearch){
            Intent intent = new Intent(WanSearchActivity.this, WanSearchDetailActivity.class);
            intent.putExtra("keyWord",edContent.getText().toString());
            startActivity(intent);
        }
    }
}
