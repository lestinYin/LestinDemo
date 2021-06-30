package com.lestin.yin.network.intercepter;

import android.content.Intent;

import com.google.gson.Gson;
import com.lestin.yin.MyApplication;
import com.lestin.yin.network.ApiManager;
import com.lestin.yin.network.entity.AuthBaseResult;
import com.lestin.yin.network.exceptions.DataInvalidException;
import com.lestin.yin.utils.LogUtil;
import com.lestin.yin.utils.ToastUtils;
import com.lestin.yin.utils.jsonUtils.SPManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Crm 的 access token 插入器
 */
public class ApiAuthnterceptor implements Interceptor {

    private Map<String, String> errorCodeDetail = new HashMap<String, String>() {{
        put("-2", "token失效，请重新登录");
    }};

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request requestWithKey = new Request.Builder()
                .headers(request.headers())
                .method(request.method(), request.body())
                .url(request.url())
                .build();
        Response response = chain.proceed(requestWithKey);
        return unWrapResponse(requestWithKey, response);
//        return chain.proceed(request);
    }

    /**
     * 解包请求结果，去掉最外层的状态码，错误转换为 Exception 抛出，交给最外层处理
     */
    private Response unWrapResponse(Request request, Response response) {
        try {
            String bodyString = response.body().string();


            AuthBaseResult data = new Gson().fromJson(bodyString, AuthBaseResult.class);


            // 重构代码时发现每次对结果 9 做了统一处理，这里已经包括错误代码字典里，所以不需要重复处理
            if (errorCodeDetail.keySet().contains(String.valueOf(data.state))) {
                LogUtil.e(errorCodeDetail.get(data.state) + "（" + data.state + "）");
//                throw new TokenInvalidException("校验失败，请重新登录");
                ToastUtils.showShort("登陆失效，请重新登录");
//                SPManager spManager = new SPManager(MyApplication.getContext());
//                spManager.clear(GlobalConfig.USER_INFO);
//                spManager.clear(GlobalConfig.TOKEN);
//                Intent intent = new Intent(App.getContext(), ALogin.class);
//                MyApplication.getContext().startActivity(intent);
            }


            return response.newBuilder()
                    .body(ResponseBody.create(ApiManager.JSON, bodyString))
                    .build();

        } catch (IOException e) {
            throw new DataInvalidException(response.toString());
        }


    }
}
