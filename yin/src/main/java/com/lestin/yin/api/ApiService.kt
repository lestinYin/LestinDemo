package com.future.taurus.api



import com.lestin.yin.entity.ECode
import com.lestin.yin.entity.EHome
import com.lestin.yin.entity.EStoreComentList
import com.lestin.yin.entity.EStoreDetail
import io.reactivex.Observable
import retrofit2.http.*


/**
 * @Description: Api服务类
 * @Author: Lestin.Yin
 * @CreateDate: 2019/7/19 17:58
 * @Version: 1.0
 */

interface ApiService {

    /**
     */
    @POST("")
    fun isRegistPhone(@Query("") phone: String): Observable<ECode>
    //注册
    @Headers("urlname:user_service")
    @POST("user_service/user")
    fun regist(@Query("phone") phone: String, @Query("password") password: String, @Query("email") email: String): Observable<ECode>//注册
    //登陆
    @Headers("urlname:user_service")
    @POST("user_service/login")
    fun login(@Query("phone") phone: String, @Query("password") password: String, @Query("email") email: String): Observable<ECode>
    //获取首页信息
    @FormUrlEncoded
    @POST("display/stores_reviews")
    fun getHome(@Field("city") city: String, @Field("themeId") themeId: String, @Field("tagId") tagId: String, @Field("pageNum") pageNum: String, @Field("pageSize") pageSize: String): Observable<EHome>//获取首页信息
    //获取店铺详情
    @FormUrlEncoded
    @POST("display/store_info")
    fun getStoreDetail(@Field("storeId") storeId: String, @Field("lat") lat: String, @Field("lnt") lnt: String, @Field("pageNum") pageNum: String, @Field("pageSize") pageSize: String): Observable<EStoreDetail>  //获取店铺详情
    //店铺评论列表
    @FormUrlEncoded
    @POST("display/store_comments")
    fun getStoreDetailComentList(@Field("storeId") storeId: String,@Field("pageNum") pageNum: String, @Field("pageSize") pageSize: String): Observable<EStoreComentList>//店铺评论列表
    //提交评论
    @Headers("urlname:user_service")
    @FormUrlEncoded
    @POST("user_service/comment")
    fun commitStoreComment(@Field("storeId") storeId: String,@Field("content") content: String): Observable<EStoreComentList>

}