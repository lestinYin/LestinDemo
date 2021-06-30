package com.lestin.yin.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.NumberFormat
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AccelerateInterpolator
import android.view.animation.TranslateAnimation
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout






/**
 * @ProjectName:
 * @Package:
 * @ClassName:
 * @Description: 工具类
 * @Author: Lestin.Yin
 * @CreateDate: 2019/7/23 11:37
 * @Version: 1.0
 */

object Utils {
    /**
     * 测量View的宽高
     *
     * @param view View
     */
    fun measureWidthAndHeight(view: View) {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)
    }

    /**
     * //     *
     * //     * @param psdMD5要加密的对象
     *
     * @returnMD5加密后市返回一个32位数的字符串，返回“”，代表加密异常
     */
    fun md5Code(psd: String): String {
        var psd = psd
        try {
            // 加盐
            psd = psd + "abc"
            // 1，获取加密算法对象，单利设计模式
            val instance = MessageDigest.getInstance("MD5")
            // 2，通过加密算法操作，对psd进行哈希加密操作
            val digest = instance.digest(psd.toByteArray())
            val sb = StringBuffer()
            // 循环16次
            for (b in digest) {
                // 获取b的后8位
                val i = b
                // 将10进制数，转化为16进制
                var hexString = Integer.toHexString(i.toInt())
                // 容错处理，长度小于2的，自动补0
                if (hexString.length < 2) {
                    hexString = "0" + hexString
                }
                // 把生成的32位字符串添加到stringBuffer中
                sb.append(hexString)
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    fun doubleToString(d: Double?): String {
        val num = NumberFormat.getPercentInstance()
        return num.format(d).replace("%", "")
    }

    fun doubleToInt(d: Double?): Int {
        val num = NumberFormat.getPercentInstance()
        return Integer.valueOf(num.format(d).replace("%", ""))!!
    }

    fun stringToInt(d: String): Int {
        val num = NumberFormat.getPercentInstance()
        return Integer.valueOf(num.format(java.lang.Double.valueOf(d)).replace("%", ""))!!
    }

    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param isDp   需要设置的数值是否为DP
     * @param left   左边距
     * @param right  右边距
     * @param top    上边距
     * @param bottom 下边距
     * @return
     */
    fun setViewMargin(view: View?, isDp: Boolean, left: Int, right: Int, top: Int, bottom: Int): ViewGroup.LayoutParams? {
        if (view == null) {
            return null
        }

        var leftPx = left
        var rightPx = right
        var topPx = top
        var bottomPx = bottom
        val params = view.layoutParams
        var marginParams: ViewGroup.MarginLayoutParams?
        //获取view的margin设置参数
        if (params is ViewGroup.MarginLayoutParams) {
            marginParams = params
        } else {
            //不存在时创建一个新的参数
            marginParams = ViewGroup.MarginLayoutParams(params)
        }

        //根据DP与PX转换计算值
        if (isDp) {
            leftPx = ViewUtil.dp2px(left)
            rightPx = ViewUtil.dp2px(right)
            topPx = ViewUtil.dp2px(top)
            bottomPx = ViewUtil.dp2px(bottom)
        }
        //设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx)
        view.layoutParams = marginParams
        return marginParams
    }

    /**
     * 防止多次点击
     */
    private var lastClickTime: Long = 0

    fun isFastDoubleClick(): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        if (0 < timeD && timeD < 500) {
            return true
        }
        lastClickTime = time
        return false
    }

    fun cryptMD5(str: String?): String {
        val digester: MessageDigest
        try {
            //if (str == null || str.length() == 0) throw new IllegalArgumentException("String to encript cannot be null or zero length");
            if (str == null || str.length == 0) return ""
            digester = MessageDigest.getInstance("MD5")
            digester.update(str.toByteArray())
            val hash = digester.digest()
            val hexString = StringBuffer()
            for (i in hash.indices) {
                if (0xff  shl(hash[i].toInt()) < 0x10) {
                    hexString.append("0" + Integer.toHexString(0xFF shl(hash[i].toInt())))
                } else {
                    hexString.append(Integer.toHexString(0xFF shl (hash[i].toInt())))
                }
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }



}