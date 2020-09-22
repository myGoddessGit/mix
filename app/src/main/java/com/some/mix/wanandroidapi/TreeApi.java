package com.some.mix.wanandroidapi;

import com.some.mix.baseapi.MixApiUtil;
import com.some.mix.wanandroidapi.base.SimpleApi;
import com.some.mix.bean.wanandroid.Tree;

/**
 * @author cyl
 * @date 2020/8/11
 * 玩安卓知识体系
 */
public class TreeApi extends SimpleApi<Tree> {

    @Override
    public String buildRealUrl() {
        return W_BASE_URL + getWorkAction();
    }

    @Override
    public String getWorkAction() {
        return MixApiUtil.Tree;
    }

    @Override
    public Class<Tree> SimpleClass() {
        return Tree.class;
    }
}
