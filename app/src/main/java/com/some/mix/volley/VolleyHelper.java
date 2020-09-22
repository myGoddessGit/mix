package com.some.mix.volley;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * @author cyl
 * @date 2020/8/10
 */
public class VolleyHelper {

    private static VolleyHelper instance ;
    private RequestQueue taskQueue;
    private Context mContext;

    public static VolleyHelper getInstance(){
        if (instance == null){
            instance = new VolleyHelper();
        }
        return instance;
    }

    public void init(Context context){
        mContext = context;
    }

    public void addRequest(Request request) {
        try {
            if (taskQueue == null) {
                taskQueue = RunRequestQueue.newRequestQueue(mContext);
            } else {
                //taskQueue.start();
            }
            taskQueue.add(request);
        }catch (Exception e){
            Log.e("tag",e.toString());
        }
    }

    public void stopQueue() {
        if(taskQueue != null && taskQueue instanceof RunRequestQueue) {
            ((RunRequestQueue)taskQueue).release();
        }
    }
}

