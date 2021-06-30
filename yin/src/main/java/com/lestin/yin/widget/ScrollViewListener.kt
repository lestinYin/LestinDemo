package com.future.taurus.widget

/**

 * @Author: Lestin.Yin
 * @CreateDate: 2019/7/23 14:47
 * @UpdateUser: Lestin.Yin
 * @Version: 1.0
 */

class ScrollViewListener {

    interface HeightObserver {
        fun addScrollHeightListener(listener: (Any) -> Unit)
    }

    interface ScrollHeightListener {
        fun onScrollYHeight(scrollY: Int)
    }
}