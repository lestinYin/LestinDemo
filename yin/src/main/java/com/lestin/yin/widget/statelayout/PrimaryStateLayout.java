package com.lestin.yin.widget.statelayout;

import android.content.Context;
import android.util.AttributeSet;


/**
 * autour: lestin.yin
 * email:lestin.yin@gmail.com
 * date: 2017/6/26 13:52
 * version:
 * description: 状态布局
*/

public class PrimaryStateLayout extends StatefulLayout {
    public PrimaryStateLayout(Context context) {
        super(context);
        initLayout();
    }

    public PrimaryStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }
    private void initLayout() {
        setAnimationEnabled(true);
    }
}
