package com.some.mix.volley

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.io.UnsupportedEncodingException

/**
 * @author cyl
 * @date 2020/10/24
 */
class XMLRequest : Request<XmlPullParser> {

    private var mListener : Response.Listener<XmlPullParser>? = null

    constructor(url : String, listener : Response.Listener<XmlPullParser>, errorListener: Response.ErrorListener) : super(Method.GET, url, errorListener) {
        mListener = listener
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<XmlPullParser> {
        return try {
            val xmlString = java.lang.String(response.data, "utf-8") as String
            val factory = XmlPullParserFactory.newInstance()
            val xmlPullParser = factory.newPullParser()
            xmlPullParser.setInput(StringReader(xmlString))
            Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e : UnsupportedEncodingException){
            Response.error(ParseError(e))
        } catch (e : XmlPullParserException ){
            Response.error(ParseError(e))
        }
    }

    override fun deliverResponse(response: XmlPullParser?) {
        mListener?.onResponse(response)
    }

}