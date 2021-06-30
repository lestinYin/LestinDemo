package com.lestin.yin.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.future.taurus.widget.ScrollViewListener
import com.lestin.yin.R


/**
 * @ProjectName:
 * @Package:
 * @ClassName:
 * @Description: java类作用描述
 * @Author: Lestin.Yin
 * @CreateDate: 2019/7/23 16:02
 * @Version: 1.0
 */
class ViewUtil private constructor() {

    companion object{
        val screenWidth: Int
        get() = Resources.getSystem().displayMetrics.widthPixels

        val screenHeight: Int
        get() = Resources.getSystem().displayMetrics.heightPixels

        /**
         * 设置标题栏随滚动渐变透明
         *
         * @param scrollContent   滚动的载体布局
         * @param headerView      所要控制透明的 View
         * @param heightReference 所要参考高度的 View
         * @param defaultColor    不透明下标题栏的颜色
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        fun setupTransHeader(scrollContent: ScrollViewListener.HeightObserver, headerView: View, heightReference: View, defaultColor: Int) {
            headerView.setBackgroundResource(defaultColor)
            val drawable = headerView.background
            drawable.mutate()
            headerView.setBackgroundResource(R.color.transparent)
            scrollContent.addScrollHeightListener({ scrollY ->
                val maxScroll = heightReference.height - headerView.height
                var percentage = Math.abs(scrollY as Int) / maxScroll.toFloat() * 255
                if (percentage > 255) {
                    percentage = 255f
                }
                drawable.alpha = percentage.toInt()
                headerView.background = drawable
            })
        }

        /**
         * 设置标题栏随滚动渐变透明
         *
         * @param scrollContent   滚动的载体布局
         * @param headerView      所要控制透明的 View
         * @param heightReference 所要参考高度的 View
         */
        fun setupTransHeader(scrollContent: ScrollViewListener.HeightObserver, headerView: View, heightReference: View) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setupTransHeader(scrollContent, headerView, heightReference, R.color.normal_color)
            }
        }

        /**
         * dp to px
         */
        fun dp2px(dp: Int): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }

        /**
         * px to dp
         */
        fun px2dp(px: Float): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (px / scale + 0.5f).toInt()
        }

        /**
         * sp转px
         *
         * @param spValue sp值
         * @return px值
         */
        fun sp2px(spValue: Float): Int {
            val fontScale = Resources.getSystem().displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * px转sp
         *
         * @param pxValue px值
         * @return sp值
         */
        fun px2sp(pxValue: Float): Int {
            val fontScale = Resources.getSystem().displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        fun getStatusBarHeight(anchor: Activity): Int {
            val rectangle = Rect()
            val window = anchor.window
            window.decorView.getWindowVisibleDisplayFrame(rectangle)
            return rectangle.top
        }

        fun getStatusBarHeightPx(context: Context): Int {
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            return context.resources.getDimensionPixelSize(resourceId)
        }


        @JvmOverloads
        fun viewToBitmap(v: View, scaleWidth: Float = 1f, scaleHeight: Float = 1f): Bitmap {
            if (v.measuredHeight <= 0) {
                v.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                val b = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
                val c = Canvas(b)
                v.layout(0, 0, (v.measuredWidth * scaleWidth).toInt(), (v.measuredHeight * scaleHeight).toInt())
                v.draw(c)
                return b
            }
            val b = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            v.layout(v.left, v.top, v.right, v.bottom)
            v.draw(c)
            return b
        }

        /**
         * 获取屏幕的高度
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val manager = context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = manager.defaultDisplay
            return display.height
        }
    }
}