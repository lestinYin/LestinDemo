package com.yin.lestin.lestindemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

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
                    .getSystemService(Service.TELEPHONY_SERVICE);
            switch (tManager.getCallState()) {

                case TelephonyManager.CALL_STATE_RINGING:
                    mIncomingNumber = intent.getStringExtra("incoming_number");
                    Log.i(TAG, "RINGING :" + mIncomingNumber);
                    Log.d(TAG, "**********************CALL_STATE_IDLE!!!!**********************");
//                    javaCallVs.runGoOpenVoice();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (mIncomingFlag) {
                        Log.i(TAG, "incoming ACCEPT :" + mIncomingNumber);
                    }
                    Log.d(TAG, "**********************CALL_STATE_OFFHOOK!!!!**********************");
//                    javaCallVs.runGoCloseVoice();
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (mIncomingFlag) {
                        Log.i(TAG, "incoming IDLE");
                    }
                    System.out.println("挂断");
                    Log.d(TAG, "**********************CALL_STATE_IDLE!!!!**********************");
//                    javaCallVs.runGoOpenVoice();
                    break;
            }
        }
    }

    public static void endCall(Context cx) { //挂断电话
        TelephonyManager telMag = (TelephonyManager) cx
                .getSystemService(Context.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        Method mthEndCall = null;
        try {
            mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
            mthEndCall.setAccessible(true);
            ITelephony iTel = (ITelephony) mthEndCall.invoke(telMag,
                    (Object[]) null);
            iTel.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean phoneIsInUse() {
        boolean phoneInUse = false;
        try {
            ITelephony phone = ITelephony.Stub.asInterface(ServiceManager.checkService("phone"));
            if (phone != null) phoneInUse = !phone.isIdle();
        } catch (RemoteException e) {
            Log.w(TAG, "phone.isIdle() failed", e);
        }
        return phoneInUse;
    }

}

