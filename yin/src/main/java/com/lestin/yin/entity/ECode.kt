package com.lestin.yin.entity

import java.io.Serializable


/**
 * @name Pinche
 * @class name：com.xiaozhu.pinche.ui.login.entity
 * @class describe
 * @author lestin.yin yinmaolin8@gmail.com
 * @time 2018/7/23 下午5:56
 * @change
 * @chang time
 * @class describe
 */
data class ECode(val code: Int, val status : String, val content: Contents) : Serializable
    data class Contents(val token:String,val userInfo: UserInfo)
        data class UserInfo(val name: String,val phone: String,val likeCount: Int,val clockCount: Int,val shareCount: Int,val email: String): Serializable