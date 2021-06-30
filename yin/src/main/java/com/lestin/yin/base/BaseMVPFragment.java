package com.lestin.yin.base;

import android.os.Bundle;

/**
 * Created by prin on 2016/4/18.
 * <p>
 * 基础 MVP Fragment
 */
public abstract class BaseMVPFragment<ViewLayerType extends IBaseView, PresenterLayerType extends BasePresenter<ViewLayerType>> extends BaseFragment implements IBaseView {

    protected PresenterLayerType mPresenter;
    protected ViewLayerType mViewer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //创建Presenter
        mViewer = createViewer();
        mPresenter = createPresenter();
        if (mPresenter != null && mViewer != null) {
            mPresenter.attachView(mViewer);
        }
    }

    /**
     * 创建 presenter
     */
    public abstract PresenterLayerType createPresenter();

    /**
     * 创建与 presenter 绑定的 view
     */
    public abstract ViewLayerType createViewer();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public int layoutId() {
        return 0;
    }


}
