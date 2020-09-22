package com.some.mix.bean.wanandroid;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/10
 */
public class BaseBean{

    private int errorCode;
    private String errorMsg;
    private List<Banner> mData;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<Banner> getmData() {
        return mData;
    }

    public void setmData(List<Banner> mData) {
        this.mData = mData;
    }
}
