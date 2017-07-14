package com.bc.caibiao.view.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.utils.AppUtil;

/**
 * Created by chengyanfang on 2017/4/23.
 */

public class UpdatePopWindow  implements View.OnClickListener {

    Context mContext;

    View mView;

    Dialog alertDialog;

    RelativeLayout mCancleLayout, mMakeCallLayout;

    View.OnClickListener mOnClickListener;

    TextView mContent;

    ScrollView mScrollView;

    String mUpdateTips;

    boolean isMust;

    public UpdatePopWindow(Context context, View view, View.OnClickListener aOnClickListener, String aUpdateTips, String updateMust) {
        mContext = context;
        mUpdateTips = aUpdateTips;
        mOnClickListener = aOnClickListener;
        isMust = "1".equals(updateMust);
        initUI();
    }

    private void initUI() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.pop_version_update_view, null);

        mCancleLayout = (RelativeLayout) mView.findViewById(R.id.layout_cancle);
        mMakeCallLayout = (RelativeLayout) mView.findViewById(R.id.layout_sure);

        mScrollView = (ScrollView) mView.findViewById(R.id.scrollView);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, AppUtil.getHeight(mContext)-AppUtil.dip2px(mContext,480));
        lp.setMargins(0	, AppUtil.dip2px(mContext,50),0,0);
        mScrollView.setLayoutParams(lp);

        mContent = (TextView) mView.findViewById(R.id.tv_tips);
        mUpdateTips = mUpdateTips.replace("\\n", "\n");
        mContent.setText(mUpdateTips);

        mCancleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mMakeCallLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mOnClickListener.onClick(v);
                } catch (Exception e) {
                }
                dismiss();
            }
        });
    }

    public void show() {
        alertDialog = new Dialog(mContext, R.style.pxf_dialog_style);

        alertDialog.setContentView(mView, new ViewGroup.LayoutParams(AppUtil.getWidth(mContext) - AppUtil.dip2px(mContext, 50), ViewGroup.LayoutParams.WRAP_CONTENT));

        alertDialog.getWindow().setGravity(Gravity.CENTER);

        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.qhq_alert_transparent_bg);

        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();

        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (isMust) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void dismiss() {
        if (isMust) {
            return;
        }
        if (alertDialog != null)
            alertDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
