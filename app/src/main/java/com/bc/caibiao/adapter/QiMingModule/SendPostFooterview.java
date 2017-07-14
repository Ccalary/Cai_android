package com.bc.caibiao.adapter.QiMingModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;

/**
 * Created by chengyanfang on 2017/4/22.
 */

public class SendPostFooterview {

    Context mContext;
    View mView;

    public SendPostFooterview(Context aContext, View.OnClickListener onClickListener) {
        this.mContext = aContext;
        getView();

        mView.findViewById(R.id.publich).setOnClickListener(onClickListener);
    }

    public SendPostFooterview(Context aContext, View.OnClickListener onClickListener,String title) {
        this.mContext = aContext;
        getView();

        mView.findViewById(R.id.publich).setOnClickListener(onClickListener);

        ((TextView)mView.findViewById(R.id.publich)).setText(title);
    }

    public View getView() {
        if (mView == null) {
            LayoutInflater aLayoutInflater = LayoutInflater.from(mContext);
            this.mView = aLayoutInflater.inflate(R.layout.footerview_taskdetail_view, null);
        }
        initUI();
        return mView;
    }


    public void setTitle(String title) {
        ((TextView)mView.findViewById(R.id.publich)).setText(title);
    }

    private void initUI(){

    }
}
