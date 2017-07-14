package com.bc.caibiao.request.subscribe;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.utils.ScreenUtils;

/**
 * @author wangkai
 * @Description : 处理需要带ProgressDialog的观察者
 * create at 16/8/15 下午4:59
 */
public abstract class ProgressSubscribe<T> extends BaseSubscribe<T> {
    private String defaultMsg = "加载中...";
    protected Dialog loadingDialog;
    private boolean isShow = true;
    private Context mContext;

    public ProgressSubscribe(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }
    public ProgressSubscribe(Context mContext, String defaultMsg) {
        super(mContext);
        this.mContext = mContext;
        this.defaultMsg = defaultMsg;
    }
    public ProgressSubscribe(Context mContext, boolean show) {
        super(mContext);
        this.mContext = mContext;
        this.isShow = show;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isShow) {
            loadingDialog = createLoadingDialog(mContext,defaultMsg);
            loadingDialog.show();
        }
    }

    @Override
    public void onCompleted() {
        if (loadingDialog != null)
            loadingDialog.dismiss();
        super.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        if (loadingDialog != null)
            loadingDialog.dismiss();
        super.onError(e);
    }

    public static Dialog createLoadingDialog(Context context,String progressTip) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_loading, null);// 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.progressBar);// 加载布局
        TextView tvProgress = (TextView) v.findViewById(R.id.tvProgress);// 提示
        tvProgress.setText(progressTip);

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout);// 设置布局
        Window window = loadingDialog.getWindow();
        window.setLayout(ScreenUtils.dip2px(context, 120), ScreenUtils.dip2px(context, 120));
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.1f;
        window.setAttributes(params);
        return loadingDialog;

    }
}
