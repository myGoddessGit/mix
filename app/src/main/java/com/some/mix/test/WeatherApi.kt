package com.some.mix.test

import android.util.Log
import com.android.volley.Response
import com.some.mix.baseapi.BaseApi
import com.some.mix.volley.VolleyHelper
import com.some.mix.volley.XMLRequest
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException


/**
 * @author cyl
 * @date 2020/10/24
 */
class WeatherApi : BaseApi() {

    override fun buildRealUrl(): String {
        return "http://flash.weather.com.cn/wmaps/xml/china.xml"
    }
    override fun execute() {
        val xmlRequest = XMLRequest(buildRealUrl(),
            Response.Listener<XmlPullParser> {
                try {
                    var eventType = it.eventType
                    while (eventType != XmlPullParser.END_DOCUMENT){
                        if (eventType == XmlPullParser.START_TAG){
                            val nodeName = it.name
                            if ("city" == nodeName) {
                                val pName = it.getAttributeValue(0)
                                Log.i("WeatherApi", "pName is $pName")
                            }
                        }
                        eventType = it.next()
                    }
                } catch (e : XmlPullParserException){
                    e.printStackTrace()
                } catch (e : IOException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Log.i("WeatherApi", it.message)
            }
        )
        VolleyHelper.getInstance().addRequest(xmlRequest)
    }
}