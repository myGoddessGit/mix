package com.some.mix.gankapi;

import com.some.mix.baseapi.MixApiUtil;
import com.some.mix.bean.gank.Category;
import com.some.mix.bean.gank.Detail;
import com.some.mix.gankapi.base.GSimpleApi;


/**
 * @author cyl
 * @date 2020/8/19
 *
 * gank Girl 分类 api
 */
public class GirlApi extends GSimpleApi<Detail.DataBean> {

    private int page;

    private int count;

    public void setPage(int page) {
        this.page = page;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String buildRealUrl() {
        return buildGirlList(page, count);
    }

    @Override
    public Class<Detail.DataBean> GSimpleClass() {
        return Detail.DataBean.class;
    }
}
