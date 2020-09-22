package com.some.mix.wanandroidapi;

import com.some.mix.wanandroidapi.base.SimpleDataApi;
import com.some.mix.bean.wanandroid.Article;

/**
 * @author cyl
 * @date 2020/8/11
 */
public class ProjectArticleApi extends SimpleDataApi<Article> {

    /**
     * 分页
     */
    private int page;

    /**
     * 文章的cid
     */
    private int cid;

    public void setPage(int page) {
        this.page = page;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public String buildRealUrl() {
        return buildProjectArticle(page, cid);
    }

    @Override
    public Class<Article> SimpleDataClass() {
        return Article.class;
    }
}
