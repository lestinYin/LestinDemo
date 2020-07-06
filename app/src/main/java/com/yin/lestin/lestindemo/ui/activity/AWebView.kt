package com.yin.lestin.lestindemo.ui.activity

import android.os.Bundle
import android.webkit.WebSettings
import kotlinx.android.synthetic.main.activity_aweb_view.*
import android.webkit.WebView
import android.webkit.WebChromeClient
import android.os.Build
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.view.ViewTreeObserver
import android.widget.TextView
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.yin.lestin.lestindemo.R


class AWebView : AppCompatActivity() {
    var isExpandDescripe = false// 初始展开状态为false，即未展开；
    var content = "开发了睡觉啊发来的都看了撒娇开发了睡觉啊发来的都看了撒娇开发看了撒娇开发开发了睡觉啊发来的都看了撒娇开发了睡觉啊发来的都看了撒娇开发看了撒娇开发开发了睡觉啊发来的都看了撒娇开发了睡觉啊发来的都看了撒娇开发看了撒娇开发开发了睡觉啊发来的都看了撒娇开发了睡觉啊发来的都看了撒娇开发看了撒娇开发"
    var url : String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aweb_view)

        var webSettings = webview.settings

        webSettings.javaScriptEnabled = true //设置能够解析Javascript

        webSettings.domStorageEnabled = true//设置适应Html5 重点是这个设置

        webSettings.setBlockNetworkImage(false)//解决图片不显示
        //不使用缓存：
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE)

        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        webview.setWebChromeClient(WebChromeClient())
        webview.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                System.out.println("$newProgress jkk")
            }

            override fun onReceivedTitle(view: WebView, title: String) {

            }
        })

        url = intent.getStringExtra("url")
//        webview.loadUrl("https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzA5MzI3NjE2MA==&scene=124&uin=NTg1MzQzMTYw&key=45db8f9509c464a5082d6e749409ced61d32632d95030a9ab9875c426ecf324a53ae187d16487960e72745c0fa8b04f46b917b34b045c8be7f66a89b8dd6a5d993d2629290e96f2ddb5ef3a23f585d03&lang=zh_CN&nettype=WIFI&a8scene=0&fontScale=100")
        webview.loadUrl(url)

        toggleEllipsize(this ,
                tv_expand, 2,
                content,
                "展开",
                R.color.colorPrimary, isExpandDescripe)

        tv_expand.setOnClickListener {
            if (isExpandDescripe) {
                isExpandDescripe = false;
                tv_expand.setMaxLines(2);// 收起
            } else {
                isExpandDescripe = true;
                tv_expand.setMaxLines(Integer.MAX_VALUE)// 展开
            }
            toggleEllipsize(this ,
                    tv_expand, 2,
                    content,
                    "展开",
                    R.color.colorPrimary, isExpandDescripe)
        }
    }


    /**
     * 设置textView结尾...后面显示的文字和颜色
     * @param context 上下文
     * @param textView textview
     * @param minLines 最少的行数
     * @param originText 原文本
     * @param endText 结尾文字
     * @param endColorID 结尾文字颜色id
     * @param isExpand 当前是否是展开状态
     */
    fun toggleEllipsize(context: Context,
                        textView: TextView,
                        minLines: Int,
                        originText: String,
                        endText: String,
                        endColorID: Int,
                        isExpand: Boolean) {
        if (TextUtils.isEmpty(originText)) {
            return
        }
        textView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (isExpand) {
                    //展开
                    //折叠
                    val paddingLeft = textView.paddingLeft
                    val paddingRight = textView.paddingRight
                    val paint = textView.paint
                    val moreText = textView.textSize * endText.length
                    val availableTextWidth = (textView.width - paddingLeft - paddingRight) * minLines - moreText
                    val ellipsizeStr = TextUtils.ellipsize(originText, paint,
                            availableTextWidth, TextUtils.TruncateAt.END)

                    if (ellipsizeStr.length < originText.length) {
                        textView.text = originText
                        val temp = originText + "收起"
                        val ssb = SpannableStringBuilder(temp)
                        ssb.setSpan(ForegroundColorSpan(context.getResources().getColor(endColorID)),
                                temp.length - endText.length, temp.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                        textView.text = ssb
                    } else {
                        textView.text = originText
                    }

                } else {
                    //折叠
                    val paddingLeft = textView.paddingLeft
                    val paddingRight = textView.paddingRight
                    val paint = textView.paint
                    val moreText = textView.textSize * endText.length
                    val availableTextWidth = (textView.width - paddingLeft - paddingRight) * minLines - moreText
                    val ellipsizeStr = TextUtils.ellipsize(originText, paint,
                            availableTextWidth, TextUtils.TruncateAt.END)
                    if (ellipsizeStr.length < originText.length) {
                        val temp = ellipsizeStr.toString() + endText
                        val ssb = SpannableStringBuilder(temp)
                        ssb.setSpan(ForegroundColorSpan(context.getResources().getColor(endColorID)),
                                temp.length - endText.length, temp.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                        textView.text = ssb
                    } else {
                        textView.text = originText
                    }
                }
                if (Build.VERSION.SDK_INT >= 16) {
                    textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    textView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
            }
        })
    }
}
