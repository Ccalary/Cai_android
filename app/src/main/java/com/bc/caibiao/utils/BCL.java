package com.bc.caibiao.utils;

import com.jiongbull.jlog.JLog;


/**
 * @author wangkai
 * @Description : LogCat工具类, 传入的数据类型任意。
 * create at 2015/10/30 11:27
 */
public class BCL {
    private static final String TAG = "BaiChun";

    public static void e(Object object) {
        if (object == null) {
            return;
        }
        JLog.e(TAG, object + "");
    }

    public static void toJson(String e) {
        if (e == null) {
            return;
        }
        JLog.json(TAG, e);
    }
}
