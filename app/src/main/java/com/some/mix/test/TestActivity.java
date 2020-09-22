package com.some.mix.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.just.agentweb.AgentWeb;
import com.some.mix.R;
import com.some.mix.activity.PhotoBrowserActivity;
import com.some.mix.adapter.GankDetailAdapter;
import com.some.mix.bean.gank.Detail;
import com.some.mix.gankapi.CateDetailApi;
import com.some.mix.callback.DataCallBack;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/10
 */
public class TestActivity extends FragmentActivity {

    private WebView webView;
    private String content = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_test);
        content = "<ul><li>单位性质：其他企业</li> <li>单位行业：教育</li> <li>单位规模：10000人以上</li> </ul><ul><li>宣讲时间：2020-09-02 19:00-21:00(周三)</li> <li>宣讲学校：三峡大学</li> <li>简历投递邮箱：[email protected]</li> <li>招聘部门电话： 15****52501（登录后查看联系方式） </li> <li>宣讲类别：空中宣讲</li> </ul> <div> <ul><li>宣讲会详情</li> <li>单位简介</li> </ul><div> <p><b>学而思网校202</b><b>0秋招空中宣讲会</b></p><p>线上宣讲会时间：2020年9月2日晚上19:00</p><p>参会方式：腾讯会议</p><p>点击链接入会，或添加至会议列表： https://meeting.tencent.com/s/tr9QHLCZrIo0 </p><p>会议 ID：580 428 905</p><p><b>【公司介绍】</b></p><p>学而思网校是好未来教育集团（2010年纽交所上市）旗下的中小学在线教育品牌，也是国内较早整合“互联网”与“教育”两大领域的中小学在线学习平台之一。</p><p>学而思网校，为3-18岁孩子提供小初高全学科课外教学。十余年教学沉淀，“直播+辅导”双师模式，AI技术辅助教学。老师带着学，私教带着练，课堂互动多，课程有回放，及时答疑，随时退费，全国200多个城市的中小学生都在网校学习。</p><p>见证互联网改变学习，不做在线教育风口的旁观者。我们期待你的加入！</p><p><b>【项目介绍】</b></p><p>学而思网校人才选拔项目——“沸点计划”已经正式启动，项目旨在选拔、培养愿意从事“互联网+教育”行业的优秀人才，为行业进步贡献新生力量。</p><p>“沸点计划”致力于为优秀学子提供一个优质的就业、实习平台，让更多人才加入学而思网校，接触学而思网校核心业务，了解学而思网校的文化价值，也为学而思网校未来发展储备优秀的核心骨干。</p><p>FIND YOUR PASSION AND DO IT !————“沸点计划”期待与你相遇，沸腾热爱，点燃梦想，一起推动“互联网+教育”的进步！</p><p><b>【招聘岗位】</b></p><p>学而思网校在线班主任</p><p><b>【招聘科目】</b></p><p>小学：语文</p><p><b>【工作职责】</b></p><p>1、参与直播课程，重点跟踪学员在网校的学习效果；</p><p>2、检查学生学习效果，优化教学方法，及时跟踪反馈提升教学质量；</p><p>3、学员班级群内维护答疑，解决学员家长问题；</p><p>4、与家长保持良好沟通，及时反馈学生学习情况，关注学员课程报名情况。 </p><p><b>【任职要求】</b></p><p>1、统招全日制本科及以上学历， 19-21届毕业生；</p><p>2、学习能力强，中小学基础知识扎实</p><p>3、普通话标准，良好的沟通能力和团队合作能力；</p><p>4、有责任心，愿意投身教育事业。</p><p><b>【</b><b>上班时间</b><b>】</b></p><p>每周四至下周一 ：13:30-21:30（周二、周三双休）</p><p><b>【薪酬福利】</b></p><p>薪资：年薪<b>8-12万</b>，每年两次涨薪机会<b>（实习期与正式员工同薪）</b></p><p>福利：</p><p>六险一金（医疗、养老、生育、工伤、失业、商保+住房公积金）；</p><p>无息住房贷款、带薪年假、每日水果零食、晚餐、加班企业滴滴、</p><p>年度体检、直系亲属报班优惠、企业职业培训、团队建设、婚嫁礼金、节日礼物等。</p><p><b>【晋升路线】</b></p><p>晋升路线：教委-组长-学科负责人-基地负责人</p><p>专业路线：初级-中级-资深班主任教师</p><p>职能路线：教研/质控/选聘等等</p><p><b>【面试流程】</b></p><p>初试（线上面谈）→培训选拔（带薪培训）→签约入职</p><p><b>【工作地点】</b></p><p>1.分公司：济南、武汉、西安、成都、长沙、石家庄、镇江、南京、长春、重庆、郑州、合肥、天津、沈阳、北京共15地。</p><p>2.武汉分校地点：武汉市硚口区越秀财富中心</p><p><img title=\"1598852382355506.png\" alt=\"image.png\" src=\"http://cdn5.haitou.cc/sp0/ctgu.91wllm.com/attachment/ctgu/ueditor/images/20200831/1598852382355506.png\" /> </p><p><img title=\"1598852391820698.png\" alt=\"image.png\" src=\"http://cdn5.haitou.cc/sp0/ctgu.91wllm.com/attachment/ctgu/ueditor/images/20200831/1598852391820698.png\" /> </p><p><b>【联系方式】</b></p><p>联系方式：李老师 15572752501（微信同号）</p><p>电子邮箱: lishiqi6@tal.com</p><p>可加入学而思网校湖北2021届招聘群获取更多信息~</p><p>QQ群号：938131069</p><p><b>【投递简历】 </b></p><p>扫码投递简历 扫码加入空宣微信答疑群</p><p> <img title=\"1598852404178955.png\" alt=\"image.png\" src=\"http://cdn5.haitou.cc/sp0/ctgu.91wllm.com/attachment/ctgu/ueditor/images/20200831/1598852404178955.png\" /> <img title=\"1598852418624611.png\" alt=\"image.png\" src=\"http://cdn5.haitou.cc/sp0/ctgu.91wllm.com/attachment/ctgu/ueditor/images/20200831/1598852418624611.png\" /> </p> </div> <div> <div><p><b>学而思网校202</b><b>0秋招</b></p><p><b>【公司介绍】</b></p><p>学而思网校是好未来教育集团（2010年纽交所上市）旗下的中小学在线教育品牌，也是国内较早整合“互联网”与“教育”两大领域的中小学在线学习平台之一。</p><p>学而思网校，为3-18岁孩子提供小初高全学科课外教学。十余年教学沉淀，“直播+辅导”双师模式，AI技术辅助教学。老师带着学，私教带着练，课堂互动多，课程有回放，及时答疑，随时退费，全国200多个城市的中小学生都在网校学习。</p><p>见证互联网改变学习，不做在线教育风口的旁观者。我们期待你的加入！</p><p><b>【项目介绍】</b></p><p>学而思网校人才选拔项目——“沸点计划”已经正式启动，项目旨在选拔、培养愿意从事“互联网+教育”行业的优秀人才，为行业进步贡献新生力量。</p><p>“沸点计划”致力于为优秀学子提供一个优质的就业、实习平台，让更多人才加入学而思网校，接触学而思网校核心业务，了解学而思网校的文化价值，也为学而思网校未来发展储备优秀的核心骨干。</p><p>FIND YOUR PASSION AND DO IT !————“沸点计划”期待与你相遇，沸腾热爱，点燃梦想，一起推动“互联网+教育”的进步！</p><p><b>【招聘岗位】</b></p><p>学而思网校在线班主任</p><p><b>【招聘科目】</b></p><p>小学：语文</p><p><b>【工作职责】</b></p><p>1、参与直播课程，重点跟踪学员在网校的学习效果；</p><p>2、检查学生学习效果，优化教学方法，及时跟踪反馈提升教学质量；</p><p>3、学员班级群内维护答疑，解决学员家长问题；</p><p>4、与家长保持良好沟通，及时反馈学生学习情况，关注学员课程报名情况。 </p><p><b>【任职要求】</b></p><p>1、统招全日制本科及以上学历， 19-21届毕业生；</p><p>2、学习能力强，中小学基础知识扎实</p><p>3、普通话标准，良好的沟通能力和团队合作能力；</p><p>4、有责任心，愿意投身教育事业。</p><p><b>【</b><b>上班时间</b><b>】</b></p><p>每周四至下周一 ：13:30-21:30（周二、周三双休）</p><p><b>【薪酬福利】</b></p><p>薪资：年薪<b>8-12万</b>，每年两次涨薪机会<b>（实习期与正式员工同薪）</b></p><p>福利：</p><p>六险一金（医疗、养老、生育、工伤、失业、商保+住房公积金）；</p><p>无息住房贷款、带薪年假、每日水果零食、晚餐、加班企业滴滴、</p><p>年度体检、直系亲属报班优惠、企业职业培训、团队建设、婚嫁礼金、节日礼物等。</p><p><b>【晋升路线】</b></p><p>晋升路线：教委-组长-学科负责人-基地负责人</p><p>专业路线：初级-中级-资深班主任教师</p><p>职能路线：教研/质控/选聘等等</p><p><b>【面试流程】</b></p><p>初试（线上面谈）→培训选拔（带薪培训）→签约入职</p><p><b>【工作地点】</b></p><p>1.分公司：济南、武汉、西安、成都、长沙、石家庄、镇江、南京、长春、重庆、郑州、合肥、天津、沈阳、北京共15地。</p><p>2.武汉分校地点：武汉市硚口区越秀财富中心</p><p><img title=\"1598852382355506.png\" alt=\"image.png\" src=\"https://ctgu.91wllm.com/attachment/ctgu/ueditor/images/20200831/1598852382355506.png\" /> </p><p><img title=\"1598852391820698.png\" alt=\"image.png\" src=\"https://ctgu.91wllm.com/attachment/ctgu/ueditor/images/20200831/1598852391820698.png\" /> </p><p><b>【联系方式】</b></p><p>联系方式：李老师 15572752501（微信同号）</p><p>电子邮箱: lishiqi6@tal.com</p><p>可加入学而思网校湖北2021届招聘群获取更多信息~</p><p>QQ群号：938131069</p><p><b>【投递简历】 </b></p><p>扫码投递简历 </p><p> <img title=\"1598852404178955.png\" alt=\"image.png\" src=\"https://ctgu.91wllm.com/attachment/ctgu/ueditor/images/20200831/1598852404178955.png\" /> </p><p> 扫码加入空宣微信答疑群</p><p> <img title=\"1598852418624611.png\" alt=\"image.png\" src=\"https://ctgu.91wllm.com/attachment/ctgu/ueditor/images/20200831/1598852418624611.png\" /></p></div> </div> </div> <div> <table border=1 cellspacing=0 cellpadding=0><tr><td rowspan=\"4\"><img src=\"http://cdn5.haitou.cc/sp0/ctgu.91wllm.com/qrcode/img?size=3&amp;margin=1&amp;encode=1&amp;url=93f1c137ca2174ccc83bczo0MzoiaHR0cHM6Ly9tZWV0aW5nLnRlbmNlbnQuY29tL3MvdHI5UUhMQ1pySW8wICI7\" alt=\"图片\" /></td> <td>学而思网校2020空中宣讲会</td> </tr><tr><td>宣讲日期：2020-09-02(周三)</td> </tr><tr><td>宣讲时间：19:00-21:00</td> </tr><tr><td>线上宣讲，点击进入</td> </tr><tr><td>手机上观看</td> <td></td> </tr></table></div>";
        initWebView();
    }

    private void initWebView(){
        webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        content = "<div id='main-wrapper' style=\\\"color:#555;  padding:8px 0;\\\">" + content + "</div>";
        webView.loadDataWithBaseURL(null, content,"text/html", "utf-8", null);
        webView.addJavascriptInterface(new JavascriptInterface(this), "imagelistener");
        webView.setWebViewClient(new MyWebViewClient());
    }


    // 注入js函数监听
    private void addImageClickListener() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "var imgUrl = \"\";"+
                "for(var i=0;i<objs.length;i++) " +
                "{" +
                    "imgUrl += objs[i].src" + ' ' + ','
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistener.openImage(this.src, imgUrl);  " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
                "    }  " +
                "}" +
                "})()");
    }

    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }
        @android.webkit.JavascriptInterface
        public void openImage(String img, String imageUrl) {
            Log.i("TestAC", imageUrl + "11");
            String[] imgs = imageUrl.split("https:");
            ArrayList<String> imageUrlList = new ArrayList<>();
            for (String s : imgs){
                imageUrlList.add(s);
            }
            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.putExtra("imageUrl", imageUrlList);
            intent.setClass(context, PhotoBrowserActivity.class);
            context.startActivity(intent);
            //System.out.println(img);
        }
    }

    // 监听
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListener();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }
}
