package com.some.mix;

import android.app.Application;
import com.some.mix.utils.PreUtils;
import com.some.mix.volley.VolleyHelper;
import com.xcj.luck.MyApplication;

/**
 * @author cyl
 * @date 2020/8/10
 */
public class MixApplication extends MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyHelper.getInstance().init(getApplicationContext());
        PreUtils.init(getApplicationContext());
    }
}
