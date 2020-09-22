package com.some.mix.wanandroidapi;

import com.some.mix.baseapi.MixApiUtil;
import com.some.mix.wanandroidapi.base.SimpleApi;
import com.some.mix.bean.wanandroid.Friend;

/**
 * @author cyl
 * @date 2020/8/11
 * 玩安卓常用网站Api
 */
public class FriendApi extends SimpleApi<Friend> {

    @Override
    public String buildRealUrl() {
        return W_BASE_URL + getWorkAction();
    }

    @Override
    public String getWorkAction() {
        return MixApiUtil.Friend;
    }

    @Override
    public Class<Friend> SimpleClass() {
        return Friend.class;
    }
}
