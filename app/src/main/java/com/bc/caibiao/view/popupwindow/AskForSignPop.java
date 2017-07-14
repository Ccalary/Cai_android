package com.bc.caibiao.view.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.ToastUtils;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class AskForSignPop {

    Context mContext;
    View mView;
    Dialog alertDialog;

    String mBrandName = "";
    EditText mBrandET,mNameET,mMobileET;

    FreeCheckCallback mFreeCheckCallback;

    public AskForSignPop(Context context, View view,String brandName,FreeCheckCallback freeCheckCallback) {
        mContext = context;
        mView = LayoutInflater.from(mContext).inflate(R.layout.popup_ask_for_brand_layout,null);
        mBrandName = brandName;
        mFreeCheckCallback = freeCheckCallback;
        initUI();
    }


    public AskForSignPop setTitle(String title){
        ((TextView)mView.findViewById(R.id.tv_dialog_title)).setText(title);
        return this;
    }



    private void initUI() {
        mBrandET =(EditText) mView.findViewById(R.id.tv_brand_name);
        mNameET =(EditText) mView.findViewById(R.id.tv_contact_name);
        mMobileET =(EditText) mView.findViewById(R.id.tv_phone);
        mBrandET.setText(mBrandName);

        mView.findViewById(R.id.dialog_title_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mView.findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(mBrandET.getText().toString().trim())){
                    ToastUtils.showShort(mContext,"请输入商标名称");
                    return;
                }

                if(TextUtils.isEmpty(mNameET.getText().toString().trim())){
                    ToastUtils.showShort(mContext,"请输入姓名");
                    return;
                }

                if(TextUtils.isEmpty(mMobileET.getText().toString().trim())){
                    ToastUtils.showShort(mContext,"请输入电话");
                    return;
                }

                mFreeCheckCallback.doCheck(mBrandET.getText().toString().trim(),mNameET.getText().toString().trim(),mMobileET.getText().toString().trim());

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


    public interface FreeCheckCallback{
        void doCheck(String brandName,String contactName,String mobile);
    }




}
