package com.some.mix.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.just.agentweb.AgentWeb;
import com.some.mix.R;
import com.some.mix.bean.gank.Detail;
import com.some.mix.bean.wanandroid.Article;
import com.some.mix.utils.LoadImageJavascriptInterface;
import com.some.mix.utils.LoadImageWebViewClient;

import java.lang.reflect.Method;

/**
 * @author cyl
 * @date 2020/8/12
 */
public class WebViewActivity extends FragmentActivity implements View.OnClickListener{

    private String url = "";
    private String title = "";
    private FrameLayout mContainer;
    private TextView textView;
    private AgentWeb web;
    private Detail.DataBean detailBean;
    private Article articleBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initData();
        initView();
    }

    private void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            detailBean = (Detail.DataBean)bundle.getSerializable("url");
            articleBean = (Article)bundle.getSerializable("obj");
            if (detailBean != null){
                url = detailBean.getUrl();
                title = detailBean.getTitle();
            }
            if (articleBean != null){
                url = articleBean.getLink();
                title = articleBean.getTitle();
                if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.N){
                    title = Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString();
                } else {
                    title = Html.fromHtml(title).toString();
                }
            }
        }
    }
    private void initView(){
        findViewById(R.id.iv_topBarBack).setOnClickListener(this);
        findViewById(R.id.iv_topBarSearch).setVisibility(View.GONE);
        textView = findViewById(R.id.tv_topBarText);
        textView.setText(title);
        mContainer = findViewById(R.id.container);
        if (url.startsWith("gank://")){
            url = url.replace("gank://","https://gank.io://");
        }
        web = AgentWeb.with(this)
                    .setAgentWebParent(mContainer, new FrameLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator(R.color.black)
                    .createAgentWeb()
                    .ready()
                    .go(url);
        web.getWebCreator().getWebView().setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (url.startsWith("http") || url.startsWith("https")){
                    view.loadUrl(url);
                    return false;
                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(view.getUrl()));
                        startActivity(intent);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (web.handleKeyEvent(keyCode, event)){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null){
            menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder");
            try {
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(menu,true);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    protected void onResume() {
        web.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        web.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        web.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_topBarBack){
            finish();
        }
    }
}
