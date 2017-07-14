package com.bc.caibiao.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author wangkai
 * @create 16/8/16 下午2:08
 * @Description :
 */
public class ToastUtils {
    public static void showLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
