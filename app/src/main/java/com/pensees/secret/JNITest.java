package com.pensees.secret;

/**
 * @ProjectName: testjni
 * @Package: com.lestin.testjni
 * @ClassName: JNITest
 * @Description: java类作用描述
 * @Author: lestin.yin
 * @CreateDate: 2020-07-01 15:16
 * @Version: 1.0
 */
public class JNITest {
    public native static String getStrFromJNI();

    public native static int secreateIsOk();


    static {
        System.loadLibrary("secret");
    }

}
