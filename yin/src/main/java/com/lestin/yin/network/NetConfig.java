package com.lestin.yin.network;


import com.lestin.yin.Constants;

/**
 * autour: lestin.yin
 * email:lestin.yin@gmail.com
 * date: 2017/6/6 15:50
 * version:
 * description:
 */

public class NetConfig {
//    private static final String debugAddress = "http://192.168.0.199:8111/app/"; //cc
//    private static final String debugAddress = "http://192.168.0.200:8111/app/"; //
    private static final String debugAddress = "http://39.104.71.160:8111/app/"; //cc
    private static final String releaseAddress = "http://39.104.71.160:8111/app/"; //外网
    public static String baseAddress = Constants.Companion.getDEBUG() ? debugAddress : releaseAddress;
    public static String imgUrl = baseAddress + "selectUserImg?filePath=";
}
