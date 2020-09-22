package com.some.mix.wanandroidapi;

import com.some.mix.baseapi.MixApiUtil;
import com.some.mix.bean.wanandroid.Navi;
import com.some.mix.wanandroidapi.base.SimpleApi;

/**
 * @author cyl
 * @date 2020/8/11
 */
public class NaviApi extends SimpleApi<Navi> {

    @Override
    public String buildRealUrl() {
        return W_BASE_URL + getWorkAction();
    }

    @Override
    public String getWorkAction() {
        return MixApiUtil.Navi;
    }

    @Override
    public Class<Navi> SimpleClass() {
        return Navi.class;
    }
}
