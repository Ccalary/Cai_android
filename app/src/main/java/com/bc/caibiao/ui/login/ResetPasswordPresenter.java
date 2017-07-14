package com.bc.caibiao.ui.login;

import android.content.Context;
import android.text.TextUtils;

import com.bc.caibiao.R;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BasePresenter;

/**
 * @author wangkai
 * @create 16/9/18 上午10:04
 * @Description :
 */
public class ResetPasswordPresenter extends BasePresenter<ResetPasswordContract.ResetPasswordView> implements ResetPasswordContract.ResetPasswordPresenterImp {
    public ResetPasswordPresenter(Context context) {
        super(context);
    }

    /**
     * 提交修改密码
     */
    @Override
    public void commit() {
        //判断手机号
        String mobile = mView.getMobile();
        if (TextUtils.isEmpty(mobile) || mobile.length() < 11) {
            mView.showToast(mContext.getString(R.string.toast_input_mobile));
            return;
        }
        //判断密码
        String password = mView.getPassword();
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            mView.showToast(mContext.getString(R.string.toast_input_pwd));
            return;
        }
        String surePassword = mView.getSurePassword();
        if (TextUtils.isEmpty(surePassword) || surePassword.length() < 6) {
            mView.showToast(mContext.getString(R.string.toast_input_sure_pwd));
            return;
        }
        if (!password.equals(surePassword)) {
            mView.showToast(mContext.getString(R.string.toast_pwd_not_equal));
            return;
        }
//        BCHttpRequest.getMemberInterface().resetMemberPwd(mobile, password)
//                .compose(HttpResponseHelper.<String>getAllData())
//                .subscribe(new ResolveFailSubscribe<String>(mContext, mContext.getString(R.string.progress_reset_pwd)) {
//                    @Override
//                    protected void _onNext(String o) {
//                        mView.showToast(mContext.getString(R.string.reset_success));
//                        mView.delayFinish(1000);
//                    }
//
//                    @Override
//                    protected void _onFail(FieldError fieldError) {
//                        mView.showToast(fieldError.getMessage());
//                    }
//                });
    }
}
