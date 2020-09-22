package com.some.mix.callback;

/**
 * @author cyl
 * @date 2020/8/10
 */
public interface DataCallBack<T> {

    void onSuccess(T response);

    void onFail(String errorMsg);
}
