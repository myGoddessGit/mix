package com.some.mix.wanandroidapi;

import com.some.mix.baseapi.MixApiUtil;
import com.some.mix.wanandroidapi.base.SimpleApi;
import com.some.mix.bean.wanandroid.Hotword;

/**
 * @author cyl
 * @date 2020/8/11
 * 玩安卓热词搜索Api
 */
public class HotKeyApi extends SimpleApi<Hotword> {

    @Override
    public String buildRealUrl() {
        return W_BASE_URL + getWorkAction();
    }

    @Override
    public String getWorkAction() {
        return MixApiUtil.HotKey;
    }

    @Override
    public Class<Hotword> SimpleClass() {
        return Hotword.class;
    }
}
