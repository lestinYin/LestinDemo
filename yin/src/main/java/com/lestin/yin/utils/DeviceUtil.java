package com.lestin.yin.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings.Secure;

import java.util.Locale;

public class DeviceUtil {

    public static String getNetType(Context context) {
        try {
            ConnectivityManager conn = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conn != null) {
                NetworkInfo info = conn.getActiveNetworkInfo();
                if (info != null) {
                    String type = info.getTypeName().toLowerCase(
                            Locale.getDefault());
                    if (type.equals("wifi")) {
                        return type;
                    } else if (type.equals("mobile")) {
                        String apn = info.getExtraInfo().toLowerCase(
                                Locale.getDefault());
                        if (apn.equals("cmwap") || apn.equals("3gwap") || apn.equals("uniwap") || apn
                                .equals("ctwap")) {
                            return "wap";
                        } else {
                            return apn;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    /**
     * 描述：判断网络是否有效.
     *
     * @param context the context
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    public static String getSysVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getDeviceName() {
        return android.os.Build.MODEL;
    }

    @SuppressLint("MissingPermission")
    public static void startCall(Context context, String uri) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + uri));
        context.startActivity(intent);
    }
}