package com.lestin.yin.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.widget.NestedScrollView

/**
 *
 * @ProjectName:    Taurus
 * @Package:        com.lestin.yin.widget
 * @ClassName:      MyScrollView
 * @Description:     向下兼容
 * @Author:         lestin.yin
 * @CreateDate:     2019-08-15 09:56
 * @Version:        1.0
 */
class MyScrollView : NestedScrollView {
//    private var mListener: ScrollChangedListener? = null
//
//    constructor(context: Context) : super(context)
//
//    constructor (context: Context, attrs: AttributeSet) : super(context, attrs)
//
//    fun setScrollChangeListener(mListener: ScrollChangedListener) {
//        this.mListener = mListener
//    }
//
//    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
//        super.onScrollChanged(l, t, oldl, oldt)
//        if (mListener != null) {
//            mListener!!.onScrollChangedListener(l, t, oldl, oldt)
//        }
//    }
//
//    interface ScrollChangedListener {
//        fun onScrollChangedListener(x: Int, y: Int, oldX: Int, oldY: Int)
//    }


    private var isNeedScroll = true
    private var xDistance: Float = 0.toFloat()
    private var yDistance:Float = 0.toFloat()
    private var xLast:Float = 0.toFloat()
    private var yLast:Float = 0.toFloat()
    private var scaledTouchSlop: Int = 0

    constructor(context: Context): super(context, null)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        scaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                yDistance = 0f
                xDistance = yDistance
                xLast = ev.x
                yLast = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val curX = ev.x
                val curY = ev.y

                xDistance += Math.abs(curX - xLast)
                yDistance += Math.abs(curY - yLast)
                xLast = curX
                yLast = curY
                Log.e("SiberiaDante", "xDistance ：$xDistance---yDistance:$yDistance")
                return !(xDistance >= yDistance || yDistance < scaledTouchSlop) && isNeedScroll
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


    /*
    该方法用来处理NestedScrollView是否拦截滑动事件
     */
    fun setNeedScroll(isNeedScroll: Boolean) {
        this.isNeedScroll = isNeedScroll
    }
}