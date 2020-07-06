package com.yin.lestin.lestindemo;

import android.content.Context;

/**
 * @ProjectName: LestinDemo
 * @Package: com.yin.lestin.lestindemo
 * @ClassName: ITelephony
 * @Description: java类作用描述
 * @Author: lestin.yin
 * @CreateDate: 2020-07-06 13:36
 * @Version: 1.0
 */
public class ITelephony {

    /**
     * @hide
     */
    private ITelephony getITelephony() {
        return ITelephony.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE));
    }
}
