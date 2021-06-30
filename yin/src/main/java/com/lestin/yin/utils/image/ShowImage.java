package com.lestin.yin.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;


import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lestin.yin.R;
import com.lestin.yin.widget.photoview.PhotoView;

import java.io.ByteArrayOutputStream;
import java.util.Random;


/**
 * autour: lestin.yin
 * email:lestin.yin@gmail.com
 * date: 2017/6/7 17:53
 * version:
 * description:
 */

public class ShowImage {

    public static void showPhotoView(Context context, String url, PhotoView mImageView) {

//        GlideApp.with(context).asBitmap()
//                .load(url)
//                .centerInside()//等比例缩放在正中间
//                .error(R.drawable.no_data)//占位图片
//                .placeholder(R.drawable.no_data)//异常图片
//                .into(mImageView);

    }

    public static void show(Context context, String url, ImageView imageView) {
//        GlideApp.with(context).asBitmap()
//                .load(url)
//                .centerCrop()//等比例缩放在正中间
//                .error(R.drawable.no_data)//占位图片
//                .placeholder(R.drawable.no_data)//异常图片
//                .into(imageView);

    }

    public static void showWithDefault(Context context, String url, ImageView imageView, int defaultPic) {
//        GlideApp.with(context).asBitmap()
//                .load(url)
//                .centerCrop()//等比例缩放在正中间
//                .error(defaultPic)//占位图片
//                .placeholder(defaultPic)//异常图片
//                .into(imageView);

    }

    public static void showCircle(Context context, String url, ImageView imageView) {
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
//        GlideApp.with(context).asBitmap()
//                .load(url)
//                .error(R.drawable.no_data_circle)//占位图片
//                .placeholder(R.drawable.no_data_circle)//异常图片
//                .centerCrop()
//                .apply(requestOptions)
//                .into(imageView);
//        Glide.with(context).load(url)
//                .placeholder(R.drawable.no_data).error(R.drawable.no_data)
//                .into(new SimpleTarget<GlideDrawable>() {
//                          @Override
//                          public void onResourceReady(GlideDrawable resource,
//                                                      GlideAnimation<? super GlideDrawable> glideAnimation) {
//                              imageView.setImageDrawable(resource);
//                          }
//                      }
//                );


        //into中用Target，占位符（placeholder、error）需要在回调中设置
//        GlideApp.with(context)
//                .asBitmap()
//                .load(url)
//                .placeholder(R.drawable.no_data_circle) //设置资源加载过程中的占位符
//                .fallback(R.drawable.no_data_circle)
//                .error(R.drawable.no_data_circle)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .apply(requestOptions)
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                        imageView.setImageBitmap(resource);
//                    }
//
//                    @Override
//                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                        super.onLoadFailed(errorDrawable);
//                        if(errorDrawable!=null){
//                            imageView.setImageDrawable(errorDrawable);
//                        }
//                    }
//
//                    @Override
//                    public void onLoadStarted(@Nullable Drawable placeholder) {
//                        super.onLoadStarted(placeholder);
//                        if(placeholder!=null){
//                            imageView.setImageDrawable(placeholder);
//                        }
//                    }
//                });

    }

    public static void showRoundCorners(Context context, String url, ImageView imageView, int radius) {
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.centerCrop();

//        GlideApp.with(context).asBitmap()
//                .load(url)
//                .error(R.drawable.no_data)//占位图片
//                .placeholder(R.drawable.no_data)//异常图片
//                .centerCrop()
//                .apply(new RequestOptions()
//                        .transforms(new CenterCrop(), new RoundedCorners(radius)
//                        ))
//                .into(imageView);

    }

    public static void showBitmap(Context context, Bitmap bitmap, ImageView imageView) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
//        GlideApp.with(context.getApplicationContext())
//                .load(bytes)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
//                .skipMemoryCache(true)//跳过内存缓存
//                .transform(new RoundedCorners(5))
//                .into(imageView);
//        transform(new CenterCrop(context), new GlideRoundTransform(context))
    }

    public static void showRes(Context context, Integer resourceId, ImageView imageView, Integer radius) {
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.centerCrop();
//        GlideApp.with(context)
//                .load(resourceId)
//                .error(R.drawable.no_data)//占位图片
//                .placeholder(R.drawable.no_data)//异常图片
//                .apply(new RequestOptions()
//                        .transforms(new CenterCrop(), new RoundedCorners(radius)
//                        ))
//                .into(imageView);
    }

    private static int getColor() {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        return color;
    }
}
