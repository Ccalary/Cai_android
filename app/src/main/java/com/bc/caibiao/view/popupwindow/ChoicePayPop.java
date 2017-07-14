package com.bc.caibiao.view.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.ToastUtils;

/**
 * Created by panghuan on 2017/4/19.
 */

public class ChoicePayPop {
    Context mContext;
    View mView;
    Dialog alertDialog;

    PayTypeCallback mPayTypeCallback;

    public ChoicePayPop(Context context, View view, String brandName, PayTypeCallback payTypeCallback) {
        mContext = context;
        mView = LayoutInflater.from(mContext).inflate(R.layout.choice_pay_layout, null);
        mPayTypeCallback = payTypeCallback;
        initUI();
    }


    public ChoicePayPop setTitle(String title) {
        ((TextView) mView.findViewById(R.id.tv_dialog_title)).setText(title);
        return this;
    }


    private void initUI() {
        mView.findViewById(R.id.dialog_title_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mView.findViewById(R.id.rlv_pay_zfb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayTypeCallback.doPayZFB();
                dismiss();
            }
        });

        mView.findViewById(R.id.rlv_pay_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayTypeCallback.doPayWX();
                dismiss();
            }
        });
    }

    public void show() {
        alertDialog = new Dialog(mContext, R.style.shareDialog1);
        alertDialog.setContentView(mView, new ViewGroup.LayoutParams(AppUtil.getWidth(mContext) - AppUtil.dip2px(mContext, 50), ViewGroup.LayoutParams.WRAP_CONTENT));//LayoutParams，设置弹出层在父类窗口的大小
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public void dismiss() {
        if (alertDialog != null)
            alertDialog.dismiss();
    }

    public interface PayTypeCallback {
        void doPayZFB();

        void doPayWX();
    }


}
