package com.lestin.yin.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager


/**
 *
 * @ProjectName:    Taurus
 * @Package:        com.lestin.yin.widget
 * @ClassName:      MyViewPager
 * @Description:     解决Viewpager高度问题
 * @Author:         lestin.yin
 * @CreateDate:     2019-08-15 15:33
 * @Version:        1.0
 */
class MyViewPager : ViewPager {
    private var current: Int = 0
    private var heights: Int = 0
    /**
     * 保存position与对于的View
     */
    private val childrenViews = LinkedHashMap<Int,View>()

    private var isCanScroll = false

    constructor (context: Context?): super(context!!, null){}

    constructor (context: Context, attrs: AttributeSet): super(context, attrs)


    fun setScanScroll(isCanScroll: Boolean) {
        this.isCanScroll = isCanScroll
    }//切换tab的时候重新设置viewpager的高度

    fun resetHeight(current: Int) {
        this.current = current
        if (childrenViews.size > current) {
            var layoutParams: LinearLayout.LayoutParams? = layoutParams as LinearLayout.LayoutParams
            if (layoutParams == null) {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
            } else {
                layoutParams.height = heights
            }
            setLayoutParams(layoutParams)
        }
    }

    /**
     * 保存position与对于的View
     */
    fun setObjectForPosition(view: View, position: Int) {
        childrenViews.put(position, view)
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        try {
            if (isCanScroll) {
                return super.onTouchEvent(arg0)
            }
        } catch (e: Exception) {
        }

        return false

    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        try {
            if (isCanScroll) {
                return super.onInterceptTouchEvent(arg0)
            }
        } catch (e: Exception) {

        }

        return false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (childrenViews.size > current) {
            val child = childrenViews.get(current)
            child!!.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            heights = child.getMeasuredHeight()
        }
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heights, View.MeasureSpec.EXACTLY)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

}