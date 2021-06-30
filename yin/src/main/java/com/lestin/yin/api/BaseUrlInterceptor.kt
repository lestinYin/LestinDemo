package com.lestin.yin.api

import android.R.attr.port
import android.R.attr.host
import android.R.attr.scheme
import android.provider.SyncStateContract
import android.util.Log
import com.future.taurus.api.UriConstant
import com.lestin.yin.Constants
import com.lestin.yin.utils.LogUtil
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 *
 * @ProjectName:    Taurus
 * @Package:        com.lestin.yin.api
 * @ClassName:      BaseUrlInterceptor
 * @Description:     baseurl拦截器
 * @Author:         lestin.yin
 * @CreateDate:     2019-08-09 17:28
 * @Version:        1.0
 */
class BaseUrlInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        //获取request
        val request = chain.request()
        //从request中获取原有的HttpUrl实例oldHttpUrl
        val oldHttpUrl = request.url()
        //获取request的创建者builder
        val builder = request.newBuilder()
        //从request中获取headers，通过给定的键url_name
        val headerValues = request.headers("urlname")
        if (headerValues != null && headerValues!!.size > 0) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader("urlname")
            //匹配获得新的BaseUrl
            val headerValue = headerValues!!.get(0)
            var newBaseUrl: HttpUrl? = null
            if ("user_service" == headerValue) {
                newBaseUrl = HttpUrl.parse(UriConstant.BASE_URL)
            } else {
                newBaseUrl = oldHttpUrl
            }
            //重建新的HttpUrl，修改需要修改的url部分
            val newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme("https")//更换网络协议
                    .host(newBaseUrl!!.host())//更换主机名
                    .port(newBaseUrl.port())//更换端口
//                    .removePathSegment(0)//移除第一个参数
                    .build()
            //重建这个request，通过builder.url(newFullUrl).build()；
            // 然后返回一个response至此结束修改
            LogUtil.e("Url", "intercept: " + newFullUrl.toString())
            return chain.proceed(builder.url(newFullUrl).build())
        }
        return chain.proceed(request)
    }
}