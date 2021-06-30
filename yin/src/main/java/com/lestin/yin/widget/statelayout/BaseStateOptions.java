package com.lestin.yin.widget.statelayout;


import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lestin.yin.R;


/**
 * autour: lestin.yin
 * email:lestin.yin@gmail.com
 * date: 2017/6/30 9:58
 * version: 
 * description: 
*/
public abstract class BaseStateOptions extends StateOptions {

    TextView mMessage;
    ImageView mImageView;

    @Override
    protected int layoutId() {
        return R.layout.view_state_base;
    }

    @Override
    protected void init(View rootView) {
        super.init(rootView);
        mMessage = rootView.findViewById(R.id.tv_message);
        mImageView = rootView.findViewById(R.id.iv_image);
        mMessage.setText(message());
        mImageView.setImageResource(imageId());

        int color = ContextCompat.getColor(rootView.getContext(), tintColor());

//        mImageView.setColorFilter(color);
        mMessage.setTextColor(color);
    }

    protected abstract String message();

    protected abstract int imageId();

    protected int tintColor() {
        return R.color.color_666666;
    }
}
