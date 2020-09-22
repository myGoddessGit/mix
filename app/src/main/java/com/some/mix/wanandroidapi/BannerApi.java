package com.some.mix.wanandroidapi;

import com.some.mix.baseapi.MixApiUtil;
import com.some.mix.wanandroidapi.base.SimpleApi;
import com.some.mix.bean.wanandroid.Banner;

/**
 * @author cyl
 * @date 2020/8/10
 * 首页Banner  Api
 */
public class BannerApi extends SimpleApi<Banner> {

    @Override
    public String getWorkAction() {
        return MixApiUtil.Banner;
    }

    @Override
    public String buildRealUrl() {
        return W_BASE_URL + getWorkAction();
    }

    @Override
    public Class<Banner> SimpleClass() {
        return Banner.class;
    }
}
