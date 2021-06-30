package com.lestin.yin.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Author: yml
 * Description:
 * Since: 1.0
 * Date: 2017/5/27 15:26
 */
public class BaseListEntity<DATA extends EBaseData> implements Serializable {
    private final int SUCCESS_CODE = 200;

    @SerializedName("result")
    private String result;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String msg;
    @SerializedName("data")
    public List<DATA> data;

    public boolean isSuccess(){
        if(SUCCESS_CODE == code) {
            return true;
        } else {
            return false;

        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int state) {
        this.code = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DATA> getData() {
        return data;
    }

    public void setData(List<DATA> data) {
        this.data = data;
    }
    public String getReason(){
        if(isSuccess())
            return "";
        return getMsg();
    }

    public List<DATA> getBase(){
        return data;
    }
}
