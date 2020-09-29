package com.some.mix.constans;

/**
 * @author cyl
 * @date 2020/8/10
 */
public class Constant {

    public static final String W_ANDROID = "https://www.wanandroid.com/";

    public static final String G_GANK = "https://gank.io/api/v2/";

    public static final String O_OPEN_EYES = "http://baobab.kaiyanapp.com/api/";

    public static final String USER_INFO = "mUserInfo";  //用户信息
    public static final String IS_LOGIN = "mIsLogin";    //登录状态
    public static final String AES = "mAES";//用户信息密钥

    public static final String COOKIE = "CookieAll";

    public static final long DELAYMILLiIS = 1 * 1000L;

    //列表Type
    public static class LIST_TYPE {
        public static final int HOME = 0; //首页文章列表
        public static final int CHAPTER = 0; //公众号文章列表
        public static final int TREE = 1; //知识体系文章列表
        public static final int COLLECT = 2; //我的收藏
        public static final int SEARCH = 3; //搜索
    }
}
