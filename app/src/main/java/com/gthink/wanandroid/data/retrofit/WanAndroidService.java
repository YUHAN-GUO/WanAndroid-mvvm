package com.gthink.wanandroid.data.retrofit;

import com.base.gyh.baselib.data.bean.HttpResult;
import com.gthink.wanandroid.data.bean.ArticleDataBean;
import com.gthink.wanandroid.data.bean.BannerDataBean;
import com.gthink.wanandroid.data.bean.KnowledgeArticleBean;
import com.gthink.wanandroid.data.bean.KnowledgeBean;
import com.gthink.wanandroid.data.bean.LoginBean;
import com.gthink.wanandroid.data.bean.NavigationArticleBean;
import com.gthink.wanandroid.data.bean.ProjectArticleBean;
import com.gthink.wanandroid.data.bean.ProjectTreeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
 * created by taofu on 2018/11/27
 **/
public interface WanAndroidService {


    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    @POST("/user/login")
    @FormUrlEncoded
    Observable<HttpResult<LoginBean>> login(@Field("username") String userName, @Field("password") String password);

    /**
     * 注册
     *
     * @param userName
     * @param password
     * @param repssword
     * @return
     */
    @POST("/user/register")
    @FormUrlEncoded
    Observable<HttpResult<LoginBean>> register(@Field("username") String userName, @Field("password") String password, @Field("repassword") String repssword);


    /***
     * 获取首页banner数据
     * @return
     */
    @GET("/banner/json")
    Observable<HttpResult<List<BannerDataBean>>> getHomeBannerData();

    /***
     * 获取首页文章列表数据
     * @return
     */
    @GET("article/list/{pageNo}/json")
    Observable<HttpResult<ArticleDataBean>> getArticlesData(@Path("pageNo") int num);

    /**
     * 体系
     * @return
     */
    @GET("/tree/json")
    Observable<HttpResult<List<KnowledgeBean>>> getKnowledgeData();

    /**
     * 体系详情
     *
     * @param pageNo 页码从0开始
     * @param cid    从“/tree/json”获取
     * @return
     */
    @GET("/article/list/{pageNo}/json")
    Observable<HttpResult<KnowledgeArticleBean>> getKnowledgeArticles(@Path("pageNo") int pageNo, @Query("cid") int cid);


    /**
     * Project tree
     *
     * @return
     */
    @GET("/project/tree/json")
    Observable<HttpResult<List<ProjectTreeBean>>> getProjectsTree();


    /**
     * Project Article 页面
     *
     * @param pageNo
     * @param cid
     * @return
     */
    @GET("/project/list/{pageNo}/json")
    Observable<HttpResult<ProjectArticleBean>> getProjectArticles(@Path("pageNo") int pageNo, @Query("cid") int cid);


    /**
     * Navigation Data
     *
     * @return
     */
    @GET("/navi/json")
    Observable<HttpResult<List<NavigationArticleBean>>> getNavigationData();




    /**
     * 退出登录
     *
     * @return
     */
    @GET("/user/logout/json")
    Observable<HttpResult<Object>> loginOut();


    /***
     * 收藏站内文章
     * @param id
     * @return
     */
    @POST("lg/collect/{id}/json")
    Observable<HttpResult<Object>> collectArticle(@Path("id") int id);

        /***
     * 文章取消收藏
     * @param id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<HttpResult<Object>> unCollectArticle(@Path("id") int id);


//    @GET("/lg/collect/list/{pageNo}/json")
//    Observable<BaseBean<ArticleList>> getCollections(@Path("pageNo") int pageNo);
//
//    @POST("lg/uncollect/{id}/json")
//    @FormUrlEncoded
//    Observable<BaseBean<Object>> cancelCollectArticle(@Path("id") int id, @Field("originId") int originid);
//

//
//    @GET("/hotkey/json")
//    Observable<BaseBean<List<HotKeyBean>>> getHotKeys();
//
//    @POST("/article/query/{page}/json")
//    @FormUrlEncoded
//    Observable<BaseBean<ArticleList>> queryArticlesByKey(@Path("page") int page, @Field("k") String key);
//
//    @GET("/tree/json")
//    Observable<BaseBean<List<KnowledgeBean>>> getKnowledgeData();
//


}
