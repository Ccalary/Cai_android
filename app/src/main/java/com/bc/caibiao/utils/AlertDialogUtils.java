package com.bc.caibiao.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bc.caibiao.R;

/**
 * Created by Harvey
 * Created time 2016/7/5
 */
public class AlertDialogUtils {
    private Context mContext;
    private int layoutId;
    public static AlertDialog mDialog;
    private Window mWindow;
    private onClickResult listener;
    public static final int DIALOG_PHOTO = 1;
    public static final int DIALOG_ALBUM = 2;
    public static final int DIALOG_CANCEL = 3;


    public AlertDialogUtils(Context mContext, onClickResult listener) {
        this.mContext = mContext;
        this.layoutId = layoutId;
        this.listener = listener;
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        mDialog.setCanceledOnTouchOutside(false);
        mWindow = mDialog.getWindow();
    }

    public void ShowPhoto() {
        mWindow.setContentView(R.layout.dialog_take_photo);
        mWindow.setGravity(Gravity.BOTTOM);
        TextView tvCancel, tvPhoto, tvAlbum;
        tvCancel = (TextView) mWindow.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(cancelListener);
        tvPhoto = (TextView) mWindow.findViewById(R.id.tvPhoto);
        tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onResult(DIALOG_PHOTO);
                }
                mDialog.dismiss();
            }
        });
        tvAlbum = (TextView) mWindow.findViewById(R.id.tvAlbum);
        tvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onResult(DIALOG_ALBUM);
                }
                mDialog.dismiss();
            }
        });
    }

//    public void Share() {
//        mWindow.setContentView(R.layout.dialog_share);
//        mWindow.setGravity(Gravity.BOTTOM);
//        LinearLayout llWeChat, llWeChatFriend;
//        TextView tvCancel;
//        tvCancel = (TextView) mWindow.findViewById(R.id.tvCancel);
//        tvCancel.setOnClickListener(cancelListener);
//        llWeChat = (LinearLayout) mWindow.findViewById(R.id.llWeChat);
//        llWeChatFriend = (LinearLayout) mWindow.findViewById(R.id.llWeChatFriend);
//        llWeChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onResult(Wechat.NAME);
//                    mDialog.dismiss();
//                }
//            }
//        });
//        llWeChatFriend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onResult(WechatMoments.NAME);
//                    mDialog.dismiss();
//                }
//            }
//        });
//    }

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };

    public interface onClickResult {
        void onResult(Object result);

    }
}
