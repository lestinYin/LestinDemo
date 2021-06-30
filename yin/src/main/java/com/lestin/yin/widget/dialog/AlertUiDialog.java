package com.lestin.yin.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lestin.yin.R;


/**
 * Created by zhusx on 2015/10/28.
 * 弹各个提示信息，动态button添加，以及提示信息
 */
public class AlertUiDialog extends Dialog implements AlertUiButtonLayout.OnItemClickListener {
    private View mTitleGroup;
    private TextView mTitle;
    private TextView mMessage;
    private AlertUiButtonLayout mButtonLayout;
    private AlertUiButtonLayout.OnItemClickListener mListener;

    public AlertUiDialog(Context context, boolean isClickCancel, String title, String message, String... button) {
        super(context, R.style.alert_ui_dialog_style);
        setContentView(getLayout());
        mTitleGroup = findViewById(R.id.title_group);
        mTitle = (TextView) findViewById(R.id.title);
        mMessage = (TextView) findViewById(R.id.message);
        mButtonLayout = (AlertUiButtonLayout) findViewById(R.id.button_layout);
        setCancelable(isClickCancel);
        setCanceledOnTouchOutside(isClickCancel);

        mTitleGroup.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        mTitle.setText(title);
        mMessage.setVisibility(TextUtils.isEmpty(message) ? View.GONE : View.VISIBLE);
        mMessage.setText(message);
        mButtonLayout.setButton(button);
        mButtonLayout.setListener(this);
        findViewById(R.id.bg).setBackgroundDrawable(mButtonLayout.getWhitShape(5, 0xffffffff, true, true, true, true));
    }

    public int getLayout(){
        return R.layout.alert_ui_dialog_layout;
    }

    public static AlertUiDialog showDialog(Activity act, boolean isClickCancel, String title, String message, String... button) {
        AlertUiDialog dialog = new AlertUiDialog(act, isClickCancel, title, message, button);
        dialog.show();
        return dialog;
    }

    public void setListener(AlertUiButtonLayout.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onItemClick(int position) {
        dismiss();
        if (mListener != null) {
            mListener.onItemClick(position);
        }
    }
}
