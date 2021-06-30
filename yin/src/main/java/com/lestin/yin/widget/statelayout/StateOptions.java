package com.lestin.yin.widget.statelayout;

import android.view.View;

import androidx.annotation.LayoutRes;

import java.io.Serializable;

/**
 * Created by twiceYuan on 2017/3/6.
 *
 * Common State Options
 */
public abstract class StateOptions implements Serializable {

    View stateView;

    protected abstract @LayoutRes
    int layoutId();

    protected void init(View rootView) {
    }

    public View rootView() {
        return stateView;
    }

    private StatefulLayout mAttachedStf;

    public void setAttachedStf(StatefulLayout attachedStf) {
        mAttachedStf = attachedStf;
    }

    public StatefulLayout getAttachedStf() {
        return mAttachedStf;
    }
}
