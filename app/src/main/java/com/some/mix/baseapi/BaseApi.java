package com.some.mix.baseapi;

import com.some.mix.constans.Constant;

import java.net.URLEncoder;

/**
 * @author cyl
 * @date 2020/8/10
 */
public abstract class BaseApi {

    public static final String W_BASE_URL = Constant.W_ANDROID;

    public static final String G_BASE_URL = Constant.G_GANK;

    public String getWorkAction() {
        return null;
    }

    public String buildRealUrl(){
        return null;
    }

    public void execute(){

    }

    protected String keyName(String key){
        try {
            return URLEncoder.encode(key, "utf-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
