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
import android.Manifest.permission.CALL_PHONE
import android.widget.Toast
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class AIm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aim)

//        val phoneReceiver = PhoneReceiver()
//        registerReceiver(phoneReceiver)
//        callPhone("18600262335")
        call("18600262335")


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
                call("tel:" + "18600262335")
            }
        }
    }

    /**
     * 拨打电话（直接拨打）
     * @param telPhone 电话
     */
    fun call(telPhone: String) {
        if (checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION)) {
            var intent =Intent() // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            var data = Uri.parse("tel:" + telPhone); // 设置数据
            intent.setData(data);
            startActivity(intent); // 激活Activity组件
        }
    }
}
