package com.some.mix.wanandroidapi;

import com.some.mix.baseapi.MixApiUtil;
import com.some.mix.wanandroidapi.base.SimpleApi;
import com.some.mix.bean.wanandroid.ProjectCate;

/**
 * @author cyl
 * @date 2020/8/11
 * 玩安卓项目首页Api
 */
public class ProjectApi extends SimpleApi<ProjectCate> {

    @Override
    public String buildRealUrl() {
        return W_BASE_URL + getWorkAction();
    }

    @Override
    public String getWorkAction() {
        return MixApiUtil.Project;
    }

    @Override
    public Class<ProjectCate> SimpleClass() {
        return ProjectCate.class;
    }
}
