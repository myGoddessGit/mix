package com.some.mix.volley;

import android.content.Context;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

import java.io.File;

/**
 * @author cyl
 * @date 2020/8/10
 */
public class RunRequestQueue extends RequestQueue {

    private static final String DEFAULT_CACHE_DIR = "volley";

    public RunRequestQueue(Cache cache, Network network) {
        super(cache, network);
    }

    public void release(){
        super.stop();
    }

    public static RequestQueue newRequestQueue(Context context, HttpStack stack){
        File cacheDir = new File(context.getCacheDir(),DEFAULT_CACHE_DIR);
        if (stack == null){
            stack = new HurlStack();
        }
        Network network = new BasicNetwork(stack);
        RequestQueue queue = new RunRequestQueue(new DiskBasedCache(cacheDir), network);
        queue.start();
        return queue;
    }

    public static RequestQueue newRequestQueue(Context context){
        return newRequestQueue(context, null);
    }
}