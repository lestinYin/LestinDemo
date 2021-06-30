package com.lestin.yin.api;



import com.google.gson.Gson;
import com.lestin.yin.Constants;
import com.lestin.yin.MyApplication;
import com.lestin.yin.net.AuthBaseResult;
import com.lestin.yin.net.XXTEA;
import com.lestin.yin.utils.ToastUtils;
import com.lestin.yin.utils.jsonUtils.SPManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Crm 的 access token 插入器
 */
public class ApiAuthnterceptor implements Interceptor {
    public static MediaType JSON = MediaType.parse("application/json");

    private Map<String, String> errorCodeDetail = new HashMap<String, String>() {{
        put("用户token失效", "token失效，请重新登录");
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
    private Response unWrapResponse(Request request, Response response) throws IOException {
        String bodyString = response.body().string();
        try {
            AuthBaseResult data = new Gson().fromJson(bodyString, AuthBaseResult.class);
            if (data.code == 400) {
//                throw new TokenInvalidException("校验失败，请重新登录");
                ToastUtils.showShort("登陆失效，请重新登录");
                SPManager spManager = new SPManager(MyApplication.getContext());
                spManager.clear("user_info");
                spManager.clear("token");
//                Intent intent = new Intent(MyApplication.getContext(), ALogin.class);
//                MyApplication.getContext().startActivity(intent);
            }
            if (!Constants.Companion.getDEBUG()) {
                String[] result = new String[]{"tag", "-1", ""};
                result[1] = String.valueOf(response.code());
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                InputStream is = response.body().byteStream();
                int len;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer, 0, 1024)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                result[2] = XXTEA.decryptToString(outStream.toByteArray(), "cHUgQenkAmS5169iZcRABw8n");
//            AuthBaseResult data = new Gson().fromJson(result[2], AuthBaseResult.class);
                // 重构代码时发现每次对结果 9 做了统一处理，这里已经包括错误代码字典里，所以不需要重复处理
//            if (errorCodeDetail.keySet().contains(String.valueOf(data.error))) {
                return response.newBuilder()
                        .body(ResponseBody.create(JSON, result[2]))
                        .build();
            }
        } catch (Exception e) {
//            throw new Exception();

        }
    return response.newBuilder()
                .body(ResponseBody.create(JSON, bodyString))
                .build();

    }
}
