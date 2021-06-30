package com.pensees.guard.utils;

/**
 * @ClassName: FaceRectView
 * @Description: java类作用描述
 * @Author: lestin.yin
 * @CreateDate: 2020-08-04 19:03
 * @Version: 1.0
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.pensees.guard.R;

/**
 * 自定义虹软人脸识别框/人脸抓拍框/人脸追踪框
 * Created by HDL on 2018/7/31.
 */
public class FaceRectView extends View {
    private Rect rect;
    private int screenWidth;
    private int screenHeight;

    public FaceRectView(Context context) {
        this(context, null);
    }

    public FaceRectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceRectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
        initPaint(context);
    }

    private void initPaint(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(context.getResources().getColor(R.color.colorAccent));
    }

    private Paint mPaint;

    /**
     * 开始画矩形框
     *
     * @param rect1
     */
    public void drawFaceRect(Rect rect1) {
        this.rect = rect1;
        //将屏幕人脸框转换为视频区域的人脸框
//        rect.left = rect.left * getWidth() / screenHeight;
//        rect.right = rect.right * getWidth() / screenHeight;
//        rect.top = rect.top * getHeight() / screenHeight;
//        rect.bottom = rect.bottom * getHeight() / screenHeight;

        rect.left = getWidth() -rect.left;
        rect.right = getWidth() - rect.right ;
        rect.top = rect.top ;
        rect.bottom = rect.bottom;
        //在主线程发起绘制请求
        postInvalidate();
    }

    public void clearRect() {
        rect = null;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rect != null) {
            /**
             * 左上角的竖线
             */
            canvas.drawLine(rect.left, rect.top, rect.left, rect.top +50, mPaint);
            /**
             * 左上角的横线
             */
            canvas.drawLine(rect.left, rect.top, rect.left - 50, rect.top, mPaint);

            /**
             * 右上角的竖线
             */
            canvas.drawLine(rect.right, rect.top, rect.right + 50, rect.top, mPaint);
            /**
             * 右上角的横线
             */
            canvas.drawLine(rect.right, rect.top, rect.right, rect.top +50, mPaint);
            /**
             * 左下角的竖线
             */
            canvas.drawLine(rect.left, rect.bottom, rect.left, rect.bottom - 50, mPaint);
            /**
             * 左下角的横线
             */
            canvas.drawLine(rect.left, rect.bottom, rect.left - 50, rect.bottom, mPaint);

            /**
             * 右下角的竖线
             */
            canvas.drawLine(rect.right, rect.bottom, rect.right, rect.bottom-50, mPaint);
            /**
             * 右下角的横线
             */
            canvas.drawLine(rect.right, rect.bottom, rect.right+50, rect.bottom , mPaint);
        }
    }
}


