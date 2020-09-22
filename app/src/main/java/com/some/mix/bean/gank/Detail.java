package com.some.mix.bean.gank;

import java.io.Serializable;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/12
 */
public class Detail implements Serializable {

    /**
     * data : [{"_id":"5e7f1b7ba7e6d153d814c29a","author":"李金山","category":"Article","createdAt":"2020-03-28 04:35:37","desc":"Flutter高级玩法贝塞尔曲线的表象认知与使用，贝塞尔可以创作出许多优美平滑的曲线。","images":["http://gank.io/images/6e4b5c819c48474f8ad6ae73898b7d7e"],"likeCounts":0,"publishedAt":"2020-03-28 04:35:37","stars":3,"title":"【Flutter高级玩法】 贝塞尔曲线的表象认知","type":"Flutter","url":"https://juejin.im/post/5e7ed37f51882573b435f329","views":718},{"_id":"5e798f841e12e48ff711862c","author":"李金山","category":"Article","createdAt":"2020-03-24 12:41:38","desc":"从一个页面到另一个页面的路由，是所有应用程序必不可少的的，当我们学习Flutter 框架或者任何框架时, 总是会遇到路由跳转的问题，如果所有的路由跳转方案向   一样简单。但是，当涉及到Flutter 路由跳转的时候，经过自己学习或者在项目的多次使用还是很容易掌握的，发现  Fluro 插件 ，也许Fluro 自己介绍的一样  ","images":["http://gank.io/images/b9e2c71029064cc6a8b21b763469e36e"],"likeCounts":0,"publishedAt":"2020-03-24 12:41:38","stars":3,"title":"在Flutter中使用Fluro插件进行路由跳转","type":"Flutter","url":"https://juejin.im/post/5e775b1b518825490e458630","views":544},{"_id":"5e798d471e12e48ff711862a","author":"李金山","category":"Article","createdAt":"2020-03-15 15:02:43","desc":"关于 Spuernova 我曾在 《Flutter Interact 的 Flutter 1.12 大进化和回顾》 中介绍过：在 2019 年末的 Flutter Interact 大会上，Spuernova 发布了对 Flutter 的支持，通过导入设计师的 Sketch 文件从而生成 Flutter 代码，这无疑提升了 Flutter 的生产力和可想象空间。","images":["http://gank.io/images/5aae44ec7a064fffb2a435d08e587ef1"],"likeCounts":0,"publishedAt":"2020-03-24 12:39:12","stars":4,"title":"Spuernova 是如何提升 Flutter 的生产力","type":"Flutter","url":"https://juejin.im/post/5e6e33b6518825492d4df8fd","views":495},{"_id":"5e552d4aefd6f28e2554f484","author":"李金山","category":"Article","createdAt":"2019-12-05 06:40:50","desc":"11 月 23 日，字节跳动技术沙龙 | Flutter 技术专场 在北京后山艺术空间圆满结束。我们邀请到字节跳动移动平台部 Flutter 架构师袁辉辉，Google Flutter 团队工程师 Justin McCandless，字节跳动移动平台部 Flutter 资深工程师李梦云，阿里巴巴高级技术专家王树彬和大家进行分享交流。","images":["http://gank.io/images/2b5955099a524aefa43a8788b73fb11a"],"likeCounts":1,"publishedAt":"2019-12-05 06:40:50","stars":4,"title":"如何缩减接近 50% 的 Flutter 包体积 | Flutter 沙龙回顾","type":"Flutter","url":"https://juejin.im/post/5de8a32c51882512664affa4","views":481},{"_id":"5e552d00efd6f28e2554f482","author":"李金山","category":"Article","createdAt":"2019-12-04 07:23:10","desc":"11 月 23 日，字节跳动技术沙龙 | Flutter 技术专场 在北京后山艺术空间圆满结束。我们邀请到字节跳动移动平台部 Flutter 架构师袁辉辉，Google Flutter 团队工程师 Justin McCandless，字节跳动移动平台部 Flutter 资深工程师李梦云，阿里巴巴高级技术专家王树彬和大家进行分享交流。","images":["http://gank.io/images/f3c8eee737d84a3b8e17b517a13ee4b7"],"likeCounts":4,"publishedAt":"2019-12-04 07:23:10","stars":1,"title":"跨平台技术趋势及字节跳动 Flutter 架构实践 | Flutter 沙龙回顾","type":"Flutter","url":"https://juejin.im/post/5de75c6b518825127c26f0e7","views":886}]
     * page : 1
     * page_count : 1
     * status : 100
     * total_counts : 5
     */

    private int page;
    private int page_count;
    private int status;
    private int total_counts;
    private List<DataBean> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal_counts() {
        return total_counts;
    }

    public void setTotal_counts(int total_counts) {
        this.total_counts = total_counts;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * _id : 5e7f1b7ba7e6d153d814c29a
         * author : 李金山
         * category : Article
         * createdAt : 2020-03-28 04:35:37
         * desc : Flutter高级玩法贝塞尔曲线的表象认知与使用，贝塞尔可以创作出许多优美平滑的曲线。
         * images : ["http://gank.io/images/6e4b5c819c48474f8ad6ae73898b7d7e"]
         * likeCounts : 0
         * publishedAt : 2020-03-28 04:35:37
         * stars : 3
         * title : 【Flutter高级玩法】 贝塞尔曲线的表象认知
         * type : Flutter
         * url : https://juejin.im/post/5e7ed37f51882573b435f329
         * views : 718
         */

        private String _id;
        private String author;
        private String category;
        private String createdAt;
        private String desc;
        private int likeCounts;
        private String publishedAt;
        private int stars;
        private String title;
        private String type;
        private String url;
        private int views;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getLikeCounts() {
            return likeCounts;
        }

        public void setLikeCounts(int likeCounts) {
            this.likeCounts = likeCounts;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
