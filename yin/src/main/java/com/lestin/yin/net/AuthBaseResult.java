package com.lestin.yin.net;

import com.google.gson.JsonElement;

/**
 * Created by twiceYuan on 01/12/2016.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 *
 * Crm 基础结果
 */
public class AuthBaseResult {
    public String error;
    public int code;
    public JsonElement data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
