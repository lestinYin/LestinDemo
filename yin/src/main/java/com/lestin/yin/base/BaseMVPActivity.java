package com.lestin.yin.base;

import android.os.Bundle;

/**
 * Created by prin on 2016/4/6.
 * MVP架构的Activity基类需要进行presenter与view绑定
 */
public abstract class BaseMVPActivity<ViewLayerType extends IBaseView, PresenterLayType extends BasePresenter<ViewLayerType>> extends BaseActivity implements IBaseView {

    protected PresenterLayType mPresenter;
    protected ViewLayerType mViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建presenter
        mViewer = createViewer();
        mPresenter = createPresenter();
        if (mPresenter != null && mViewer != null) {
            mPresenter.attachView(mViewer);
        }
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            //将业务处理与view层解绑
            mPresenter.detachView();
        }
    }

    /**
     * 创建presenter
     */
    public abstract PresenterLayType createPresenter();

    /**
     * 创建与presenter绑定的view
     */
    public abstract ViewLayerType createViewer();
    /**
     * 创建与presenter绑定的view
     */
    public abstract void getData();
}
