package com.some.mix.constans;

/**
 * @author cyl
 * @date 2020/8/12
 * 干货集中营API V2 文档
 */
public class GankApiDetail {

    /**
     * gank api
     *
     *  首页轮播图Banner
     *  https://gank.io/api/v2/banners  请求方式: GET
     *  注意 : 返回首页轮播图的数据
     *
     *
     *
     *  分类 API
     *  https://gank.io/api/v2/categories/ <category_type> 请求方式: GET
     *  参数 <category_type> => Article | GanHuo | Girl
     *  Article => 专题分类  GanHuo => 干货分类  Girl  => Girl Image
     *  注:获取所有分类具体子分类[types]数据
     *  获取GanHuo所有的子分类
     *  https://gank.io/api/v2/categories/GanHuo
     *
     *  获取文章所搜的子分类
     *  https://gank.io/api/v2/categories/Article
     *
     *  获取Girl所有的子分类
     *  https://gank.io/api/v2/categories/Girl (该类型只有一项, 且为Girl)
     *
     *
     *
     *
     *  分类数据Api
     *  https://gank.io/api/v2/data/category/<category>/type/<type>/page/<page>/count/<count>
     *  请求方式GET
     *  参数 <category> => All | Article | GanHuo | Girl
     *  参数 <type> => GanHuo (All | Android | iOS | Flutter | frontend(前端) | app)
     *             => Article (All | Android | iOS | Flutter | frontend(前端) | backend(后端) | app)
     *  page => 分页 >= 1
     *  count: [10, 50] 每一页的数量
     *
     *  示例
     *  获取Girl 列表
     *  https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/10
     *
     *  获取Android干货列表
     *  https://gank.io/api/v2/data/category/GanHuo/type/Android/page/1/count/10
     *
     *
     *
     *
     *  随机API  (GET 请求)
     *  https://gank.io/api/v2/random/category/<category>/type/<type>/count/<count>
     *  参数 <category> => Article | GanHuo | Girl
     *  参数 <type> => GanHuo (Android | iOS | Flutter | frontend(前端) | app)
     *             => Article (Android | iOS | Flutter | frontend(前端) | backend(后端) | app)
     *  count: [1, 50] 随机的数量 1 到 50 之间
     *
     *  示例:
     *  获取干货分类下Android子分类的10个随机文章列表
     *  https://gank.io/api/v2/random/category/GanHuo/type/Android/count/10
     *
     *
     *
     *
     *  文章详情 API  (GET 请求)
     *  https://gank.io/api/v2/post/<post_id>
     *  <post_id> => Detail => 分类数据API返回的_id字段
     *
     *  示例
     *  获取5e777432b8ea09cade05263f的详情数据
     *  https://gank.io/api/v2/post/5e777432b8ea09cade05263f
     *
     *
     *
     *  本周最热 API  (GET请求)
     *  https://gank.io/api/v2/hot/<hot_type>/category/<category>/count/<count>
     *  请求方式: GET
     *  参数: <hot_type> => views (浏览数) | likes (点赞数) | comments (评论数)
     *        <category> => Article | GanHuo | Girl
     *        <count> => [1, 20]
     *
     *
     *
     *
     *  文章评论获取API  (GET 请求)
     *  https://gank.io/api/v2/post/comments/<post_id>
     *  参数 <post_id> => 分类数据API返回的_id字段
     *
     *
     *
     *
     *
     *  搜索 API (GET 请求)
     *  https://gank.io/api/v2/search/<search>/category/<category>/type/<type>/page/<page>/count/<count>
     *  参数  <search> => 搜索的内容
     *        <category> => All | Article | GanHuo
     *        <type> => GanHuo (All | Android | iOS | Flutter | frontend(前端) | app)
     *              => Article (All | Android | iOS | Flutter | frontend(前端) | backend(后端) | app)
     *        <count> => [10, 50] (每一页的内容 10 到 50)
     *        <page> => 分页 >= 1
     *
     *  示例
     *      搜索干货-Android分类下的Android关键字
     *      https://gank.io/api/v2/search/android/category/GanHuo/type/Android/page/1/count/10
     *
     *      搜索文章-Android分类下的Android关键字
     *      https://gank.io/api/v2/search/android/category/Article/type/Android/page/1/count/10
     *
     *      搜索全部分类下的Android关键字
     *      https://gank.io/api/v2/search/android/category/All/type/All/page/1/count/10
     */
}
