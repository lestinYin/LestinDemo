/**
 * ===============================================================================================
 * Project Name:
 * Class Description: BitmapUtils.java 工具类
 * Created by timeyoyo
 * Created at 2018/1/5 10:14
 * -----------------------------------------------------------------------------------------------
 * ★ Some Tips For You ★
 * 1.https://stackoverflow.com/questions/9768611/encode-and-decode-bitmap-object-in-base64-string-in-android
 * 2.
 * ===============================================================================================
 * HISTORY
 *
 *
 * Tag                      Date       Author           Description
 * ======================== ========== ===============  ==========================================
 * MK                       2018/1/5   timeyoyo         Create new file
 * ===============================================================================================
 */
package com.pensees.guard.utils


import ai.pensees.commons.ImageUtils
import ai.pensees.face.Face
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.net.Uri
import android.provider.MediaStore
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Base64
import com.pensees.guard.R


import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.regex.Pattern


object BitmapUtils {
    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */

    fun bitmapToBase64(bitmap: Bitmap?): String {
        var result: String? = null
        var baos: ByteArrayOutputStream? = null
        try {
            if (bitmap != null) {
                baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

                baos.flush()
                baos.close()

                val bitmapBytes = baos.toByteArray()
                if (bitmapBytes != null) {
                    result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (baos != null) {
                    baos.flush()
                    baos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return replaceBlank(result)
    }

    fun replaceBlank(str: String?): String {
        var dest = ""
        if (str != null) {
            val p = Pattern.compile("\\s*|\t|\r|\n")
            val m = p.matcher(str)
            dest = m.replaceAll("")
        }
        //        System.out.println(dest);
        return dest
    }

     fun bitmapToBytes(bitmap: Bitmap,quality:Int = 100): ByteArray {

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
        try {
            baos.flush()
            baos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return baos.toByteArray()
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */

    fun base64ToBitmap(base64Data: String): Bitmap {
        val bytes = Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }


    /**
     * 获取url且避免存储到sdcard
     * https://stackoverflow.com/questions/26059748/is-there-away-to-get-uri-of-bitmap-with-out-save-it-to-sdcard
     *
     * @param inContext
     * @param inImage
     * @return
     */
    fun getImageUri(inContext: Context, inImage: Bitmap?): Uri {
        if (null == inImage) {
            return Uri.parse("res:///" + R.mipmap.ic_launcher)
        }
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        try {
            bytes.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Uri.parse(path)
    }


    /**
     * 获取url且避免存储到sdcard
     * https://stackoverflow.com/questions/26059748/is-there-away-to-get-uri-of-bitmap-with-out-save-it-to-sdcard
     *
     * @param inContext
     * @param imgData
     * @return
     */

    fun getImageUriByStr(inContext: Context, imgData: String): Uri {
        return getImageUri(inContext, base64ToBitmap(imgData))
    }

    @Throws(IllegalArgumentException::class)
    fun convert(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(
            base64Str.substring(base64Str.indexOf(",") + 1),
            Base64.DEFAULT
        )

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun compressBitmap(bitmap: Bitmap, quality: Int): Bitmap?{
        var compress :Bitmap? =null
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        if (outputStream.toByteArray() != null) {
            compress=BitmapFactory.decodeByteArray(outputStream.toByteArray(),0,outputStream.toByteArray().size)
        }
        return compress
    }

    fun convert(bitmap: Bitmap ,quality: Int): String? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        var tmp : String? = null
        if (outputStream.toByteArray() != null) {
            tmp = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
        }
        try {
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return tmp
    }
    fun convert(context: Context, filename: String, bitmap: Bitmap): File {
        //create a file to write bitmap data
        val f = File(context.cacheDir, filename)
        try {
            f.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos)
        val bitmapData = bos.toByteArray()
        try {
            bos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(f)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //        if (!bitmap.isRecycled()) {
        //            bitmap.recycle();   //回收图片所占的内存
        //            System.gc();
        //        }
        return f
    }



    @Throws(IOException::class)
    fun saveBitmap(bitmap: Bitmap, bitName: String) {
        val file = File("/sdcard/DCIM/Camera/$bitName")
        if (file.exists()) {
            file.delete()
        }
        val out: FileOutputStream
        try {
            out = FileOutputStream(file)
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush()
                out.close()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * http://www.cnblogs.com/manuosex/p/3661762.html
     *
     * @param context
     * @param resId
     * @return
     */
    fun readBitMap(context: Context, resId: Int): Bitmap? {
        val opt = BitmapFactory.Options()
        opt.inPreferredConfig = Bitmap.Config.RGB_565
        opt.inPurgeable = true
        opt.inInputShareable = true
        //获取资源图片
        val `is` = context.resources.openRawResource(resId)
        return BitmapFactory.decodeStream(`is`, null, opt)
    }

    fun readRes2Base64(context: Context, resId: Int): String? {
        val opt = BitmapFactory.Options()
        opt.inPreferredConfig = Bitmap.Config.RGB_565
        opt.inPurgeable = true
        opt.inInputShareable = true
        //获取资源图片
        val `is` = context.resources.openRawResource(resId)
        val bmp = BitmapFactory.decodeStream(`is`, null, opt)

        var bpBase64Str: String? = null
        if (bmp != null) {
            val bpBase64Bytes = bitmapToBytes(bmp)
            bpBase64Str = Base64.encodeToString(bpBase64Bytes, Base64.DEFAULT)
        }

        return bpBase64Str
    }


    fun returnBitMap(url: String): Bitmap? {

        var bitmap: Bitmap? = null

        var imageurl: URL? = null

        try {
            imageurl = URL(url)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        try {
            val conn = imageurl!!.openConnection() as HttpURLConnection
            conn.doInput = true
            conn.connect()
            val `is` = conn.inputStream
            bitmap = BitmapFactory.decodeStream(`is`)
            `is`.close()
        } catch (e: Exception) {
            //e.printStackTrace()
        }

        return bitmap
    }


    fun detectMaxFace(faces: Array<Face>, width: Int, height: Int, bgr: ByteArray): Bitmap? {
        var bestRect: Rect? = null
        var maxFace: Face? = null
        for (face in faces) {
            var tmpRect = face.rect
            if (bestRect == null) {
                bestRect = tmpRect
            } else if (tmpRect != null) {
                if (tmpRect!!.width() * tmpRect!!.height() > bestRect.width() * bestRect.height()) {
                    bestRect = tmpRect
                }
            }
        }
        if (bestRect != null) {

            var maxRect = ImageUtils.tryMakeBestRect(width, height, bestRect,1.6f,1.6f)
            if (maxRect != null) {
                try {
                    val bgrface = ImageUtils.cropBGR(bgr, width, height, maxRect)
                    val bmp = Bitmap.createBitmap(
                        maxRect.right - maxRect.left,
                        maxRect.bottom - maxRect.top,
                        Bitmap.Config.ARGB_8888
                    )
                    ImageUtils.BGRToBitmap(bgrface, bmp)

                    return bmp

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }


    fun tryCropBestFace(face:Rect, width: Int, height: Int, bgr: ByteArray): Bitmap? {
        var bestRect: Rect? = face
        if (bestRect != null) {

            var maxRect = ImageUtils.tryMakeBestRect(width, height, bestRect,1.6f,1.6f)
            if (maxRect != null) {
                try {
                    val bgrface = ImageUtils.cropBGR(bgr, width, height, maxRect)
                    val bmp = Bitmap.createBitmap(
                        maxRect.right - maxRect.left,
                        maxRect.bottom - maxRect.top,
                        Bitmap.Config.ARGB_8888
                    )
                    ImageUtils.BGRToBitmap(bgrface, bmp)

                    return bmp

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    fun tryCropBestBGRFace(face:Rect, width: Int, height: Int, bgr: ByteArray,xScale:Float = 1.4f,yScale:Float = 1.4f):  Pair<ByteArray?,Rect?>? {
        var bestRect: Rect? = face
        if (bestRect != null) {

            var maxRect = ImageUtils.tryMakeBestRect(width, height, bestRect,xScale,yScale)
            if (maxRect != null) {
                try {
                    return  Pair(ImageUtils.cropBGR(bgr, width, height, maxRect),maxRect)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    //图片缩放比例
    private val BITMAP_SCALE = 0.4f

    /**
     * 模糊图片的具体方法
     *
     * @param context 上下文对象
     * @param image   需要模糊的图片
     * @return 模糊处理后的图片
     */
    fun blurBitmap(context: Context, image: Bitmap, blurRadius: Float): Bitmap {
        // 计算图片缩小后的长宽
        val width = Math.round(image.width * BITMAP_SCALE)
        val height = Math.round(image.height * BITMAP_SCALE)

        // 将缩小后的图片做为预渲染的图片
        val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
        // 创建一张渲染后的输出图片
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        // 创建RenderScript内核对象
        val rs = RenderScript.create(context)
        // 创建一个模糊效果的RenderScript的工具对象
//        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)

        // 设置渲染的模糊程度, 25f是最大模糊度
//        blurScript.setRadius(blurRadius)
        // 设置blurScript对象的输入内存
//        blurScript.setInput(tmpIn)
        // 将输出数据保存到输出内存中
//        blurScript.forEach(tmpOut)

        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap)

        return outputBitmap
    }
}
