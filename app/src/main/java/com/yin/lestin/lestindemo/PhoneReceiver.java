package com.yin.lestin.lestindemo;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.ContactsContract;
import android.telecom.Call;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;


import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;


import java.lang.reflect.Method;

import static android.content.Context.TELEPHONY_SERVICE;


/**
 * @ProjectName: LestinDemo
 * @Package: com.yin.lestin.lestindemo
 * @ClassName: PhoneReceiver
 * @Description: java类作用描述
 * @Author: lestin.yin
 * @CreateDate: 2020-07-06 11:26
 * @Version: 1.0
 */
public class PhoneReceiver extends BroadcastReceiver {
    private static final String TAG = "message";
    private static boolean mIncomingFlag = false;
    private static String mIncomingNumber = null;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            // 如果是拨打电话
            mIncomingFlag = false;
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i(TAG, "call OUT:" + phoneNumber);

        } else {
            // 如果是来电
            TelephonyManager tManager = (TelephonyManager) context
                    .getSystemService(TELEPHONY_SERVICE);
            switch (tManager.getCallState()) {

                case TelephonyManager.CALL_STATE_RINGING:
                    mIncomingNumber = intent.getStringExtra("incoming_number");
                    Log.i(TAG, "RINGING :" + mIncomingNumber);
                    Log.d(TAG, "**********************CALL_STATE_RINGING!!!!**********************");
//                    javaCallVs.runGoOpenVoice();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (mIncomingFlag) {
                        Log.i(TAG, "incoming ACCEPT :" + mIncomingNumber);
                    }
                    Log.e(TAG, "**********************CALL_STATE_OFFHOOK!!!!**********************");
//                    javaCallVs.runGoCloseVoice();
                    endCallReJection(context);

                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (mIncomingFlag) {
                        Log.i(TAG, "incoming IDLE");
                    }
                    System.out.println("挂断");
                    Log.e(TAG, "**********************CALL_STATE_IDLE!!!!**********************");
//                    javaCallVs.runGoOpenVoice();
                    break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void endCall(Context paramContext) {

        TelecomManager systemServices= (TelecomManager) paramContext.getSystemService(Context.TELECOM_SERVICE);


        try {
            if (Build.VERSION.SDK_INT >= 21) {
                TelecomManager telecom = (TelecomManager) paramContext.getSystemService(Context.TELECOM_SERVICE);
                if (ActivityCompat.checkSelfPermission(paramContext, Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                systemServices.acceptRingingCall();
                systemServices.endCall();
                ((TelecomManager) telecom).endCall();
            }
            TelephonyManager systemService = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (systemService == null) {
                return;
            }
            Method method = systemService.getClass().getDeclaredMethod("getITelephony", new Class[0]);
            method.setAccessible(true);
            Object invoke = method.invoke(paramContext, new Object[0]);
            if (invoke == null) {
                return;
            }
            Method localObject = paramContext.getClass().getMethod("endCall", new Class[0]);
            ((Method) localObject).setAccessible(true);
            ((Method) localObject).invoke(paramContext, new Object[0]);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void endCallReJection(Context context) {
        // 延迟5秒后自动挂断电话
        // 首先拿到TelephonyManager
        try {
            TelephonyManager telMag = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class<TelephonyManager> c = TelephonyManager.class;

            // 再去反射TelephonyManager里面的私有方法 getITelephony 得到 ITelephony对象
            Method mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
            //允许访问私有方法
            mthEndCall.setAccessible(true);
            final Object obj = mthEndCall.invoke(telMag, (Object[]) null);

            // 再通过ITelephony对象去反射里面的endCall方法，挂断电话
            Method mt = obj.getClass().getMethod("endCall");
            //允许访问私有方法
            mt.setAccessible(true);
            mt.invoke(obj);
        }catch (Exception e) {
            Log.e("----",e.toString());

        }

    }



}

