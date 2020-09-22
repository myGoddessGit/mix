package com.some.mix.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.some.mix.activity.PhotoBrowserActivity;

/**
 * @author cyl
 * @date 2020/8/31
 */
public class LoadImageJavascriptInterface {

    private Context mContext;
    //private String[] imageUrls;

    public LoadImageJavascriptInterface(Context context){
        this.mContext = context;
        //this.imageUrls = strings;
    }

    @JavascriptInterface
    public void openImages(String img){
        Intent intent = new Intent();
        //intent.putExtra("imageUrls", imageUrls);
        intent.putExtra("curImageUrl", img);
        intent.setClass(mContext, PhotoBrowserActivity.class);
        mContext.startActivity(intent);
    }
}
