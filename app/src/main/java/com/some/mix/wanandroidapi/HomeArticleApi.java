package com.some.mix.wanandroidapi;

import com.some.mix.wanandroidapi.base.SimpleDataApi;
import com.some.mix.bean.wanandroid.Article;

/**
 * @author cyl
 * @date 2020/8/11
 * 玩安卓首页文章列表
 */
public class HomeArticleApi extends SimpleDataApi<Article> {

    /**
     * 分页
     */
    private int page;

    public void setPage(int mPage){
        this.page = mPage;
    }

    @Override
    public String buildRealUrl() {
        return buildHeadPage(page);
    }

    @Override
    public Class<Article> SimpleDataClass() {
        return Article.class;
    }
}
