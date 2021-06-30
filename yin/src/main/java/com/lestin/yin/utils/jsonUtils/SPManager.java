package com.lestin.yin.utils.jsonUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SPManager implements Serializable{

    private Context mCtx;
    private SharedPreferences mSp;

    public SPManager(Context ctx) {
        mCtx = ctx;
//        mSp = mCtx.getSharedPreferences(mCtx.getPackageName(), Context.MODE_PRIVATE);
        mSp = mCtx.getSharedPreferences(mCtx.getPackageName(), Context.MODE_PRIVATE);
    }

    public String get(String key){
        if(mSp == null) return null;
        String str = mSp.getString(key, null);
        return str;
    }
    public boolean getBoolean(String key,boolean defaultValue){
        if(mSp == null) return defaultValue;
        Boolean str = mSp.getBoolean(key, defaultValue);
        return str;
    }

    public boolean saveStr(String key, String data){
        if(mSp != null){
            mSp.edit().putString(key, data).commit();
            return true;
        }
        return false;
    }
    public boolean saveBoolean(String key, boolean data){
        if(mSp != null){
            mSp.edit().putBoolean(key, data).commit();
            return true;
        }
        return false;
    }


    public <T> T get(String key, Class<T> clz){
        T temp = null;
        if(mSp == null) return null;
        String str = mSp.getString(key, null);
        if (!TextUtils.isEmpty(str)) {
            temp = new Gson().fromJson(str, clz);
        }
        return temp;
    }

    public <T> List<T> getList(String key, Class<T[]> clz){
        T[] temp = null;
        if(mSp == null) return null;
        String str = mSp.getString(key, null);
        if (!TextUtils.isEmpty(str)) {
            temp = new Gson().fromJson(str, clz);
        }
        return temp == null ? new ArrayList<T>() : Arrays.asList(temp);
    }

    public <T> T get(String key, TypeToken type){
        T temp = null;
        if(mSp == null) return null;
        String str = mSp.getString(key, null);
        if (!TextUtils.isEmpty(str)) {
            temp = new Gson().fromJson(str, type.getType());
        }
        return temp;
    }

    public <T> boolean save(String key, T data){
        String str = new Gson().toJson(data);
        if(mSp != null){
            mSp.edit().putString(key, str).commit();
            return true;
        }
        return false;
    }

    public <T> boolean save(String key, T[] data){
        String str = new Gson().toJson(data);
        if(mSp != null){
            mSp.edit().putString(key, str).commit();
            return true;
        }
        return false;
    }

    public <T> boolean save(String key, T data, _TypeToken<T> type){
        String str = new Gson().toJson(data, type.getType());
        if(mSp != null){
            mSp.edit().putString(key, str).commit();
            return true;
        }
        return false;
    }

    public void clear(String key) {
        if (mSp != null && mSp.contains(key)) {
            mSp.edit().putString(key, "").commit();
        }
    }

}
