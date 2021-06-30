package com.future.taurus.api.model
import com.future.taurus.api.RetrofitManager
import com.lestin.yin.entity.ECode
import com.lestin.yin.entity.EHome
import com.lestin.yin.entity.EStoreComentList
import com.lestin.yin.entity.EStoreDetail
import com.lestin.yin.scheduler.SchedulerUtils

import io.reactivex.Observable
import retrofit2.http.Query


/**
 * @ClassName:
 * @Description: java类作用描述
 * @Author: Lestin.Yin
 * @CreateDate: 2019/7/23 11:14
 * @Version: 1.0
 */

class MainModel {
    /**
     * 请求热门关键词的数据
     */
    fun testPhoneExist(phone: String): Observable<ECode> {
        return RetrofitManager.serviceHome.isRegistPhone(phone)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 注册
     */
    fun regist(phone: String,password: String,email:String): Observable<ECode> {
        return RetrofitManager.serviceHome.regist(phone,password,email)
                .compose(SchedulerUtils.ioToMain())
    }
    /**
     * 登陆
     */
    fun login(phone: String,password: String,email:String): Observable<ECode> {
        return RetrofitManager.serviceHome.login(phone,password,email)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 获取首页信息
     */
    fun getHome(city: String, themeId: String, tagId: String, pageNum: String,  pageSize: String): Observable<EHome> {
        return RetrofitManager.serviceHome.getHome(city,themeId,tagId,pageNum,pageSize)
                .compose(SchedulerUtils.ioToMain())
    }
    /**
     * 获取店铺详情
     */
    fun getStoreDetail(storeId: String, lat: String, lnt: String, pageNum: String,  pageSize: String): Observable<EStoreDetail> {
        return RetrofitManager.serviceHome.getStoreDetail(storeId,lat,lnt,pageNum,pageSize)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 获取店铺评论列表
     */
    fun getStoreComentList(storeId: String,pageNum: String,  pageSize: String): Observable<EStoreComentList> {
        return RetrofitManager.serviceHome.getStoreDetailComentList(storeId,pageNum,pageSize)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 提交评论
     */
    fun commitStoreComment(storeId: String,contentText: String): Observable<EStoreComentList> {
        return RetrofitManager.serviceHome.commitStoreComment(storeId,contentText)
                .compose(SchedulerUtils.ioToMain())
    }


}