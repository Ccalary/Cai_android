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
 * Created by chengyanfang on 2016/11/27.
 */

public class PXFAlterView implements View.OnClickListener{

    Context mContext;

    View mView;

    Dialog alertDialog;

    TextView mTitle;

    TextView mContent;

    TextView mCancleTitle;

    TextView mSureTitle;

    EditText mInputView;

    RelativeLayout mCancleLayout;

    RelativeLayout mSureLayout;

    public PXFAlterView(Context context, String title, String content, final View.OnClickListener sureClickListener){
        mContext = context;

        initUI();

        mTitle.setText(title);

        mContent.setText(content);

        mSureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureClickListener.onClick(v);
                alertDialog.dismiss();
            }
        });

    }

    public PXFAlterView(Context context, String title, final String content, EditContentCallBack editContentCallBack,boolean showEdit){
        mContext = context;

        mEditContentCallBack = editContentCallBack;

        initUI();

        mTitle.setText(title);

        mContent.setText(content);

        mSureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(mInputView.getText().toString())){
                    ToastUtils.showShort(mContext,content);
                    return;
                }

                mEditContentCallBack.onCompleteEdit(mInputView.getText().toString());
                alertDialog.dismiss();
            }
        });

        if (showEdit){
            mContent.setVisibility(View.GONE);
            mInputView.setVisibility(View.VISIBLE);
            mInputView.setHint(content);
        }

    }


    private void initUI(){
        mView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_makecall_layout,null);

        mTitle = (TextView)mView.findViewById(R.id.tv_title);

        mContent = (TextView)mView.findViewById(R.id.tv_content);

        mInputView = (EditText) mView.findViewById(R.id.etTaskTitle);

        mCancleTitle = (TextView)mView.findViewById(R.id.tv_cancle);

        mSureTitle = (TextView)mView.findViewById(R.id.tv_sure);

        mCancleLayout = (RelativeLayout)mView.findViewById(R.id.rlv_cancle_layout);

        mSureLayout = (RelativeLayout)mView.findViewById(R.id.rlv_sure);

        mCancleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void show(){
        alertDialog = new Dialog(mContext, R.style.pxf_dialog_style);
        alertDialog.setContentView(mView, new ViewGroup.LayoutParams(AppUtil.getWidth(mContext)-AppUtil.dip2px(mContext,80), AppUtil.dip2px(mContext,195)));
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    public PXFAlterView setLoggedOutSetting(){
        alertDialog.setCanceledOnTouchOutside(false);
        mCancleLayout.setVisibility(View.GONE);
        return this;
    }

    EditContentCallBack mEditContentCallBack;

    public interface EditContentCallBack{

        void onCompleteEdit(String content);

    }
}
