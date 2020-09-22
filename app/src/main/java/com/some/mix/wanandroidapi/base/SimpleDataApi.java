package com.some.mix.wanandroidapi.base;

import android.text.TextUtils;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
public class SimpleDataApi<T> extends ActionApi {

    private static final String TAGFAIL = "SimpleDataApiFail";

    private static final String TAGEXCEPTION = "SimApiDataException";

    private static final String TAGERROR = "SimApiDataError";

    private DataCallBack<List<T>> callBack;

    private DataCallBack<List<T>> getCallBack(){
        return callBack;
    }

    public void setCallBack(DataCallBack<List<T>> callBack){
        this.callBack = callBack;
    }

    public Class<T> SimpleDataClass(){
        return null;
    }

    @Override
    public String buildRealUrl() {
        return null;
    }

    public int requestType(){
        return Request.Method.GET;
    }

    @Override
    public void execute() {
        final MixJsonObjectRequest request = new MixJsonObjectRequest(requestType(), buildRealUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String state = "";
                try {
                    state = response.getString("errorCode").toLowerCase();
                    if ("0".equals(state)){
                        JSONObject jsonObject = response.getJSONObject("data");
                        JSONArray jsonArray = null;
                        jsonArray = jsonObject.getJSONArray("datas");
                        List<T> list = new ArrayList<T>();
                        try {
                            Gson gson = new Gson();
                            JsonArray array = new JsonParser().parse(jsonArray.toString()).getAsJsonArray();
                            for (JsonElement jsonElement : array){
                                list.add(gson.fromJson(jsonElement, SimpleDataClass()));
                            }
                        } catch (Exception e){

                        }
                        if (getCallBack() != null){
                            getCallBack().onSuccess(list);
                        }
                    } else {
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
