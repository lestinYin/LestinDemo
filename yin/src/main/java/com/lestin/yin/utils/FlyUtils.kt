package com.lestin.yin.utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.LinearLayout
import android.widget.TextView

/**
 *
 * @ProjectName:    Taurus
 * @Package:        com.lestin.yin.utils
 * @ClassName:      FlyUtils
 * @Description:     购物车飞入动画
 * @Author:         lestin.yin
 * @CreateDate:     2019-08-26 16:54
 * @Version:        1.0
 */
object FlyUtils {
    /**
     *
     */
    fun setAnim(context : Activity, v: View, startLocation: IntArray,re_zhongcai_tanchu:TextView) {


        var anim_mask_layout : ViewGroup? = null
        anim_mask_layout = createAnimLayout(context) //创建动画层
        anim_mask_layout.addView(v)//把动画小球添加到动画层
        val view = addViewToAnimLayout(anim_mask_layout, v,
                startLocation)
        val endLocation = IntArray(2)// 存储动画结束位置的X、Y坐标
        re_zhongcai_tanchu.getLocationInWindow(endLocation)// re_zhongcai_tanchu是那个抛物线最后掉落的控件

        // 计算位移
//        val endX = 0 - startLocation[0] + 40// 动画位移的X坐标
//        val endY = endLocation[1] - startLocation[1]// 动画位移的y坐标

         val endX = endLocation[0] - startLocation[0] // 动画位移的X坐标
        val endY = endLocation[1] - startLocation[1]   // 动画位移的y坐标

        val translateAnimationX = TranslateAnimation(0f,
                endX.toFloat(), 0f, 0f)
        translateAnimationX.interpolator = LinearInterpolator()
        translateAnimationX.repeatCount = 0// 动画重复执行的次数
        translateAnimationX.fillAfter = true

        val translateAnimationY = TranslateAnimation(0f, 0f,
                0f, endY.toFloat())
        translateAnimationY.interpolator = AccelerateInterpolator()
        translateAnimationY.repeatCount = 0// 动画重复执行的次数
        translateAnimationX.fillAfter = true

        val set = AnimationSet(false)
        set.fillAfter = false
        set.addAnimation(translateAnimationY)
        set.addAnimation(translateAnimationX)
        set.duration = 300// 动画的执行时间
        view.startAnimation(set)
        // 动画监听事件
        set.setAnimationListener(object : Animation.AnimationListener {
            // 动画的开始
            override fun onAnimationStart(animation: Animation) {
                v.visibility = View.VISIBLE
                //    Log.e("动画","asdasdasdasd");
            }

            override fun onAnimationRepeat(animation: Animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            override fun onAnimationEnd(animation: Animation) {
                v.visibility = View.GONE
                set.cancel()
                animation.cancel()
            }
        })

    }

    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private fun createAnimLayout(context : Activity): ViewGroup {
        val rootView = context.getWindow().getDecorView() as ViewGroup
        val animLayout = LinearLayout(context)
        val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        animLayout.layoutParams = lp
        animLayout.id = Integer.MAX_VALUE
        animLayout.setBackgroundResource(android.R.color.transparent)
        rootView.addView(animLayout)
        return animLayout
    }


    private fun addViewToAnimLayout(parent: ViewGroup, view: View,
                                    location: IntArray): View {
        val x = location[0]
        val y = location[1]
        val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.leftMargin = x
        lp.topMargin = y
        view.layoutParams = lp
        return view
    }

}