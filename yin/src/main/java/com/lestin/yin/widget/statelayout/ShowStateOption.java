package com.lestin.yin.widget.statelayout;


/**
 * Created by twiceYuan on 2017/4/18.
 *
 * 暂无相关订单
 */

public class ShowStateOption extends BaseStateOptions {
    private String message;
    private int resId;
    public ShowStateOption(String message, int resId) {
        this.message = message;
        this.resId = resId;
    }

    @Override
    protected String message() {
        return message;
    }

    @Override
    protected int imageId() {
        return resId;
    }
}
