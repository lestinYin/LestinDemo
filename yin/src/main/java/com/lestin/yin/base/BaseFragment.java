package com.lestin.yin.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.future.taurus.api.model.MainModel;
import com.gyf.barlibrary.ImmersionBar;
import com.lestin.yin.MyApplication;
import com.lestin.yin.R;
import com.lestin.yin.entity.EUser;
import com.lestin.yin.utils.DialogUtil;
import com.lestin.yin.utils.jsonUtils.SPManager;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
/**
 * @ClassName: BaseFragment
 * @Description: fragment基类
 * @Author: Lestin.Yin
 * @CreateDate: 2019/7/23 14:13
 */


public abstract class BaseFragment extends Fragment {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private DialogUtil mDialogUtil = null;

    public SPManager spManager = new SPManager(MyApplication.getContext());
    public EUser mUser = null;

    public MainModel mainModel = new MainModel();
    /**
     * 多种状态的 View 的切换
     */
//    protected var mLayoutStatusView: MultipleStatusView? = null
    public ImmersionBar mImmersionBar = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUser = spManager.get("user_info", EUser.class);
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
        }
        initView();
        initData();
        start();
        initListener();
    }


    private void initListener() {
//        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

//    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
//        start()
//    }


    /**
     * 加载布局
     */
    public abstract int layoutId();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化 View
     */
    public abstract void initView();

    /**
     * 开始请求
     */
    public abstract void start();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }

        if (mImmersionBar != null) {
            mImmersionBar.destroy(); //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        }

    }


    public void addSubscription(Disposable disposable) {
        compositeDisposable.add(disposable);
    }


    protected void showProgressDialog() {
        if (isShowRequestProgress()) {
            mDialogUtil = getProgressDialog();
            if (mDialogUtil != null) {
                mDialogUtil.showProgressDialog();
            }
        }
    }

    protected Boolean isShowProgressDialog() {
        Boolean showing = false;
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

    protected DialogUtil getProgressDialog() {
        if (mDialogUtil == null) {
            mDialogUtil = new DialogUtil();
            mDialogUtil.createProgressDialog(getActivity());
        }
        return mDialogUtil;
    }

    /**
     * 是否显示请求的加载对话框，默认显示。即在任何有网络请求的地方，在请求发送之前显示正在加载，并在加载完成后隐藏
     */
    protected Boolean isShowRequestProgress() {
        return true;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(getActivity());
        mImmersionBar.statusBarColor(R.color.colorPrimary);
        mImmersionBar.init();
    }
}
