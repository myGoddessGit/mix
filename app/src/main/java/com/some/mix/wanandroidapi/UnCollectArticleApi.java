package com.some.mix.wanandroidapi;

/**
 * @author cyl
 * @date 2020/8/11
 * wanAndroid 取消收藏文章
 */
public class UnCollectArticleApi extends CollectArticleApi {

    /**
     * 收藏文章的originId or 首页文章的id
     */
    private int originId;

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    @Override
    public String buildRealUrl() {
        return buildUnCollect(originId);
    }
}
