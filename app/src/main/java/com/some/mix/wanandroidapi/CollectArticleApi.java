package com.some.mix.wanandroidapi;

import android.text.TextUtils;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.some.mix.callback.DataCallBack;
import com.some.mix.volley.VolleyHelper;
import com.some.mix.baseapi.ActionApi;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author cyl
 * @date 2020/8/11
 * 收藏文章的Api
 */
public class CollectArticleApi extends ActionApi {

    /**
     * 文章id
     */
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    private DataCallBack<JSONObject> callBack;

    private DataCallBack<JSONObject> getCallBack(){
        return callBack;
    }

    public void setCallBack(DataCallBack<JSONObject> callBack) {
        this.callBack = callBack;
    }

    @Override
    public String buildRealUrl() {
        return buildCollect(id);
    }

    @Override
    public void execute() {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, buildRealUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String state = null;
                try {
                    state = response.getString("errorCode").toLowerCase();
                    if ("0".equals(state)){
                        if (getCallBack() != null){
                            getCallBack().onSuccess(response);
                        }
                    } else  {
                        String message = response.getString("errorMsg");
                        if (!TextUtils.isEmpty(message)){
                            if (getCallBack() != null){
                                getCallBack().onFail(message);
                            }
                        }
                    }
                } catch (JSONException e){
                    if (getCallBack() != null){
                        getCallBack().onFail(e.toString());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (getCallBack() != null){
                    getCallBack().onFail(error.toString());
                }
            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return CollectArticleListApi.initResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return CollectArticleListApi.initMap();
            }
        };

        VolleyHelper.getInstance().addRequest(request);
    }
}
