package com.some.mix.wanandroidapi;

import com.android.volley.Request;
import com.some.mix.wanandroidapi.base.SimpleDataApi;
import com.some.mix.bean.wanandroid.Article;

/**
 * @author cyl
 * @date 2020/8/11
 * 玩安卓热词搜索详情的
 */
public class HotKeyArticleApi extends SimpleDataApi<Article> {

    /**
     * 分页
     */
    private int page;

    /**
     * 搜索的关键字
     */
    private String key;

    public void setPage(int page) {
        this.page = page;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String buildRealUrl() {
        return buildHotKeyArticle(page, key);
    }

    @Override
    public Class<Article> SimpleDataClass() {
        return Article.class;
    }

    /**
     * 1 为 post请求
     * 0 为 get 请求 默认为get请求
     * @return
     */
    @Override
    public int requestType() {
        return Request.Method.POST;
    }
}
