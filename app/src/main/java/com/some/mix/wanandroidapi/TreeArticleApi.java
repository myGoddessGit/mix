package com.some.mix.wanandroidapi;

import com.some.mix.wanandroidapi.base.SimpleDataApi;
import com.some.mix.bean.wanandroid.Article;

/**
 * @author cyl
 * @date 2020/8/11
 * 玩安卓体系下的文章Api
 */
public class TreeArticleApi extends SimpleDataApi<Article> {

    /**
     * 文章的cid
     */
    private int cid;

    /**
     * 分页
     */
    private int page;

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String buildRealUrl() {
        return buildTreeArticle(cid, page);
    }

    @Override
    public Class<Article> SimpleDataClass() {
        return Article.class;
    }
}
