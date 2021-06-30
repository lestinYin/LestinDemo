package com.lestin.yin.base;

import java.util.List;

/**
 * 作者：Lestin.yin on 2017/6/29 15:13
 * 邮箱：lestin.yin@gmail.com
 * Description:
 */

public interface AdapterInterface<T> {

    public void setData(List<T> data);

    public void addData(List<T> data);

    public void noData();
}
