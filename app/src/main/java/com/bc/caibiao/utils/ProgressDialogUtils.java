package com.bc.caibiao.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

/**
 * @author wangkai
 * @create 16/9/8 上午10:48
 * @Description :
 */
public class ProgressDialogUtils {
    private String defaultMsg = "数据加载中...";
    private ProgressDialog mDialog;
    private boolean isCancelable = true;
    private Context context;

    public ProgressDialogUtils(Context context) {
        this.context = context;
    }

    public ProgressDialogUtils(Context context, String tipMsg) {
        this.context = context;
        if (!TextUtils.isEmpty(tipMsg))
            defaultMsg = tipMsg;
    }

    public ProgressDialogUtils(Context context, String tipMsg, boolean isCancelable) {
        this.context = context;
        this.isCancelable = isCancelable;
        if (!TextUtils.isEmpty(tipMsg))
            defaultMsg = tipMsg;
    }


    public void show() {
        mDialog = new ProgressDialog(context);
        mDialog.setMessage(defaultMsg);
        mDialog.setCancelable(isCancelable);
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
