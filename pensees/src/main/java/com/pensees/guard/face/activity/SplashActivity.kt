package com.pensees.guard.face.activity

import ai.pensees.facequality.PESFaceQualitySDK
import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cimevue.THFace.THFaceSDKHelper
import com.cimevue.THFace.common.THFaceDetectSDK
import com.cimevue.THFace.common.THFaceFeatureSDK
import com.cimevue.THFace.common.THFaceLiveSDK
import com.pensees.guard.R
import com.pensees.guard.face.service.AutoInstallAccessibilityService
import pub.devrel.easypermissions.EasyPermissions

open class SplashActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private val TAG = javaClass.name
    private val MANAGE_OVERLAY = 117
    private val ACCESSIBILITY = 118
    //    private val splashScope = MainScope() + CoroutineName(TAG)
    private val perms = arrayOf<String>(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestPremissions()
    }


    fun requestPremissions() {
        //特殊权限申请
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (EasyPermissions.hasPermissions(this, *perms)) {
//                asyncInitThface()
                val intent = Intent(this@SplashActivity, NewSplashActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                this.requestPermissions(
                        perms,
                        102)
            }
        } else {
//            asyncInitThface()

            val intent = Intent(this@SplashActivity, NewSplashActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun asyncInitThface() {
//        initDataBase()
        Thread {
            initTHFace()
            runOnUiThread {
                val intent = Intent(this@SplashActivity, FaceVerifyActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()

    }

    fun initTHFace() {
//        while (!THFaceSDKHelper.initEnvironment()){}
        //只有作为launcher时 必须先加载质量算法 再加载thface相关 否则质量算法初始化会报错
        PESFaceQualitySDK(this)
        val ret_quality = PESFaceQualitySDK.initPESFaceQuality()
        Log.e(TAG, "pesFaceQuality=${ret_quality}")

//        THFaceSDKHelper.initEnvironment()
        val ret_detect = THFaceDetectSDK.getInstance().init(3, THFaceSDKHelper.defaultModelDir(), THFaceSDKHelper.defaultWriteDir())
        val ret_feature = THFaceFeatureSDK.getInstance().init(1, THFaceSDKHelper.defaultModelDir(), THFaceSDKHelper.defaultWriteDir())
        val ret_live = THFaceLiveSDK.getInstance().init(1, THFaceSDKHelper.defaultModelDir(), THFaceSDKHelper.defaultWriteDir())
        Log.e(TAG, "thFaceDetectSDK=$ret_detect\nthFaceFeatureSDK=$ret_feature\nthFaceLiveSDK=$ret_live")


    }




    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (perms.size == this.perms.size) {
            asyncInitThface()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (perms.size > 0) {
            Toast.makeText(this, "无法获取权限，app即将关闭", Toast.LENGTH_SHORT).show()
            this.finish()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        requestPremissions()

    }
}
