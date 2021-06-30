package com.lestin.yin.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lestin.yin.R;


public class DialogUtil {

    public Dialog dialog;
    public Activity activity;
    private CustomDialog mProgressDialog;

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static CustomDialog createLoadingDialog(Context context, String msg) {

        return createLoadingDialog(context, msg, false);
    }

    public static CustomDialog createLoadingDialog(Context context, String msg, boolean cancel) {

        CustomDialog dialog = new DialogUtil().new CustomDialog();

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.pub_loading_dialog, null);// 得到加载view
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字

        if (TextUtils.isEmpty(msg)) {
            tipTextView.setVisibility(View.GONE);
        } else {
            tipTextView.setVisibility(View.VISIBLE);
            tipTextView.setText(msg);
        }

        Dialog loadingDialog = new Dialog(context, R.style.pub_loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(cancel);
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        window.setAttributes(wl);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.setGravity(Gravity.CENTER);
        //window.setGravity(Gravity.TOP);
        loadingDialog.setContentView(v);// 设置布局

        dialog.dialog = loadingDialog;
        dialog.spaceshipImage = spaceshipImage;
        dialog.context = context;
        return dialog;
    }



    public void createDialog(Context mContext, View view) {
        dialog = new Dialog(mContext);
        activity = (Activity) mContext;
        dialog.setContentView(view);
    }

    public void createDialog(Context context, View view, int style, boolean cancel) {
        dialog = new Dialog(context, style);
        dialog.setContentView(view);
        dialog.setCancelable(cancel);
        activity = (Activity) context;
    }

    public void create(Context mContext, View view) {
        activity = (Activity) mContext;
        dialog = new Dialog(mContext, R.style.pub_dialog);
        dialog.setContentView(view);
        dialog.setCancelable(false);
    }

    public void showDialog() {
        dialog.show();
    }

    public void showDialog(Context mContext, View view) {
        createDialog(mContext, view);
        dialog.show();
    }

    public void closeDialog() {
        dialog.dismiss();
    }

    public void createProgressDialog(Activity activity) {
        this.activity = activity;
        mProgressDialog = DialogUtil.createLoadingDialog(activity, "");
    }

    public void showProgressDialog() {
        mProgressDialog.dialog.show();
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                mProgressDialog.context, R.anim.pub_loading_animation);
        // 使用ImageView显示动画
        mProgressDialog.spaceshipImage.startAnimation(hyperspaceJumpAnimation);
    }

    public void closeProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.dialog.isShowing()) {
            mProgressDialog.dialog.dismiss();
        }
    }

    public boolean isDialogShowing() {
        return (mProgressDialog.dialog != null && mProgressDialog.dialog.isShowing());
    }

    public void createProgressDialog(Activity activity, boolean cancel) {
        this.activity = activity;
        mProgressDialog = DialogUtil.createLoadingDialog(activity, "loading...", cancel);
    }

    public class CustomDialog {
        public Dialog dialog;
        public ImageView spaceshipImage;
        public Context context;
    }

//    public static void centerToast(Context context, String message) {
//        Toast toast = new Toast(context);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        TextView textView = new TextView(context);
//        textView.setText(message);
//        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
//        GradientDrawable drawable = new GradientDrawable();
//        drawable.setColor(0x7f000000);
//        drawable.setCornerRadius(ViewUtil.dp2px(2));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            textView.setBackground(drawable);
//        }
//        toast.setView(textView);
//        int marginTopBottom = ViewUtil.dp2px(12);
//        int marginLeftRight = ViewUtil.dp2px(16);
//        textView.setPadding(marginLeftRight, marginTopBottom, marginLeftRight, marginTopBottom);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//    }

}
