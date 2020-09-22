package com.some.mix.wanandroidapi;

import android.text.TextUtils;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.some.mix.callback.LoginDataCallBack;
import com.some.mix.constans.Constant;
import com.some.mix.utils.PreUtils;
import com.some.mix.volley.VolleyHelper;
import com.some.mix.baseapi.ActionApi;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cyl
 * @date 2020/8/11
 */
public class LoginApi extends ActionApi {

    private static final String TAGFAIL = "LoginApiFail";

    private static final String TAGEXCEPTION = "LoginException";

    private static final String TAGERROR = "LoginError";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private LoginDataCallBack callBack;

    private LoginDataCallBack getCallBack(){
        return callBack;
    }

    public void setCallBack(LoginDataCallBack callBack){
        this.callBack = callBack;
    }
    @Override
    public String buildRealUrl() {
        return buildLogin(username, password);
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
                            getCallBack().loginOk(response);
                        }
                    } else {
                        String message = response.getString("errorMsg");
                        if (!TextUtils.isEmpty(message)){
                            if (getCallBack() != null){
                                callBack.loginFail(message);
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
                    getCallBack().loginFail(error.toString());
                }
                Log.i(TAGERROR, error.getMessage());
            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                HashMap<String, String> cookieMap = new HashMap<>();
                for (int i = 1; i < response.allHeaders.size(); i++){
                    Header h = response.allHeaders.get(i);
                    if (h.getName().equalsIgnoreCase("Set-Cookie")){
                        if (h.getValue() != null){
                            String firstItem = h.getValue().substring(0, h.getValue().indexOf(";"));
                            String[] cookiePair = firstItem.split("=");
                            if (cookiePair.length == 2){
                                cookieMap.put(cookiePair[0], cookiePair[1]);
                            }
                        }
                    }
                }
                StringBuilder builder = new StringBuilder();
                for (Map.Entry<String, String> entry : cookieMap.entrySet()){
                    builder.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
                }
                PreUtils.put(Constant.COOKIE, builder.toString());
                return super.parseNetworkResponse(response);
            }
        };
        VolleyHelper.getInstance().addRequest(request);
    }
}
