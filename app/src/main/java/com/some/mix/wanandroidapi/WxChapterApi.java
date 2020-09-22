package com.some.mix.wanandroidapi;
import com.some.mix.baseapi.MixApiUtil;
import com.some.mix.wanandroidapi.base.SimpleApi;
import com.some.mix.bean.wanandroid.Chapter;

/**
 * @author cyl
 * @date 2020/8/11
 * 微信公众号api
 */
public class WxChapterApi extends SimpleApi<Chapter> {

    @Override
    public String buildRealUrl() {
        return W_BASE_URL + getWorkAction();
    }

    @Override
    public String getWorkAction() {
        return MixApiUtil.WxChapter;
    }

    @Override
    public Class<Chapter> SimpleClass() {
        return Chapter.class;
    }
}
