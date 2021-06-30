package com.lestin.yin.widget.statelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.AnimRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.lestin.yin.R;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Android layout to show most common state templates like loading, empty, error etc. To do that all you need to is
 * wrap the target area(view) with StatefulLayout. For more information about usage look
 * <a href="https://github.com/gturedi/StatefulLayout#usage">here</a>
 */
public class StatefulLayout extends LinearLayout {

    private static final String        MSG_ONE_CHILD        = "StatefulLayout must have one child!";
    private static final boolean       DEFAULT_ANIM_ENABLED = true;
    private static final int           DEFAULT_IN_ANIM      = android.R.anim.fade_in;
    private static final int           DEFAULT_OUT_ANIM     = android.R.anim.fade_out;
    private final AtomicBoolean mContentIn           = new AtomicBoolean(false);
    private final Object       mOptionsLock    = new Object();
    private          boolean     animationEnabled;
    @AnimRes private int         inAnimation;
    @AnimRes private int         outAnimation;
    private          View        content;
    private          FrameLayout stContainer;
    private StateOptions mCurrentOptions = null;

    public StatefulLayout(Context context) {
        this(context, null);
    }

    public StatefulLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.stfStatefulLayout, 0, 0);
        animationEnabled = array.getBoolean(R.styleable.stfStatefulLayout_stfAnimationEnabled, DEFAULT_ANIM_ENABLED);
        inAnimation = array.getResourceId(R.styleable.stfStatefulLayout_stfInAnimation, DEFAULT_IN_ANIM);
        outAnimation = array.getResourceId(R.styleable.stfStatefulLayout_stfOutAnimation, DEFAULT_OUT_ANIM);
        array.recycle();
    }

    public boolean isAnimationEnabled() {
        return animationEnabled;
    }

    @SuppressWarnings("unused")
    public void setAnimationEnabled(boolean animationEnabled) {
        this.animationEnabled = animationEnabled;
    }

    @SuppressWarnings("unused")
    @AnimRes
    public int getInAnimation() {
        return inAnimation;
    }

    // content //

    @SuppressWarnings("unused")
    public void setInAnimation(@AnimRes int anim) {
        inAnimation = anim;
    }

    @SuppressWarnings("unused")
    @AnimRes
    public int getOutAnimation() {
        return outAnimation;
    }

    @SuppressWarnings("unused")
    public void setOutAnimation(@AnimRes int anim) {
        outAnimation = anim;
    }

    // loading //

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 1) {
            throw new IllegalStateException(MSG_ONE_CHILD);
        }
        if (isInEditMode()) {
            return; // hide state views in designer
        }
        setOrientation(VERTICAL);
        content = getChildAt(0); // assume first child as content
        LayoutInflater.from(getContext()).inflate(R.layout.stf_template, this, true);
        stContainer = (FrameLayout) findViewById(R.id.stContainer);
    }

    public void showContent() {
        if (isAnimationEnabled()) {
            if (stContainer.getVisibility() == VISIBLE) {
                synchronized (mContentIn) {
                    Animation outAnim = createOutAnimation();
                    if (!mContentIn.get()) {
                        outAnim.setAnimationListener(new StateAnimationListener() {
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                stContainer.setVisibility(GONE);
                                content.setVisibility(VISIBLE);
                                content.startAnimation(createInAnimation());
                                mContentIn.set(false);
                            }
                        });
                        stContainer.startAnimation(outAnim);
                        mContentIn.set(true);
                    }
                }
            }
        } else {
            stContainer.setVisibility(GONE);
            content.setVisibility(VISIBLE);
        }
    }

    // empty //

    public void showLoading() {
        showLoading(R.string.stfLoadingMessage);
    }

    public void showLoading(@StringRes int resId) {
        showLoading(str(resId));
    }

    public void showLoading(String message) {
        showCustom(new SimpleStateOptions()
                .message(message)
                .loading());
    }

    // error //

    public void showEmpty() {
        showEmpty(R.string.stfEmptyMessage);
    }

    public void showEmpty(@StringRes int resId) {
        showEmpty(str(resId));
    }

    public void showEmpty(String message) {
        showCustom(new SimpleStateOptions()
                .message(message)
                .image(android.R.drawable.arrow_down_float));
    }

    // offline

    public void showError(OnClickListener clickListener) {
        showError(R.string.stfErrorMessage, clickListener);
    }

    public void showError(@StringRes int resId, OnClickListener clickListener) {
        showError(str(resId), clickListener);
    }

    public void showError(String message, OnClickListener clickListener) {
        showCustom(new SimpleStateOptions()
                .message(message)
                .image(android.R.drawable.arrow_down_float)
                .buttonText(str(R.string.stfButtonText))
                .buttonClickListener(clickListener));
    }

    // location off //

    public void showOffline(OnClickListener clickListener) {
        showOffline(R.string.stfOfflineMessage, clickListener);
    }

    public void showOffline(@StringRes int resId, OnClickListener clickListener) {
        showOffline(str(resId), clickListener);
    }

    public void showOffline(String message, OnClickListener clickListener) {
        showCustom(new SimpleStateOptions()
                .message(message)
                .image(android.R.drawable.arrow_down_float)
                .buttonText(str(R.string.stfButtonText))
                .buttonClickListener(clickListener));
    }

    // custom //

    public void showLocationOff(OnClickListener clickListener) {
        showLocationOff(R.string.stfLocationOffMessage, clickListener);
    }

    public void showLocationOff(@StringRes int resId, OnClickListener clickListener) {
        showLocationOff(str(resId), clickListener);
    }

    public void showLocationOff(String message, OnClickListener clickListener) {
        showCustom(new SimpleStateOptions()
                .message(message)
                .image(android.R.drawable.arrow_down_float)
                .buttonText(str(R.string.stfButtonText))
                .buttonClickListener(clickListener));
    }

    /**
     * Shows custom state for given options. If you do not set buttonClickListener, the button will not be shown
     *
     * @param options customization options
     * @see SimpleStateOptions
     */
    // helper methods //
    public void showCustom(final StateOptions options) {

        synchronized (mOptionsLock) {
            if (mCurrentOptions == options) {
                return;
            }
            mCurrentOptions = options;
        }

        stContainer.clearAnimation();
        content.clearAnimation();

        stContainer.post(new Runnable() {
            @Override
            public void run() {
                if (isAnimationEnabled()) {
                    if (stContainer.getVisibility() == GONE) {
                        Animation outAnim = createOutAnimation();
                        outAnim.setAnimationListener(new StateAnimationListener() {
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                content.setVisibility(GONE);
                                stContainer.setVisibility(VISIBLE);
                                stContainer.startAnimation(createInAnimation());
                            }
                        });
                        content.startAnimation(outAnim);
                        refreshContainer(options);
                    } else {
                        Animation outAnim = createOutAnimation();
                        outAnim.setAnimationListener(new StateAnimationListener() {
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                refreshContainer(options);
                                stContainer.startAnimation(createInAnimation());
                            }
                        });
                        stContainer.startAnimation(outAnim);
                    }
                } else {
                    content.setVisibility(GONE);
                    stContainer.setVisibility(VISIBLE);
                    refreshContainer(options);
                }
            }
        });
    }

    private void refreshContainer(StateOptions options) {
        stContainer.removeAllViews();
        if (options.stateView == null) {
            options.stateView = LayoutInflater.from(getContext()).inflate(options.layoutId(), null, false);
            stContainer.addView(options.stateView);
            options.setAttachedStf(this);
            options.init(options.stateView);
        } else {
            View stateView = options.stateView;
            ViewGroup parent = (ViewGroup) stateView.getParent();
            if (parent == null) {
                stContainer.addView(options.stateView);
            }
            options.init(stateView);
        }
    }

    private String str(@StringRes int resId) {
        return getContext().getString(resId);
    }

    private Animation createInAnimation() {
        return AnimationUtils.loadAnimation(getContext(), inAnimation);
    }

    private Animation createOutAnimation() {
        return AnimationUtils.loadAnimation(getContext(), outAnimation);
    }
}
