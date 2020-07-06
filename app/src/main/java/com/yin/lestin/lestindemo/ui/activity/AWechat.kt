package com.yin.lestin.lestindemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yin.lestin.lestindemo.bean.WechatBean
import com.yin.lestin.lestindemo.utils.image.ShowImage
import kotlinx.android.synthetic.main.activity_awechat.*
import com.yin.lestin.lestindemo.R


class AWechat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_awechat)

        val foodAdapter = FoodAdapter(wechatList())
        rv_wechat.adapter = foodAdapter


        foodAdapter.setOnItemClickListener { _, _, position ->
            val weChatBean = wechatList()[position].url
            val intent = Intent(this, AWebView::class.java)
            intent.putExtra("url",weChatBean)
            startActivity( intent)
        }


//        val animator = ValueAnimator.ofInt(-0x100, -0xffff01)
//        animator.setEvaluator(ArgbEvaluator())
//        animator.duration = 3000
//        animator.addUpdateListener { animation ->
//            val curValue = animation.animatedValue as Int
//            tv_content.setBackgroundColor(curValue)
//        }
//        animator.start()

    }

    //左侧列表
    class FoodAdapter(data: List<WechatBean>) : BaseQuickAdapter<WechatBean, BaseViewHolder>(data) {
        override fun convert(helper: BaseViewHolder, item: WechatBean?) {
            val view = helper.getView<ImageView>(R.id.iv_pic)
            ShowImage.show(mContext, item?.pic,view)
            helper.setText(R.id.tv_title,item?.title)
            helper.setText(R.id.tv_description,item?.description)


        }

        init {
            mLayoutResId = R.layout.item_wechat
        }

    }
    private fun wechatList(): List<WechatBean> {
        return listOf(
                WechatBean("Android开发中文站","这里有资讯，干货，技术，源码，精彩内容不容错过！","http://wx.qlogo.cn/mmhead/Q3auHgzwzM52ZpI3ufk4ibhxib4fvE55Hpz9YAy7n5ib2ibMic9SEtSqxNw/0","https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzA4NDM2MjAwNw==&scene=124&uin=NTg1MzQzMTYw&key=4f19dee08d168950b49abbf28d35d3af5b5d6ab81947f5e6b341993001b57a598b92f8fdb6a45616926284e52994a42cf62291009dda6268111d53829021ff023ce30627ab8ac8a971861e7ce9a9a4d4&devicetype=iMac+MacBookPro15%2C2+OSX+OSX+10.14.5+build(18F132)&version=12031a11&lang=zh_CN&nettype=WIFI&a8scene=0&fontScale=100&pass_ticket=mrWhlZVRnt%2FocJvcU40zCIzks9qNFBjKdAaubNCNuF2PB0mhdW2%2Ff8l3H6k0gp3d")
        )
    }
}
