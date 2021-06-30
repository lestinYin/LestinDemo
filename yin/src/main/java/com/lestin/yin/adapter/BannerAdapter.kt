package com.lestin.yin.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.jude.rollviewpager.RollPagerView
import com.jude.rollviewpager.adapter.LoopPagerAdapter
import com.lestin.yin.R
import com.lestin.yin.utils.image.ShowImage

/**
 *
 * @ProjectName:    Taurus
 * @Package:        com.jinniu.delivery.adapter
 * @ClassName:      BannerAdapter
 * @Description:     轮播图
 * @Author:         lestin.yin
 * @CreateDate:     2019-08-28 18:35
 * @Version:        1.0
 */
class BannerAdapter : LoopPagerAdapter {
    private val mContext: Context
    private val imgList: List<String>

    constructor(viewPager: RollPagerView, context: Context, imgList: List<String>) :  super(viewPager){
        this.mContext = context
        this.imgList = imgList
    }

    override fun getView(container: ViewGroup?, position: Int): View {
        //不能直接创建ImageView 必须要有superview 不然glide会报   You must not call setTag() on a view Glide is targeting
        val layout = LinearLayout(container!!.context)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layout.layoutParams = layoutParams
        val view = ImageView(mContext)
        view.layoutParams = layoutParams
        val imgUrl = imgList[position]

        if (TextUtils.isEmpty(imgUrl)) {
            view.setImageResource(R.mipmap.delivery_home_banner)
        } else {
            ShowImage.show(mContext, imgUrl, view)
        }
        if (!TextUtils.isEmpty(imgUrl)) {
            //            view.setOnClickListener(v -> ToastUtils.showShort(mContext,imgUrl));
        }
        layout.addView(view)
        return layout
    }

    override fun getRealCount(): Int = imgList.size
}