package com.some.mix.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cyl
 * @date 2020/8/10
 */
public class MixJsonObjectRequest extends JsonObjectRequest {

    private Map<String, String> headers = new HashMap<>();
    public MixJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    public MixJsonObjectRequest(int method, String url,JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            int tagIndex = jsonString.indexOf("{");
            if (tagIndex > 0) {
                jsonString = jsonString.substring(tagIndex);
            }
            JSONObject jsonObject = new JSONObject(jsonString);
            return Response.success(jsonObject,
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }
}
