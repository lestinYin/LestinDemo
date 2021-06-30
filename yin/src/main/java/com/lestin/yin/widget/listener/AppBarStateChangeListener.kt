package com.lestin.yin.widget.listener

import com.google.android.material.appbar.AppBarLayout


/**
 *
 * @ProjectName:    Taurus
 * @Package:        com.lestin.yin.widget.listener
 * @ClassName:      AppBarStateChangeListener
 * @Description:     监听Appbarlayout状态
 * @Author:         lestin.yin
 * @CreateDate:     2019-08-01 17:16
 * @Version:        1.0
 */
open class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener{
    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private var mCurrentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout!!, State.EXPANDED)
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout!!.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED)
            }
            mCurrentState = State.COLLAPSED
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE)
            }
            mCurrentState = State.IDLE
        }
    }

     open fun onStateChanged(appBarLayout: AppBarLayout, state: State) {}

}