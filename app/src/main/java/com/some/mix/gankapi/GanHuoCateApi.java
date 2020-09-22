package com.some.mix.gankapi;

import com.some.mix.baseapi.MixApiUtil;
import com.some.mix.bean.gank.Category;
import com.some.mix.gankapi.base.GSimpleApi;

/**
 * @author cyl
 * @date 2020/8/19
 * Gank 干货分类
 */
public class GanHuoCateApi extends GSimpleApi<Category.DataBean> {

    @Override
    public String buildRealUrl() {
        return G_BASE_URL + getWorkAction();
    }

    @Override
    public String getWorkAction() {
        return MixApiUtil.GanHuoCate;
    }

    @Override
    public Class<Category.DataBean> GSimpleClass() {
        return Category.DataBean.class;
    }
}
