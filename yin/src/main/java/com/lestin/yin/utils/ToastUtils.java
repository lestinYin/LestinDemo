package com.lestin.yin.utils;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.lestin.yin.MyApplication;


/**
 * Author: lcf
 * Description: Toast工具类
 * Since: 1.0
 * Date: 2017/5/26 11:01
 */
public class ToastUtils {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Toast mToast = null;
    private static Context context;

    private ToastUtils(Context context) {
        this.context = context;
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     *
     * @param context Context
     * @param message 消息
     */
    public static void showShort(final Context context, final String message) {
        showMessage(context, message, Toast.LENGTH_SHORT);
    }

    public static void showShort(final String message) {
        showMessage(MyApplication.getContext(),message, Toast.LENGTH_SHORT);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     *
     * @param context Context
     * @param message 消息
     */
    public static void showLong(final Context context, final String message) {
        showMessage(context, message, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     *
     * @param context   Context
     * @param messageId 消息文本资源ID
     */
    public static void showShort(final Context context, final int messageId) {
        showMessage(context, messageId, Toast.LENGTH_SHORT);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     *
     * @param context   Context
     * @param messageId 消息文本资源ID
     */
    public static void showLong(final Context context, final int messageId) {
        showMessage(context, messageId, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息
     *
     * @param context   Context
     * @param messageId 消息文本资源ID
     * @param duration  持续时间
     */
    private static void showMessage(final Context context, final int messageId, final int duration) {
        showMessage(context, context.getString(messageId), duration);
    }

    /**
     * Toast发送消息
     *
     * @param context  Context
     * @param message  消息
     * @param duration 持续时间
     */
    private static void showMessage(final Context context, final String message, final int duration) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mToast != null) {
                    mToast.setText(message);
                    mToast.setDuration(duration);
                } else {
                    mToast = Toast.makeText(context.getApplicationContext(), message, duration);
                }
                mToast.show();
            }
        });
    }

    /**
     * 关闭当前Toast
     */
    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
