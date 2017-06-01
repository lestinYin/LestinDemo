package com.yin.lestin.lestindemo.utils.image;

import android.content.Context;
import android.widget.ImageView;
import com.yin.lestin.lestindemo.R;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ShowImage {

    public static void show(Context context, String url, ImageView imageView) {
        GlideApp.with(context).asBitmap()
                .load(url)
                .fitCenter()//等比例缩放在正中间
                .error(R.mipmap.ic_launcher)//占位图片
                .placeholder(R.mipmap.ic_launcher)//异常图片
                .into(imageView);

    }
    public static void showCircle(Context context, String url, ImageView imageView) {
        GlideApp.with(context).asBitmap()
                .load(url)
                .circleCrop()
                .error(R.mipmap.ic_launcher)//占位图片
                .placeholder(R.mipmap.ic_launcher)//异常图片
                .into(imageView);

    }
}
