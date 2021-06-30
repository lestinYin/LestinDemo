package com.lestin.yin.widget.statelayout;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.lestin.yin.R;


/**
 * Model builder class to show custom state
// * @see com.gturedi.views.StatefulLayout#showCustom(StateOptions)
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class SimpleStateOptions extends StateOptions {

    @DrawableRes
    private int imageRes;
    private boolean isLoading;
    private String message;
    private String buttonText;
    private View.OnClickListener buttonClickListener;

    private ProgressBar stProgress;
    private ImageView stImage;
    private TextView stMessage;
    private Button stButton;

    public SimpleStateOptions image(@DrawableRes int val) {
        imageRes = val;
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public SimpleStateOptions loading() {
        isLoading = true;
        return this;
    }

    public SimpleStateOptions message(String val) {
        message = val;
        return this;
    }

    public SimpleStateOptions buttonText(String val) {
        buttonText = val;
        return this;
    }

    public SimpleStateOptions buttonClickListener(View.OnClickListener val) {
        buttonClickListener = val;
        return this;
    }

    int getImageRes() {
        return imageRes;
    }

    boolean isLoading() {
        return isLoading;
    }

    String getMessage() {
        return message;
    }

    String getButtonText() {
        return buttonText;
    }

    View.OnClickListener getClickListener() {
        return buttonClickListener;
    }

    @Override
    protected int layoutId() {
        return R.layout.stf_simple_state;
    }

    @Override
    protected void init(View rootView) {
        stProgress = (ProgressBar) stateView.findViewById(R.id.stProgress);
        stImage = (ImageView) stateView.findViewById(R.id.stImage);
        stMessage = (TextView) stateView.findViewById(R.id.stMessage);
        stButton = (Button) stateView.findViewById(R.id.stButton);

        if (!TextUtils.isEmpty(getMessage())) {
            stMessage.setVisibility(View.VISIBLE);
            stMessage.setText(getMessage());
        } else {
            stMessage.setVisibility(View.GONE);
        }

        if (isLoading()) {
            stProgress.setVisibility(View.VISIBLE);
            stImage.setVisibility(View.GONE);
            stButton.setVisibility(View.GONE);
        } else {
            stProgress.setVisibility(View.GONE);
            if (getImageRes() != 0) {
                stImage.setVisibility(View.VISIBLE);
                stImage.setImageResource(getImageRes());
            } else {
                stImage.setVisibility(View.GONE);
            }

            if (getClickListener() != null) {
                stButton.setVisibility(View.VISIBLE);
                stButton.setOnClickListener(getClickListener());
                if (!TextUtils.isEmpty(getButtonText())) {
                    stButton.setText(getButtonText());
                }
            } else {
                stButton.setVisibility(View.GONE);
            }
        }
    }
}
