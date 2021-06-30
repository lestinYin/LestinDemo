package com.lestin.yin.base;

import java.util.List;

/**
 * autour: lestin.yin
 * email:lestin.yin@gmail.com
 * date: 2017/6/23 13:45
 * version:
 * description: 上拉加载下拉刷新返回数据mode
*/
public interface DataMode<T> {

    public boolean isPageLast();

    public List<T> getList();

    public String getResultMessage();
}
