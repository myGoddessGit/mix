package com.some.mix.utils;

import android.view.View;

/**
 * @author cyl
 * @date 2020/8/12
 * 单击防抖
 */
public abstract class OnClickUtil implements View.OnClickListener{

    private static final int DELAY_TIME = 1000;
    private static long lastClickTime;

    public abstract void onMultiClick(View v);

    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= DELAY_TIME){
            lastClickTime = curClickTime;
            onMultiClick(v);
        }
    }
}
