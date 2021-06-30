package com.pensees.guard.face.activity

import ai.pensees.commons.ImageUtils
import ai.pensees.sdk.pesemotion.PESEmotion
import ai.pensees.view.FaceCameraResult
import ai.pensees.view.FaceCameraView
import ai.pensees.view.FaceCameraViewLite
import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cimevue.THFace.THFI_FacePos
import com.cimevue.THFace.common.THFaceDetectSDK
import com.cimevue.THFace.common.THFaceSDKImageFormat
import com.pensees.guard.Constant
import com.pensees.guard.R
import com.pensees.guard.face.presenter.FacePresenter
import com.pensees.guard.face.view.FaceView
import kotlinx.android.synthetic.main.activity_face_verify.*
import java.lang.reflect.InvocationTargetException



/**
 *
 * @ProjectName:    LestinDemo
 * @Package:        com.pensees.guard.face.presenter
 * @ClassName:      FacePresenter
 * @Description:     人脸认证页面Demo
 * @Author:         lestin.yin
 * @CreateDate:     2020-07-08 16:26
 * @Version:        1.0
 */
class FaceVerifyActivity : AppCompatActivity() {


    //每秒跳帧
    private var mSkipFrame: Int = 10
    private val screenWidth = 1280
    private val screenHeight = 800
    private var mSmileCount = 0
    private var mFullImage: ByteArray? = null

    private var SMILE = arrayOf("Angry" , "Disgust" , "Fear" , "Happy" , "Sad", "Surprise" , "Neutral" , "Contempt" )


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_verify)
//        this.requestPermissions(
//                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
//                        .ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                102)
//        val ret = PESEmotion.init(this)
//        PESEmotion.init(this, 2, PESEmotion.ENGIN_GPU)
        initFaceCamera()
        setData()
    }

    private fun setData() {
        bt_clean.setOnClickListener {
            mSmileCount = 0
            tv_smile.text = mSmileCount.toString()

        }
        iv_back.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        mSmileCount = 0
        editText.onChanged { s, start, before, count ->

        }
    }

    /**
     * 初始化相机
     */
    private fun initFaceCamera() {
        view_camera.clipToOutline = true
        var cameraIds = mutableListOf(Constant.CameraInfrared)
        //如果需要判断活体和红外需要加入红外相机
//        if (isAliveDetect && useInfraredAlive) {
        cameraIds.add(Constant.CameraInfrared)
//        }
        view_camera.setCameraIndex(cameraIds.toIntArray())
        view_camera.setRotation(Constant.CarmeraRotation)
//        view_camera.setSkipFrame(mSkipFrame)

        view_camera.setCameraIndex(cameraIds.toIntArray())
//        view_camera.resumeView()
//        view_camera.startCameraView()
//        view_camera.setOtherTextures(listOf())

        view_camera.setPreviewRotation(Constant.CameraRGB, 270)
        view_camera.setCallbackRotation(Constant.CameraRGB, 90)

        view_camera.setPreviewRotation(Constant.CameraInfrared, 270)
        view_camera.setCallbackRotation(Constant.CameraInfrared, 90)
        val rgb_camera = getProperty("rgb_camera")
//        val rgb_camera = "1"
        if (rgb_camera.isNotBlank()) {
            val rgb_cameraid = rgb_camera.toInt()
            Constant.MAIN_CAMERAID = rgb_cameraid
            view_camera.setCameraIndex(intArrayOf(rgb_cameraid))
        } else {
            Constant.MAIN_CAMERAID = 0
            view_camera.setCameraIndex(intArrayOf(0))
        }
        view_camera.setRotation(0)
//        view_camera.setSkipFrame(20)
        view_camera.setisFacedetect(false)



        view_camera.faceListener = (object : FaceCameraViewLite.FaceListener {
            override fun onFacesInfo(facesInfo: Array<FaceCameraResult>) {
                if (facesInfo != null) {
//                    onFaceInfoCallBack(facesInfo)
                }

//
            }
        })


    }

    private var formerSmile = ""
    /**
     * 相机回掉
     */
    private fun onFaceInfoCallBack(facesInfo: Array<FaceCameraResult>) {
        var rgbFaceResult: FaceCameraResult? =
                filterCameraResult(facesInfo, Constant.CameraRGB)
        var bgr = rgbFaceResult!!.bgr!!
        var height = rgbFaceResult!!.imgHeight!!
        var width = rgbFaceResult!!.imgWidth!!

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        ImageUtils.BGRToBitmap(bgr, bitmap)

//        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.happy)
//        val bgr = ImageUtils.bitmapToBGR(bitmap)

        val emotions = PESEmotion.detect(bgr, bitmap.width, bitmap.height, PESEmotion.IMAGE_BGR)
        runOnUiThread {
            blurry_background.setImageBitmap(bitmap)
            blurry_background.scaleY = 1f
            blurry_background.scaleX = -1f
            tv_emotion_name.text = emotions.name
            if (emotions.name == "Happy" && formerSmile != "Happy") {
                mSmileCount += 1
                tv_smile.text = mSmileCount.toString()
            }
            formerSmile = emotions.name
        }
    }

    private fun retrieveBestRectFace(faces: Array<THFI_FacePos>): Rect {
        faces.sortByDescending { (it.rcFace.right - it.rcFace.left) * (it.rcFace.bottom - it.rcFace.top) }
        return Rect(faces[0].rcFace.left, faces[0].rcFace.top, faces[0].rcFace.right, faces[0].rcFace.bottom)
    }

    /**
     * 获取最后一张RGB
     */
    private fun filterCameraResult(facesInfo: Array<FaceCameraResult>, cameraId: Int): FaceCameraResult? {
        return facesInfo.last {
            it?.cameraId == cameraId
        }
    }


    private fun getProperty(key: String): String {
        try {
            val classtype = Class.forName("android.os.SystemProperties")
            val method = classtype.getDeclaredMethod("get", *arrayOf<Class<*>>(String::class.java))
            return method.invoke(classtype, key) as String
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return ""
    }

    override fun onPause() {
        super.onPause()
        view_camera.pauseView()
        view_camera.stopCameraView()
        PESEmotion.release()
    }

    // 定义EditText扩展函数，方便监听TextChange
    inline fun EditText.onTextChange(
            crossinline afterChanged: (s: Editable?) -> Unit = {},
            crossinline beforeChanged: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { s, start, couunt, after -> },
            crossinline onChanged: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { s, start, before, count -> }
    ) {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = afterChanged(s)
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = beforeChanged(s, start, count, after)
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = onChanged(s, start, before, count)
        }
        addTextChangedListener(listener)
    }

    inline fun EditText.onChanged(
            crossinline onChanged: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> }) {
        onTextChange(onChanged = onChanged)
    }


}
