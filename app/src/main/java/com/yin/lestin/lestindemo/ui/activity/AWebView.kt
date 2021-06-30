package com.yin.lestin.lestindemo.ui.activity

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.yin.lestin.lestindemo.R
import kotlinx.android.synthetic.main.activity_aweb_view.*


class AWebView : AppCompatActivity() {
    var url : String = "";

    //5.0以下使用
    private var mUploadMessage: ValueCallback<Uri>? = null

    // 5.0及以上使用
    private var mUploadCallbackAboveL: ValueCallback<Array<Uri?>>? = null

    //拍照图片路径
    private var cameraFielPath: String = ""
    private val FILECHOOSER_RESULTCODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aweb_view)

        var webSettings = web_view.settings

        webSettings.javaScriptEnabled = true //设置能够解析Javascript

        webSettings.domStorageEnabled = true//设置适应Html5 重点是这个设置

        webSettings.setBlockNetworkImage(false)//解决图片不显示
        //不使用缓存：
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE)

        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        var vb = object : com.tencent.smtt.sdk.WebViewClient() {
            override fun shouldOverrideUrlLoading(p0: WebView?, p1: com.tencent.smtt.export.external.interfaces.WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(p0, p1)
            }
        }
        web_view.webViewClient = vb
        web_view.webChromeClient = object : WebChromeClient() {
            // For Android < 3.0
//            fun openFileChooser(valueCallback: ValueCallback<Uri>) {
//                uploadMessage = valueCallback
//                take()
//            }
//
//            // For Android  >= 3.0
//            fun openFileChooser(valueCallback: ValueCallback<Uri>, acceptType: String?) {
//                uploadMessage = valueCallback
//                take()
//            }
//
//            //For Android  >= 4.1
//            override fun openFileChooser(valueCallback: ValueCallback<Uri>, acceptType: String, capture: String) {
//                uploadMessage = valueCallback
//                take()
//            }

            // For Android >= 5.0
            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri?>>, fileChooserParams: FileChooserParams?): Boolean {
                mUploadCallbackAboveL = filePathCallback
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                this@AWebView.startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE)
                return true
                return true
            }

            override fun onProgressChanged(view: com.tencent.smtt.sdk.WebView, newProgress: Int) {
                pb_horizental.incrementProgressBy(newProgress) //平缓增加
                if (newProgress == 100) {
                    pb_horizental.visibility = View.GONE
                }
                super.onProgressChanged(view, newProgress)
            }
        }




//        url = intent.getStringExtra("url")
//        webview.loadUrl("https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzA5MzI3NjE2MA==&scene=124&uin=NTg1MzQzMTYw&key=45db8f9509c464a5082d6e749409ced61d32632d95030a9ab9875c426ecf324a53ae187d16487960e72745c0fa8b04f46b917b34b045c8be7f66a89b8dd6a5d993d2629290e96f2ddb5ef3a23f585d03&lang=zh_CN&nettype=WIFI&a8scene=0&fontScale=100")
        web_view.loadUrl("http://www.wanyu.city/")
//        web_view.loadUrl("http://47.92.52.99/jingxin")


    }

    //    @Override
    //    public boolean onKeyDown(int keyCode, KeyEvent event) {
    //        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
    //            webView.goBack(); //goBack()表示返回WebView的上一页面
    //            return true;
    //        }else{
    //            finish();
    //        }
    //        return true;
    //    }
    /* 改写物理按键返回的逻辑 */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
            // 返回上一页面
            web_view.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return
            val result = if (data == null || resultCode != Activity.RESULT_OK) null else data.data
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data)
            } else if (mUploadMessage != null) {
                mUploadMessage!!.onReceiveValue(result)
                mUploadMessage = null
            }
        }
    }

    private fun onActivityResultAboveL(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return
        }
        var results: Array<Uri?>? = null
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
            } else {
                val dataString = data.dataString
                val clipData: ClipData? = data.clipData
                if (clipData != null) {
                    results = arrayOfNulls(clipData.itemCount)
                    for (i in 0 until clipData.itemCount) {
                        val item: ClipData.Item = clipData.getItemAt(i)
                        results[i] = item.uri
//                        Log.e(FragmentActivity.TAG, "onActivityResultAboveL: " + results[i]!!.path)
                    }
                }
                if (dataString != null) results = arrayOf(Uri.parse(dataString))
//                Log.e(FragmentActivity.TAG, "onActivityResultAboveL: " + results!!.size)
            }
        }
        mUploadCallbackAboveL!!.onReceiveValue(results)
        mUploadCallbackAboveL = null
        return
    }







}


