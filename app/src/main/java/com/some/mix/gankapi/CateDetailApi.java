package com.some.mix.gankapi;

import com.some.mix.bean.gank.Detail;
import com.some.mix.gankapi.base.GSimpleApi;

/**
 * @author cyl
 * @date 2020/8/19
 * gank  类型分类列表详情  api
 */
public class CateDetailApi extends GSimpleApi<Detail.DataBean> {

    private String cate;

    private String type;

    private int page;

    private int count;

    private String cateApi;

    public void setCateApi(String cateApi) {
        this.cateApi = cateApi;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int defaultCount(){
        return 15;
    }


    @Override
    public String buildRealUrl() {
        if ("random".equals(cateApi)){
            return buildRandomList(cate, type, count);
        }
        return buildDetailList(cate, type, page, count);
    }

    @Override
    public Class<Detail.DataBean> GSimpleClass() {
        return Detail.DataBean.class;
    }
}
