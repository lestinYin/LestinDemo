package com.lestin.yin.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.lestin.yin.R
import com.lestin.yin.utils.ViewUtil
import kotlinx.android.synthetic.main.common_title_layout.view.*


/**
 * @Description: java类作用描述
 * @Author: Lestin.Yin
 * @CreateDate: 2019/7/23 14:55
 * @Version: 1.0
 */

class TitleBar : FrameLayout {

    var rlChBack: RelativeLayout? = null
    var ivBack: ImageView? = null
    var tvChTitle: TextView? = null
    var tvChRight: TextView? = null
    var ivChRight: ImageView? = null
    var header: RelativeLayout? = null

    var containerView: RelativeLayout? = null
        internal set

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val view = View.inflate(context, R.layout.common_title_layout, null)
        addView(view)
        val params = view.layoutParams
        params.height = ViewUtil.dp2px(50)
        layoutParams = params

        rlChBack = rl_ch_back
        tvChTitle = tv_ch_title
        ivBack = iv_back
        tvChRight = tv_ch_right
        ivChRight = iv_ch_right
        header = rl_general_header



    }

    fun setTitleText(text: CharSequence) {
        tvChTitle!!.text = text
    }

    fun setTitleTextColor(color: Int) {
        tvChTitle!!.setTextColor(color)
    }
    fun setBackgroundsColor(color: Int) {
        header!!.setBackgroundColor(color)
    }

    fun setTitleTextSize(size: Int) {
        tvChTitle!!.textSize = size.toFloat()
    }

    fun setTitleClickListener(listener: View.OnClickListener) {
        tvChTitle!!.setOnClickListener(listener)
    }

    fun setBackClickListener(listener: View.OnClickListener) {
        rlChBack!!.setOnClickListener(listener)
    }

    fun setRightClickListener(listener: View.OnClickListener) {
        ivChRight!!.setOnClickListener(listener)
        tvChRight!!.setOnClickListener(listener)
    }

    fun setRightText(text: CharSequence) {
        tvChRight!!.visibility = View.VISIBLE
        ivChRight!!.visibility = View.GONE
        tvChRight!!.text = text
    }

    fun setRightTextColor(color: Int) {
        tvChRight!!.setTextColor(color)
    }

    fun setRightImage(@DrawableRes resId: Int) {
        ivChRight!!.visibility = View.VISIBLE
        tvChRight!!.visibility = View.GONE
        ivChRight!!.setImageResource(resId)
    }

    fun setBackImage(@DrawableRes resId: Int) {
        rlChBack!!.visibility = View.VISIBLE
        //        tvChRight.setVisibility(GONE);
        ivBack!!.setImageResource(resId)
    }

    fun setBackIconVisible(isVisible: Boolean) {
        rlChBack!!.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}