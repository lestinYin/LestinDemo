import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;


import com.android.internal.telephony.ITelephony;

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

    private static ITelephony mITelephony;
    private static TelephonyManager manager;

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


                    TelephonyManager telephony = (TelephonyManager) context.getSystemService(
                            Context.TELEPHONY_SERVICE);
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    javaCallVs.runGoCloseVoice();
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
    void toEnd() {
        try {
            Method getITelephonyMethod = TelephonyManager.class
                    .getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
            mITelephony = (ITelephony) getITelephonyMethod.invoke(manager,
                    (Object[]) null);
            // 拒接来电
            mITelephony.endCall();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}

