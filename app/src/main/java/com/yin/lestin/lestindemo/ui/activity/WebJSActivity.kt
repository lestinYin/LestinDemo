package com.yin.lestin.lestindemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yin.lestin.lestindemo.R
import android.widget.Toast
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.webkit.*
import android.widget.Button


class WebJSActivity : AppCompatActivity() {

    private var myWebView: WebView? = null
    private var myButton: Button? = null

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_js)

        myWebView = findViewById<View>(R.id.myWebView) as WebView?

        // 得到设置属性的对象
        val webSettings = myWebView!!.settings

        // 使能JavaScript
        webSettings.javaScriptEnabled = true

        // 支持中文，否则页面中中文显示乱码
        webSettings.defaultTextEncodingName = "GBK"

        // 限制在WebView中打开网页，而不用默认浏览器
        myWebView!!.webViewClient = WebViewClient()

        // 如果不设置这个，JS代码中的按钮会显示，但是按下去却不弹出对话框
        // Sets the chrome handler. This is an implementation of WebChromeClient
        // for use in handling JavaScript dialogs, favicons, titles, and the
        // progress. This will replace the current handler.
        myWebView!!.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView, url: String, message: String,
                                   result: JsResult): Boolean {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result)
            }
        }

        // 用JavaScript调用Android函数：
        // 先建立桥梁类，将要调用的Android代码写入桥梁类的public函数
        // 绑定桥梁类和WebView中运行的JavaScript代码
        // 将一个对象起一个别名传入，在JS代码中用这个别名代替这个对象，可以调用这个对象的一些方法
        myWebView!!.addJavascriptInterface(WebAppInterface(this),
                "myInterfaceName")

        // 载入页面：本地html资源文件
        myWebView!!.loadUrl("file:///android_asset/sample.html")

        // 这里用一个Android按钮按下后调用JS中的代码
        myButton = findViewById<View>(R.id.button1) as Button

        myButton!!.setOnClickListener {
            // 用Android代码调用JavaScript函数：
            val hello = "hello &&&"
            myWebView!!.loadUrl("javascript:myFunction('"+hello+"')")

            // 这里实现的效果和在网页中点击第一个按钮的效果一致
        }

    }

    /**
     * 自定义的Android代码和JavaScript代码之间的桥梁类
     *
     * @author 1
     */
    inner class WebAppInterface
    /** Instantiate the interface and set the context  */
    internal constructor(internal var mContext: Context) {

        /** Show a toast from the web page  */
        // 如果target 大于等于API 17，则需要加上如下注解
         @JavascriptInterface
        fun showToast(toast: String) {
            // Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show()
        }
    }

}
