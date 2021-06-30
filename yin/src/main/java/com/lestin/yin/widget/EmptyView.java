package com.lestin.yin.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lestin.yin.R;


public class EmptyView extends LinearLayout {
    private int loadingRes;
    private int emptyRes;
    private int failRes;
    private ImageView mLoadingImage;
    private ViewGroup mLoadingLayout;
    private TextView mResultText;
    private ImageView mResultImage;
    private ViewGroup mResultLayout;
    private Context context;

    public EmptyView(Context context) {

        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initLayout(context, attrs);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.context=context;
        initLayout(context, attrs);
    }

    private void initLayout(Context context, AttributeSet attrs) {
        LayoutInflater.from(context)
                .inflate(R.layout.custom_empty_view, this);
        this.mLoadingLayout = ((ViewGroup) findViewById(R.id.loadingLayout));
        this.mResultLayout = ((ViewGroup) findViewById(R.id.resultLayout));
        this.mLoadingImage = ((ImageView) findViewById(R.id.loadingImage));
        this.mResultImage = ((ImageView) findViewById(R.id.resultImage));
        this.mResultText = ((TextView) findViewById(R.id.resultText));
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.EmptyView, 0, 0);

            this.loadingRes = a
                    .getResourceId(R.styleable.EmptyView_loadingDrawable,
                            attrs.getAttributeResourceValue(null, "src", 0));
            this.emptyRes = a
                    .getResourceId(R.styleable.EmptyView_emptyDrawable,
                            attrs.getAttributeResourceValue(null, "src", 0));
            this.failRes = a
                    .getResourceId(R.styleable.EmptyView_failDrawable,
                            attrs.getAttributeResourceValue(null, "src", 0));
            a.recycle();
        }
    }


    public void showLoadingView() {

        mLoadingLayout.setVisibility(VISIBLE);
        mLoadingImage.setImageResource(R.drawable.pub_icon_loading);
        if (mLoadingImage!=null){
            Animation animation= AnimationUtils.loadAnimation(context,R.anim.rotate_loading_infinite);
            mLoadingImage.startAnimation(animation);
        }
    }

    public void showEmptyResultView() {
        showEmptyResultView(R.string.load_empty);
    }

    public void showEmptyResultView(int resId) {
        this.mResultLayout.setVisibility(View.VISIBLE);
        this.mLoadingLayout.setVisibility(View.GONE);
        this.mResultImage.setImageResource(this.emptyRes);
        this.mResultText.setText(resId);
    }

    public void showEmptyResultView(String result) {
        this.mResultLayout.setVisibility(View.VISIBLE);
        this.mLoadingLayout.setVisibility(View.GONE);
        this.mResultImage.setImageResource(this.emptyRes);
        this.mResultText.setText(result);
    }

    public void showFailResultView() {
        this.mResultLayout.setVisibility(View.VISIBLE);
        this.mResultImage.setImageResource(this.failRes);
        this.mLoadingLayout.setVisibility(View.GONE);
        this.mResultText.setText(R.string.load_failed);
    }

    public void showFailResultView(int resId) {
        this.mResultLayout.setVisibility(View.VISIBLE);
        this.mResultImage.setImageResource(this.failRes);
        this.mLoadingLayout.setVisibility(View.GONE);
        this.mResultText.setText(resId);
    }

    public void showFailResultView(String result) {
        this.setVisibility(VISIBLE);
        this.mResultLayout.setVisibility(View.VISIBLE);
        this.mResultImage.setImageResource(this.failRes);
        this.mLoadingLayout.setVisibility(View.GONE);
        this.mResultText.setText(result);
    }

    public void hideView() {
        setVisibility(View.GONE);
    }

}
