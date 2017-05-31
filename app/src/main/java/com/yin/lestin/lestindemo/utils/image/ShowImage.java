package com.yin.lestin.lestindemo.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.yin.lestin.lestindemo.R;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ShowImage {

    public static void show(Context context, String url, ImageView imageView) {
//        Glide.with(context)
//                .load(url)
//                .placeholder(R.drawable.white_bg_f)
//                .error(R.drawable.white_bg_f)
////                .into(imageView);
//        GlideApp
//                .with(myFragment)
//                .load(url)
//                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
//                .into(myImageView);
        GlideApp.with(context).asBitmap()
                .load(url)
                .fitCenter()//等比例缩放在正中间
                .error(R.mipmap.ic_launcher)//占位图片
                .placeholder(R.mipmap.ic_launcher)//异常图片
                .into(imageView);

    }
    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context,String url,ImageView imageView){

        RequestBuilder<Bitmap> bitmapRequestBuilder= GlideApp.with(context)
                .asBitmap()//指定Bitmap类型的RequestBuilder
                .load(url)//网络URL
                .error(R.mipmap.ic_launcher)//异常图片
                .placeholder(R.mipmap.ic_launcher)//占位图片
                .fallback(R.mipmap.ic_launcher);//当url为空时，显示图片

        RequestOptions requestOptions=new RequestOptions();
        //在RequestOptions中使用Transformations
        requestOptions.transform(new CircleTransform(context));

        //RequestBuilder<Bitmap> 中添加RequestOptions
        bitmapRequestBuilder.apply(requestOptions).into(imageView);
    }
    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public static void loadImage1(Context context,String url,ImageView imageView){

        RequestBuilder<Bitmap> bitmapRequestBuilder= GlideApp.with(context)
                .asBitmap()//指定Bitmap类型的RequestBuilder
                .load(url)//网络URL
                .error(R.mipmap.ic_launcher)//异常图片
                .placeholder(R.mipmap.ic_launcher)//占位图片
                .fallback(R.mipmap.ic_launcher);//当url为空时，显示图片

        //在RequestBuilder<Bitmap> 中使用自定义的ImageViewTarget
        bitmapRequestBuilder.into(new CircularBitmapImageViewTarget(context,imageView));
    }
}
