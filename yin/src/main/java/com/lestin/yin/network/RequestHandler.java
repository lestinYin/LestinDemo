package com.lestin.yin.network;

import android.content.Intent;

import com.lestin.yin.network.exceptions.DataInvalidException;
import com.lestin.yin.network.exceptions.ServerException;
import com.lestin.yin.network.exceptions.TokenInvalidException;
import com.lestin.yin.utils.ToastUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

/**
 * 作者：Lestin.yin on 2017/6/6 16:22
 * 邮箱：lestin.yin@gmail.com
 * Description:全局网络请求处理
 */

public class RequestHandler {
    /**
     * 网络错误处理
     */
    private static ErrorHandler<IOException> networkHandler =
            ErrorHandler.create(IOException.class, throwable -> {
                ToastUtils.showShort("网络异常，请稍后再试");
                // TODO: 2017/3/17 发送网络错误广播便于统一封装网络错误界面
            });

    /**
     * 服务器错误处理
     */
    private static ErrorHandler<ServerException> serverHandler =
            ErrorHandler.create(ServerException.class, throwable ->
                    ToastUtils.showShort("服务器错误:" + throwable.status));

    /**
     * 数据格式错误
     */
    private static ErrorHandler<DataFormatException> dataErrorHandler =
            ErrorHandler.create(DataFormatException.class, throwable -> {
                ToastUtils.showShort("数据格式错误");
//                L.e(throwable, "数据格式错误");
            });

    private static ErrorHandler<IllegalArgumentException> dataParseErrorHandler =
            ErrorHandler.create(IllegalArgumentException.class, throwable -> {
                ToastUtils.showShort("数据解析失败");
//                L.e(throwable, "数据解析失败");
            });

    private static ErrorHandler<DataInvalidException> logicErrorHandler =
            ErrorHandler.create(DataInvalidException.class, throwable -> {
                ToastUtils.showShort(throwable.getMessage());
//                Log.e(throwable, throwable.getMessage());
            });

    /**
     * Token 登录状态失效处理
     */
    private static ErrorHandler<TokenInvalidException> tokenInvalidHandler =
            ErrorHandler.create(TokenInvalidException.class, throwable -> {
                // token 失效拦截器
//                ToastUtils.showShort(throwable.getMessage());
//                Intent intent = new Intent(App.getContext(), ALogin.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                App.getContext().startActivity(intent);
            });

    /**
     * 默认的拦截器，如果需要替换可以使用 {@link RequestHandler#handleError(Throwable, ErrorHandler[])}
     * <p>
     * 传入需要替换的 ErrorHandler，在 {@link ErrorHandler} 构造方法中传入需要替换的异常 Class 和拦截操作
     */
    private static Map<Class<? extends Throwable>, ErrorHandler> mDefaultHandlers =
            new HashMap<Class<? extends Throwable>, ErrorHandler>() {{
                // 默认拦截器配置
                put(ServerException.class, serverHandler);
                put(IOException.class, networkHandler);
                put(DataFormatException.class, dataErrorHandler);
                put(IllegalArgumentException.class, dataParseErrorHandler);
                put(TokenInvalidException.class, tokenInvalidHandler);
                put(DataInvalidException.class, logicErrorHandler);
            }};

    /**
     * 默认的错误处理方法
     *
     * @param throwable 需要处理的错误
     * @return 是否被处理
     */
    public static boolean handleError(Throwable throwable) {
        return realHandlerError(throwable, mDefaultHandlers);
    }

    /**
     * 自定义替换错误处理器的方法
     *
     * @param throwable      需要处理的错误
     * @param customHandlers 需要自定义的错误处理方法，在 {@link ErrorHandler} 构造方法中传入需要替换的
     *                       异常 Class 和拦截操作
     * @return 是否被处理
     */
    public static boolean handleError(Throwable throwable, ErrorHandler... customHandlers) {
        Map<Class<? extends Throwable>, ErrorHandler> tempHandlers = new HashMap<>(mDefaultHandlers);
        for (ErrorHandler customHandler : customHandlers) {
            //noinspection unchecked
            tempHandlers.put(customHandler.getErrorClass(), customHandler);
        }
        return realHandlerError(throwable, tempHandlers);
    }

    /**
     * 所有的错误处理的出口方法
     *
     * @param throwable 需要处理的错误
     * @param handlers  处理 handler 集合
     * @return 是否被处理
     */
    private static boolean realHandlerError(Throwable throwable,
                                            Map<Class<? extends Throwable>, ErrorHandler> handlers) {

        for (ErrorHandler mather : handlers.values()) {
            if (mather.handle(throwable)) {
                return true;
            }
        }

//        LogUtil.e(throwable);
        return false;
    }


}
