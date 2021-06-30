package com.lestin.yin.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 作者：Lestin.yin on 2017/6/23 16:15
 * 邮箱：lestin.yin@gmail.com
 * Description:
 */

public class CompressImgUtil {
    // 图片压缩处理
    public static Bitmap getThumbUploadImage(String oldPath)
            throws Exception {
        if (TextUtils.isEmpty(oldPath)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(oldPath, options);
        int width = options.outWidth;
        int reqWidth = 800;// bitmapMaxWidth;
        Matrix matrix = new Matrix();
        float scale = (float) reqWidth / (float) width;
        scale = (scale > 1.0 ? 1 : scale);
        matrix.postScale(scale, scale); // 长和宽放大缩小的比例
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(oldPath, options);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
    /**
     * 处理图片
     *
     * @param filePath 原文件
     * @param outPath  输出文件
     * @param isDelete 是否删除原文件
     * @param maxSize  缩放最大是多少（缩放后图片不超过这个值）
     * @return
     */
    public static File compressImage(File filePath, File outPath, boolean isDelete, int maxSize) {
        try {
            Bitmap bitmap = getThumbUploadImage(filePath.getPath());
            if (bitmap == null) {
                return filePath;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > maxSize) { // 循环判断如果压缩后图片是否大于250kb,大于继续压缩
                options -= 10;// 每次都减少10
                baos.reset();// 重置baos即清空baos
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            }
            if(!outPath.exists()) outPath.createNewFile();
            FileOutputStream fos = new FileOutputStream(outPath);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            if (isDelete) {
                if (filePath.exists()) {
                    filePath.delete();
                }
            }
            baos.close();
            return outPath;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return filePath;
    }
}
