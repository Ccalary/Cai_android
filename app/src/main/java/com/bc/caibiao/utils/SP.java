package com.bc.caibiao.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.Member;
import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * @author wangkai
 * @Description: 存取Json缓存
 * create at 2015/11/3 16:20
 */
public class SP {
    private SharedPreferences sharedPreferences;
    private static SP instance = null;

    public static SP getInstance() {
        if (instance == null) {
            synchronized (SP.class) {
                if (instance == null) {
                    instance = new SP();
                }
            }
        }
        return instance;
    }

    /**
     * 通过TAG取缓存对象
     */
    public String getString(String TAG) {
        sharedPreferences = BaseApplication.getSharedPreferences();
        try {
            return sharedPreferences.getString(TAG, null);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveString(String TAG, String s) {
        sharedPreferences = BaseApplication.getSharedPreferences();
        sharedPreferences.edit().putString(TAG, s).apply();
    }

    public Member getMemberSP() throws Exception {
        sharedPreferences = BaseApplication.getSharedPreferences();
        String temp = sharedPreferences.getString(SPTag.TAG_MEMBER, null);
        if (temp != null) {
            try {
                if (TextUtils.isEmpty(temp)) {
                    throw new NullPointerException();
                }
                return new Gson().fromJson(temp, Member.class);
            } catch (Exception e) {
                throw new NullPointerException();
            }
        } else {
            throw new NullPointerException();
        }
    }

    public void clearAll() {
        sharedPreferences = BaseApplication.getSharedPreferences();
        sharedPreferences.edit().clear().apply();
    }

}