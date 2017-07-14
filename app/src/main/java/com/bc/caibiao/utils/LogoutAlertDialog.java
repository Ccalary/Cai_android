package com.bc.caibiao.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bc.caibiao.R;

/**
 * Created time 2017/4/18
 */
public class LogoutAlertDialog {
    private Context mContext;
    private int layoutId;
    public static AlertDialog mDialog;
    private Window mWindow;
    private onClickConfirm listener;

    public LogoutAlertDialog(Context mContext, onClickConfirm listener) {
        this.mContext = mContext;
        this.layoutId = layoutId;
        this.listener = listener;
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        mDialog.setCanceledOnTouchOutside(false);
        mWindow = mDialog.getWindow();
    }

    public void ShowDialog() {
        mWindow.setContentView(R.layout.dialog_custom);
        mWindow.setGravity(Gravity.CENTER);
        TextView tvCancel, tvConfirm, tvAlbum;
        tvCancel = (TextView) mWindow.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(cancelListener);
        tvConfirm = (TextView) mWindow.findViewById(R.id.tvConfirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
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

    public interface onClickConfirm {
        void onResult();

    }
}
