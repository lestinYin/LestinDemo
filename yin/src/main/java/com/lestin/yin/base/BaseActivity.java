package com.lestin.yin.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.lestin.yin.BuildConfig;
import com.lestin.yin.R;
import com.lestin.yin.entity.EUser;
import com.lestin.yin.network.ApiManager;
import com.lestin.yin.network.BaseApi;
import com.lestin.yin.network.RequestHandler;
import com.lestin.yin.utils.DialogUtil;
import com.lestin.yin.utils.FunctionUtil;
import com.lestin.yin.utils.RxUtil;
import com.lestin.yin.utils.ToastUtils;
import com.lestin.yin.utils.ViewUtil;
import com.lestin.yin.utils.jsonUtils.SPManager;
import com.lestin.yin.widget.EmptyView;
import com.lestin.yin.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static android.view.View.*;


/**
 * Created by prin on 2015/11/9.
 * Activity的基类
 * (1)对于一些登录信息的监听
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String ACTION_FINISH = BuildConfig.APPLICATION_ID + ".intent.action.FINISH";
    public Activity mContext;
    private EmptyView mLoadingView;
    private TitleBar mTitleBar;
    private BroadcastReceiver mExitReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_FINISH)) {
                finish();
            }
        }
    };
    private DialogUtil mDialogUtil;
    private View mHeaderContainer;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//    protected Unbinder mBinder;
    public SPManager spManager;
    public EUser user;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

//        mBinder = ButterKnife.bind(this);

        if (needTranslucentStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        mContext = this;
        // 设置屏幕不可旋转
//        if (isScreenOrientationPortrait()) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }

        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
        }
        IntentFilter exitFilter = new IntentFilter();
        exitFilter.addAction(ACTION_FINISH);
        registerReceiver(mExitReceiver, exitFilter);

        spManager = new SPManager(getApplicationContext());
        user = spManager.get(com.lestin.yin.Constants.Companion.getUSER_INFO(),EUser.class);
//

        mLoadingView = (EmptyView) this.findViewById(R.id.loadingView);
        initTitle();
        initView();
        initData();

    }

    /**
     * 设置布局文件
     *
     * @return LayoutRes
     */
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 是否需要透明状态栏
     */
    protected boolean needTranslucentStatusBar() {
        return true;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        // Activity 中，约定标题栏默认显示 Activity 清单文件中的 label，并且返回按钮操作为关闭界面
        View view = findViewById(R.id.title);
        if (view != null && view instanceof TitleBar) {
            mTitleBar = (TitleBar) view;
            if (getTitle() != null) {
                mTitleBar.setTitleText(getTitle());
                mTitleBar.setBackClickListener(v -> onBackPressed());
            }
        }
//        mHeaderContainer = findViewById(R.id.header_container);
    }

//    protected int provideHeaderViewId() {
//        return R.id.header_container;
//    }


    public View getHeaderContainer() {
        return mHeaderContainer;
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mTitleBar != null) {
            mTitleBar.setTitleText(title);
        } else {
            super.setTitle(title);
        }
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }

    public void setStatusBarTextColor() {
//        if (StatusBarUtils.AvailableRomType.NA == StatusBarUtils.getLightStatausBarAvailableRomType()) {
//            addStatusBarView();
//        } else {
            this.getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
    }

    /**
     * 设置一个可以自定义颜色的状态栏
     */

    public void addStatusBarView(int color) {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(color));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewUtil.Companion.getStatusBarHeightPx(this));

        ViewGroup decorView = (ViewGroup) findViewById(android.R.id.content);

        decorView.addView(view, params);
    }

    public boolean isScreenOrientationPortrait() {
        return true;
    }

    protected void initViews() {
        // loading view

    }

    protected void initTitle() {
    }

    protected void showLoadEmptyView(String msg) {
        if (mLoadingView != null) {
            mLoadingView.showEmptyResultView(msg);
        }
    }

    protected void showLoadFailView(String msg) {
        if (mLoadingView != null) {
            if (TextUtils.isEmpty(msg)) {
                mLoadingView.showFailResultView();
            } else {
                mLoadingView.showFailResultView(msg);
            }
        }
    }

    protected void hideLoadingView() {
        if (mLoadingView != null) {
            mLoadingView.hideView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        if (mExitReceiver != null) {
            unregisterReceiver(mExitReceiver);
        }
//        if (mBinder != null) {
//            mBinder.unbind();
//        }
        compositeDisposable.dispose();
    }

    //在同一线程进行ui更新，不能执行耗时任务
    public void onEvent(Object params) {

    }

    //在任何线程中执行的事件，都会在ui线程进行更新  适用于耗时任务
    public void onEventMainThread(Object params) {

    }

    public boolean checkNetOk() {
        if (!FunctionUtil.isNetworkAvailable(this)) {
            ToastUtils.showShort(getString(R.string.network_exception));
            return false;
        }
        return true;
    }


    public boolean isNeedHideKeyboardOnTouchSpace() {
        return false;
    }

    /**
     * 点击空白区域，隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Bugtags.onDispatchTouchEvent(this, ev);
        if (!isNeedHideKeyboardOnTouchSpace() && ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (FunctionUtil.isShouldHideKeyboard(v, ev)) {
                FunctionUtil.forceHideInputMethod(getCurrentFocus(), mContext);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    DialogUtil getProgressDialog() {
        if (mDialogUtil == null) {
            mDialogUtil = new DialogUtil();
            mDialogUtil.createProgressDialog(this);
        }
        return mDialogUtil;
    }

    protected void showProgressDialog() {
        if (isShowRequestProgress()) {
            mDialogUtil = getProgressDialog();
            if (mDialogUtil != null) {
                mDialogUtil.showProgressDialog();
            }
        }
    }
   protected boolean isShowProgressDialog() {
       boolean showing = false;
        if (mDialogUtil != null) {
            showing = mDialogUtil.isDialogShowing();
        }
       return showing;
   }

    protected void closeProgressDialog() {
        if (isShowRequestProgress()) {
            if (mDialogUtil != null) {
                mDialogUtil.closeProgressDialog();
            }
        }
    }

    /*
     * Retrofit & RxJava
     */

    /**
     * 是否显示请求的加载对话框，默认显示。即在任何有网络请求的地方，在请求发送之前显示正在加载，并在加载完成后隐藏
     */
    protected boolean isShowRequestProgress() {
        return true;
    }

    public <T> void exec(Observable<T> request, Consumer<T> resultCallback) {
        exec(request, resultCallback, RequestHandler::handleError);
    }

    /**
     * 默认的请求处理拦截器优先，同时在没有处理错误时交给自己的错误处理器处理错误
     * <p>
     * Run a common retrofit request
     */
    public <T> void exec(Observable<T> request, Consumer<T> resultCallback, Consumer<Throwable> errorCallback) {
//        showLoadingDialog();
        compositeDisposable.add(RxUtil.network(request).subscribe(t -> {
//            hideLoadingDialog();
            resultCallback.accept(t);
        }, throwable -> {
//          hideLoadingDialog();
            closeProgressDialog();
            errorCallback.accept(throwable);
            if (RequestHandler.handleError(throwable)) {
                return;
            }
        }));
    }


    public BaseApi baseApi() {
        return ApiManager.getBaseApi();
    }



}
