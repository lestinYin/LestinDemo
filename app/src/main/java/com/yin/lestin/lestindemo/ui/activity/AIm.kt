package com.yin.lestin.lestindemo.ui.activity

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yin.lestin.lestindemo.R
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import com.yin.lestin.lestindemo.PhoneReceiver
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.Manifest.permission
import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.telecom.TelecomManager
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.text.Typography.tm


class AIm : AppCompatActivity() {

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aim)

//        val phoneReceiver = PhoneReceiver()
//        registerReceiver(phoneReceiver)
//        callPhone("18600262335")

        checkReadStatePermission(CAMERA,REQUEST_MODIFY_PHONE_STATE)





        call("13051123027")
//        call("18612693537")


    }

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    fun callPhone(phoneNum: String) {
        val intent = Intent(Intent.ACTION_CALL)
        val data = Uri.parse("tel:$phoneNum")
        intent.data = data
        startActivity(intent)
    }


    val REQUEST_CALL_PERMISSION = 10111 //拨号请求码
    val REQUEST_ANSWER_PHONE_CALLS = 10112 //拨号请求码
    val REQUEST_MODIFY_PHONE_STATE = 10113 //拨号请求码

    /**
     * 判断是否有某项权限
     * @param string_permission 权限
     * @param request_code 请求码
     * @return
     */
    fun checkReadPermission(string_permission: String, request_code: Int): Boolean {
        var flag = false
        if (ContextCompat.checkSelfPermission(this, string_permission) === PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true
        } else {//申请权限
            ActivityCompat.requestPermissions(this, arrayOf(string_permission, ANSWER_PHONE_CALLS), request_code)
        }
        return flag
    }


    fun checkReadStatePermission(string_permission: String, request_code: Int): Boolean {
        var flag = false
        if (ContextCompat.checkSelfPermission(this, string_permission) === PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true
        } else {//申请权限
            ActivityCompat.requestPermissions(this, arrayOf(string_permission), request_code)
        }
        return flag
    }

    /**
     * 检查权限后的回调
     * @param requestCode 请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CALL_PERMISSION //拨打电话
            -> if (permissions.size != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                Toast.makeText(this, "请允许拨号权限后再试", Toast.LENGTH_SHORT).show()
            } else {//成功
                checkReadStatePermission(Manifest.permission.ANSWER_PHONE_CALLS, REQUEST_CALL_PERMISSION);
            }
            REQUEST_MODIFY_PHONE_STATE
            -> if (permissions.size != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                Toast.makeText(this, "请允许拨号权限后再试", Toast.LENGTH_SHORT).show()
            } else {//成功
                val systemService:TelecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
                systemService.acceptRingingCall()
                systemService.endCall()
            }
        }
    }

    /**
     * 拨打电话（直接拨打）
     * @param telPhone 电话
     */
    fun call(telPhone: String) {
        if (checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION)) {
            var intent = Intent() // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            var data = Uri.parse("tel:" + telPhone); // 设置数据
            intent.setData(data);
            startActivity(intent); // 激活Activity组件
        }
    }


    @SuppressLint("PrivateApi")
    fun endCall(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            if (telecomManager != null && ContextCompat.checkSelfPermission(context, Manifest.permission.ANSWER_PHONE_CALLS) == PackageManager.PERMISSION_GRANTED) {
                telecomManager.endCall()
                return true
            }
            return false
        }
        //use unofficial API for older Android versions, as written here: https://stackoverflow.com/a/8380418/878126
        try {
            val telephonyClass = Class.forName("com.android.internal.telephony.ITelephony")
            val telephonyStubClass = telephonyClass.classes[0]
            val serviceManagerClass = Class.forName("android.os.ServiceManager")
            val serviceManagerNativeClass = Class.forName("android.os.ServiceManagerNative")
            val getService = serviceManagerClass.getMethod("getService", String::class.java)
            val tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder::class.java)
            val tmpBinder = Binder()
            tmpBinder.attachInterface(null, "fake")
            val serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder)
            val retbinder = getService.invoke(serviceManagerObject, "phone") as IBinder
            val serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder::class.java)
            val telephonyObject = serviceMethod.invoke(null, retbinder)
            val telephonyEndCall = telephonyClass.getMethod("endCall")
            telephonyEndCall.invoke(telephonyObject)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
