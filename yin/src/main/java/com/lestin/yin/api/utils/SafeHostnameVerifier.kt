package com.lestin.yin.api.utils

import com.lestin.yin.Constants
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 *
 * @ProjectName:    Taurus
 * @Package:        com.lestin.yin.api.utils
 * @ClassName:      SafeHostnameVerifier
 * @Description:     java类作用描述
 * @Author:         lestin.yin
 * @CreateDate:     2019-08-05 18:45
 * @Version:        1.0
 */
class SafeHostnameVerifier : HostnameVerifier {
    override fun verify(hostname: String?, p1: SSLSession?): Boolean {
        if (Constants.IP.equals(hostname)) {//校验hostname是否正确，如果正确则建立连接
            return true
        }
        return false
    }
}