package com.lestin.yin.network.intercepter;

import android.text.TextUtils;

import com.lestin.yin.MyApplication;
import com.lestin.yin.utils.jsonUtils.SPManager;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author lestin.yin yinmaolin8@gmail.com
 * @name PreDiagnosis
 * @class name：com.doctorai.prediagnosis.network.intercepter
 * @class describe
 * @time 2018/1/6 下午3:55
 * @change
 * @chang time
 * @class describe 添加公共请求参数
 */

public class IntercepterCommonRequest implements Interceptor {
    private SPManager spManager;

    @Override
    public Response intercept(Chain chain) throws IOException {
        spManager = new SPManager(MyApplication.getContext());
//        EUser eUser = spManager.get(GlobalConfig.USER_INFO, EUser.class);
//        String token = "";
//        if (eUser != null) {
//            token = eUser.getData().getSessionToken().getToken();
//        }
        String tokenDD = spManager.get(com.lestin.yin.Constants.Companion.getTOKEN());
        if (TextUtils.isEmpty(tokenDD)) {
            return chain.proceed(chain.request());
        } else {
            tokenDD = tokenDD.substring(1,tokenDD.length()-1);
        }
        Request oldRequest = chain.request();
        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("authentication",tokenDD)
        ;


        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();
        return chain.proceed(newRequest);
//
//        String url = chain.request().url().toString();
//        String host = Uri.parse(url).getHost();
//        Request.Builder builder = chain.request().newBuilder();
//        Set<String> preferences = CookieStore.getCookies(host);
//        for (String cookie : preferences) {
//            builder.addHeader("Cookie", cookie);
//        }
//        return chain.proceed(builder.build());
    }
}
