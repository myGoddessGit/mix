package com.some.mix.baseapi;

/**
 * @author cyl
 * @date 2020/8/10
 */
public class MixApiUtil {

    /**
     * 玩安卓首页Banner
     */
    public static final String Banner = "banner/json";

    /**
     * 玩安卓首页列表 (后缀在ActionApi类中拼接)
     */
    public static final String HeadPage = "article/list/";

    /**
     * 玩安卓微信公众号
     */
    public static final String WxChapter = "wxarticle/chapters/json";

    /**
     * 玩安卓微信公众号列表 (后缀在ActionApi类中拼接)
     */
    public static final String WxArticle = "wxarticle/list/";

    /**
     * 玩安卓知识体系
     */
    public static final String Tree = "tree/json";

    /**
     * 玩安卓知识体系下的文章列表 (后缀在ActionApi类中拼接)
     */
    public static final String TreeArticle = "article/list/";

    /**
     * 玩安卓 项目首页
     */
    public static final String Project = "project/tree/json";

    /**
     * 玩安卓项目首页详情列表 (后缀在ActionApi类中拼接)
     */
    public static final String ProjectArticle = "project/list/";

    /**
     * 玩安卓热词搜索
     */
    public static final String HotKey = "/hotkey/json";

    /**
     * 玩安卓热词搜索详情 (后缀在ActionApi类中拼接)
     */
    public static final String HotKeyArticle = "article/query/";

    /**
     * 玩安卓常用网站
     */
    public static final String Friend = "friend/json";

    /**
     * 玩安卓首页置顶文章
     */
    public static final String TopArticle = "article/top/json";

    /**
     * 玩安卓收藏列表 (后缀在ActionApi类中拼接)
     */
    public static final String CollectArticle = "lg/collect/list/";

    public static final String Login = "user/login";

    /**
     * 玩安卓 收藏文章 (后缀在ActionApi类中拼接)
     */
    public static final String Collect = "lg/collect/";

    /**
     * wanAndroid 取消收藏文章 (后缀在ActionApi类中拼接)
     */
    public static final String CancelCollect = "lg/uncollect_originId/";

    /**
     * wanAndroid 导航数据
     */
    public static final String Navi = "navi/json";

    /**
     * 干货集中营  banner 数据
     */
    public static final String GBanner = "banners";
    /**
     * 干货集中营 干货分类
     */
    public static final String GanHuoCate = "categories/GanHuo";

    /**
     * 干货集中营 文章分类
     */
    public static final String ArticleCate = "categories/Article";

    /**
     * 干货集中营  Girl 分类
     */
    public static final String GirlCate = "categories/Girl";

    /**
     * gank 分类详细数据  完整api 在ActionApi 中拼接
     */
    public static final String detailList = "data/category/";

    /**
     * gank 随机数据  完整api 在ActionApi中拼接
     */
    public static final String randomList = "random/category/";

    /**
     * gank girl 数据 完整api 在ActionApi中拼接
     */
    public static final String girlList = "data/category/Girl/type/Girl/";

}
