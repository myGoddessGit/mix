package com.some.mix.callback;

import org.json.JSONException;

/**
 * @author cyl
 * @date 2020/8/11
 * 登录的接口回调
 */
public interface LoginDataCallBack {

    void loginOk(Object object) throws JSONException;

    void loginFail(Object object);
}
