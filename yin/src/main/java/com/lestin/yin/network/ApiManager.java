package com.lestin.yin.network;


import androidx.annotation.NonNull;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lestin.yin.MyApplication;
import com.lestin.yin.network.intercepter.ApiAuthnterceptor;
import com.lestin.yin.network.intercepter.IntercepterCommonRequest;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * autour: lestin.yin
 * email:lestin.yin@gmail.com
 * date: 2017/6/6 15:18
 * version:
 * description:
 */

public class ApiManager {
    public static MediaType JSON = MediaType.parse("application/json");

    private static Retrofit.Builder sBuilder;

    private static BaseApi baseApi;

    static {
        OkHttpClient client = buildOkHttpClient();
        sBuilder = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    @NonNull
    private static OkHttpClient buildOkHttpClient() {
        ClearableCookieJar cookieJar = new PersistentCookieJar(
                new SetCookieCache(),
                new SharedPrefsCookiePersistor(MyApplication.getContext()));
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .readTimeout(6, TimeUnit.SECONDS) // 超时时间
                .writeTimeout(6, TimeUnit.SECONDS) // 超时时间
                .connectTimeout(6, TimeUnit.SECONDS)
                .addInterceptor(new IntercepterCommonRequest())
                .addInterceptor(new ApiAuthnterceptor())
                ; // 超时时间


        if (com.lestin.yin.Constants.Companion.getDEBUG()) {
//            builder.addNetworkInterceptor(new StethoInterceptor()); // chrome调试
        }
        return builder.build();
    }



    public static BaseApi getBaseApi() {
        if (baseApi == null) {
            baseApi = sBuilder.baseUrl(NetConfig.baseAddress).build().create(BaseApi.class);
        }
        return baseApi;
    }
}
