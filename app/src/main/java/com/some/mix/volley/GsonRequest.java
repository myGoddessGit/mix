package com.some.mix.volley;

import com.android.volley.*;
import com.android.volley.Response.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cyl
 * @date 2020/8/10
 */
public class GsonRequest<T> extends Request<T> {

    private final Listener<T> mListener;
    private static Gson mGson = new Gson();
    private Class<T> mClass;
    private Map<String, String> mParams = new HashMap<>();
    private TypeToken<T> mTypeToken;

    public GsonRequest(int method,  String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        mClass = clazz;
        mListener = listener;
        setMyRetryPolicy();
    }

    public GsonRequest(int method, String url, TypeToken<T> typeToken, Listener<T> listener, ErrorListener errorListener){
        super(method, url, errorListener);
        mTypeToken = typeToken;
        mListener = listener;
        setMyRetryPolicy();
    }

    private void setMyRetryPolicy(){
        setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public GsonRequest(String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener){
        this(Method.GET, url, clazz, listener, errorListener);
    }

    public GsonRequest(String url, TypeToken<T> typeToken, Listener<T> listener, ErrorListener errorListener){
        this(Method.GET, url, typeToken, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if (mTypeToken == null){
                return Response.success(mGson.fromJson(jsonString, mClass), HttpHeaderParser.parseCacheHeaders(response));
            } else  {
                return (Response<T>) Response.success(mGson.fromJson(jsonString, mTypeToken.getType()), HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e){
            return Response.error(new ParseError((e)));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
