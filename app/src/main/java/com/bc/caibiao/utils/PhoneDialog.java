package com.bc.caibiao.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bc.caibiao.R;

import static com.bc.caibiao.R.id.tvConfirm;

/**
 * Created time 2017/4/18
 */
public class PhoneDialog {
    private Context mContext;
    private int layoutId;
    public static AlertDialog mDialog;
    private Window mWindow;
    private onClickCall listener;

    public PhoneDialog(Context mContext, onClickCall listener) {
        this.mContext = mContext;
        this.layoutId = layoutId;
        this.listener = listener;
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        mDialog.setCanceledOnTouchOutside(false);
        mWindow = mDialog.getWindow();
    }

    public void ShowDialog() {
        mWindow.setContentView(R.layout.dialog_phone);
        mWindow.setGravity(Gravity.CENTER);
        TextView tvCancel, tvCall;
        tvCancel = (TextView) mWindow.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(cancelListener);
        tvCall = (TextView) mWindow.findViewById(tvConfirm);
        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onResult();
                }
                mDialog.dismiss();
            }
        });
    }
    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };

    public interface onClickCall {
        void onResult();

    }
}
