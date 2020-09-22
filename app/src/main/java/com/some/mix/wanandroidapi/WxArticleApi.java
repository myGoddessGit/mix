package com.some.mix.wanandroidapi;

import com.some.mix.wanandroidapi.base.SimpleDataApi;
import com.some.mix.bean.wanandroid.Article;

/**
 * @author cyl
 * @date 2020/8/11
 * 微信公众号列表api
 */
public class WxArticleApi extends SimpleDataApi<Article> {

    /**
     * 文章ID
     */
    private int chapterId;

    /**
     * 分页
     */
    private int page;

    public void setChapterId(int mChapterId){
        this.chapterId = mChapterId;
    }

    public void setPage(int mPage){
        this.page = mPage;
    }

    @Override
    public String buildRealUrl() {
        return buildWxArticle(chapterId, page);
    }

    @Override
    public Class<Article> SimpleDataClass() {
        return Article.class;
    }
}
