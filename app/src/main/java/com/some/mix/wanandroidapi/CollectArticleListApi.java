package com.some.mix.wanandroidapi;

import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.some.mix.callback.DataCallBack;
import com.some.mix.constans.Constant;
import com.some.mix.utils.PreUtils;
import com.some.mix.volley.VolleyHelper;
import com.some.mix.baseapi.ActionApi;
import com.some.mix.bean.wanandroid.Article;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cyl
 * @date 2020/8/11
 */
public class CollectArticleListApi extends ActionApi {

    /**
     * 分页
     */
    private int page;

    public void setPage(int page) {
        this.page = page;
    }

    private DataCallBack<List<Article>> callBack;

    private DataCallBack<List<Article>> getCallBack(){
        return callBack;
    }

    public void setCallBack(DataCallBack<List<Article>> callBack) {
        this.callBack = callBack;
    }

    @Override
    public String buildRealUrl() {
        return buildCollectArticle(page);
    }

    @Override
    public void execute() {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, buildRealUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String state = null;
                try {
                    Gson gson = new Gson();
                    List<Article> list;
                    state = response.getString("errorCode").toLowerCase();
                    if ("0".equals(state)){
                        JSONObject object = response.getJSONObject("data");
                        JSONArray jsonArray = object.getJSONArray("datas");
                        list = gson.fromJson(jsonArray.toString(), new TypeToken<List<Article>>(){}.getType());
                        if (getCallBack() != null){
                            getCallBack().onSuccess(list);
                        }
                    }
                } catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return initResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return initMap();
            }
        };
        VolleyHelper.getInstance().addRequest(request);
    }

    public static Response<JSONObject> initResponse(NetworkResponse response){
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject object = new JSONObject(jsonString);
            object.put("cookie", (String)PreUtils.get(Constant.COOKIE, ""));
            return Response.success(object, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e){
            return Response.error(new ParseError(e));
        } catch (JSONException e){
            return Response.error(new ParseError(e));
        }
    }

    public static Map<String, String> initMap(){
        Map<String, String> map = new HashMap<>();
        map.put("cookie", (String) PreUtils.get(Constant.COOKIE, ""));
        return map;
    }
}
