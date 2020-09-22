package com.some.mix.gankapi.base;

import android.util.Log;
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
 * @date 2020/8/19
 */
public class GSimpleApi<T> extends ActionApi {

    private static final String TAGFAIL = "GSimpleApiFail";

    private static final String TAGEXCEPTION = "GSimApiException";

    private static final String TAGERROR = "GSimApiError";

    private DataCallBack<List<T>> callBack;

    private DataCallBack<List<T>> getCallBack(){
        return callBack;
    }

    public void setCallBack(DataCallBack<List<T>> callBack){
        this.callBack = callBack;
    }

    @Override
    public String buildRealUrl() {
        return null;
    }

    @Override
    public String getWorkAction() {
        return null;
    }

    public Class<T> GSimpleClass(){
        return null;
    }

    @Override
    public void execute() {
        final MixJsonObjectRequest request = new MixJsonObjectRequest(buildRealUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status = null;
                try {
                    status = response.getString("status").toLowerCase();
                    if ("100".equals(status)){
                        JSONArray jsonArray = response.getJSONArray("data");
                        List<T> list = new ArrayList<T>();
                        try {
                            Gson gson = new Gson();
                            JsonArray array = new JsonParser().parse(jsonArray.toString()).getAsJsonArray();
                            for (JsonElement jsonElement : array){
                                list.add(gson.fromJson(jsonElement, GSimpleClass()));
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        if (getCallBack() != null){
                            getCallBack().onSuccess(list);
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
