package com.some.mix.baseapi;

/**
 * @author cyl
 * @date 2020/8/11
 */
public class ActionApi extends BaseApi {

    /**
     * 玩安卓首页列表的真实api
     * @param
     * @return
     */
    public String buildHeadPage(int page){
        return W_BASE_URL + MixApiUtil.HeadPage + page + "/json";
    }

    /**
     * 玩安卓微信公众号列表真实的api
     * @param chapterId
     * @param page
     * @return
     */
    public String buildWxArticle(int chapterId, int page) {
        return W_BASE_URL + MixApiUtil.WxArticle + chapterId + "/" + page + "/json";
    }

    /**
     * 玩安卓知识体系下的文章的真实api
     * @param cid
     * @param page
     * @return
     */
    public String buildTreeArticle(int cid, int page){
        return W_BASE_URL + MixApiUtil.TreeArticle + page + "/json?cid=" + cid;
    }

    /**
     * 玩安卓项目首页下的文章的真实api
     * @param page
     * @param cid
     * @return
     */
    public String buildProjectArticle(int page, int cid){
        return W_BASE_URL + MixApiUtil.ProjectArticle + page + "/json?cid=" + cid;
    }

    /**
     * 玩安卓热词搜索的文章详情realApi
     * @param page
     * @param key
     * @return
     */
    public String buildHotKeyArticle(int page, String key){
        return W_BASE_URL + MixApiUtil.HotKeyArticle + page + "/json?k=" + keyName(key);
    }

    /**
     * 玩安卓收藏列表 realApi
     * @param page
     * @return
     */
    public String buildCollectArticle(int page) {
        return W_BASE_URL + MixApiUtil.CollectArticle + page + "/json";
    }

    /**
     * 玩安卓 登录 real api
     * @param name
     * @param password
     * @return
     */
    public String buildLogin(String name, String password){
        return W_BASE_URL + MixApiUtil.Login + "?username=" + keyName(name) + "&password=" + keyName(password);
    }

    /**
     * 玩安卓 收藏 文章的 realApi
     * @param id
     * @return
     */
    public String buildCollect(int id) {
        return W_BASE_URL + MixApiUtil.Collect + id + "/json";
    }

    /**
     * wanAndroid 取消收藏 realApi
     * @param originId
     * @return
     */
    public String buildUnCollect(int originId){
        return W_BASE_URL + MixApiUtil.CancelCollect + originId + "/json";
    }

    /**
     * gank  详情
     * @param cate
     * @param type
     * @param page
     * @param count
     * @return
     */
    public String buildDetailList(String cate, String type, int page, int count){
        return G_BASE_URL + MixApiUtil.detailList + keyName(cate) + "/type/" + keyName(type) + "/page/" + page + "/count/" + count;
    }

    public String buildRandomList(String cate, String type, int count){
        return G_BASE_URL + MixApiUtil.randomList +  keyName(cate) + "/type/" + keyName(type)+ "/count/" + count;
    }

    /**
     *
     * @param page 分页
     * @param count 每页数量
     * @return
     */
    public String buildGirlList(int page, int count){
        return G_BASE_URL  + MixApiUtil.girlList + "page/" + page +  "/count/" + count;
    }
}
