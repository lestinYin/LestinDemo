package com.lestin.yin

/**
 * @Description: gongyou
 * @Author: Lestin.Yin
 * @CreateDate: 2019/7/19 16:01
 * @UpdateUser: Lestin.Yin
 * @Version: 1.0
 */

class Constants private constructor() {

    companion object {
        val DEBUG = !BuildConfig.isReleaseEnv
        val retxtpwd = "^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{6,20}$"
        //用户信息
        val USER_INFO: String = "user_info"

        val TOKEN : String = "token"

        val IP : String = "103.193.190.118"

        val USER_EXIST_STATE : String = "199"
        val SUCCESS : String = "200"

        //图片相关
        val INTENT_TAKE_PIC_DICM : Int= 1
        val INTENT_TAKE_PIC_CAMEAR : Int= 2
        val INTENT_TAKE_PIC_SCALE : Int= 3
        val IS_NORMAL_PIC : String = "is_normal_pic"
        val RESULT_CODE_GET_PIC = 1


    }

}