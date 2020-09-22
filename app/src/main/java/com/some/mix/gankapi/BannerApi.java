package com.some.mix.gankapi;

import com.some.mix.baseapi.MixApiUtil;
import com.some.mix.bean.gank.Banner;
import com.some.mix.gankapi.base.GSimpleApi;

/**
 * @author cyl
 * @date 2020/8/19
 * gank banner api
 */
public class BannerApi extends GSimpleApi<Banner.DataBean> {

    @Override
    public String buildRealUrl() {
        return G_BASE_URL + getWorkAction();
    }

    @Override
    public String getWorkAction() {
        return MixApiUtil.GBanner;
    }

    @Override
    public Class<Banner.DataBean> GSimpleClass() {
        return Banner.DataBean.class;
    }
}
