package com.some.mix.wanandroidapi.base;

import android.text.TextUtils;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.*;
import com.some.mix.baseapi.ActionApi;
import com.some.mix.callback.DataCallBack;
import com.some.mix.volley.MixJsonObjectRequest;
import com.some.mix.volley.VolleyHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/11
 */
public class SimpleApi<T> extends ActionApi {

    private static final String TAGFAIL = "SimpleApiFail";

    private static final String TAGEXCEPTION = "SimApiException";

    private static final String TAGERROR = "SimApiError";

    private DataCallBack<List<T>> callBack;

    private DataCallBack<List<T>> getCallBack(){
        return callBack;
    }

    public Class<T> SimpleClass(){
       return null;
    }

    public void setCallBack(DataCallBack<List<T>> callBack){
        this.callBack = callBack;
    }

    @Override
    public String getWorkAction() {
        return null;
    }

    @Override
    public String buildRealUrl() {
        return null;
    }



    @Override
    public void execute() {
        final MixJsonObjectRequest request = new MixJsonObjectRequest(buildRealUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String state = null;
                try {
                    state = response.getString("errorCode").toLowerCase();
                    if ("0".equals(state)){
                        JSONArray jsonArray = response.getJSONArray("data");
                        List<T> list = new ArrayList<T>();
                        try {
                            Gson gson = new Gson();
                            JsonArray array = new JsonParser().parse(jsonArray.toString()).getAsJsonArray();
                            for (JsonElement jsonElement : array){
                                list.add(gson.fromJson(jsonElement, SimpleClass()));
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        if (getCallBack() != null){
                            getCallBack().onSuccess(list);
                        }
                    } else  {
                        String message = response.getString("errorMsg");
                        if (!TextUtils.isEmpty(message)){
                            if (getCallBack() != null){
                                callBack.onFail(message);
                            }
                            Log.i(TAGFAIL, message);
                        }
                    }
                } catch (JSONException e){
                    Log.i(TAGEXCEPTION, e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (getCallBack() != null){
                    getCallBack().onFail(error.toString());
                }
                Log.i(TAGERROR, error.getMessage());
            }
        });
        VolleyHelper.getInstance().addRequest(request);
    }

}
