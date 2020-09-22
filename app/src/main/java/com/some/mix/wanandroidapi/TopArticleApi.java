package com.some.mix.wanandroidapi;

import com.some.mix.baseapi.MixApiUtil;
import com.some.mix.wanandroidapi.base.SimpleApi;
import com.some.mix.bean.wanandroid.Article;

/**
 * @author cyl
 * @date 2020/8/11
 * 玩安卓首页置顶文章Api
 */
public class TopArticleApi extends SimpleApi<Article> {
    
    @Override
    public String buildRealUrl() {
        return W_BASE_URL + getWorkAction();
    }

    @Override
    public String getWorkAction() {
        return MixApiUtil.TopArticle;
    }

    @Override
    public Class<Article> SimpleClass() {
        return Article.class;
    }
}
