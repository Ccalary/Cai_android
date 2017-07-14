package com.bc.caibiao.ui.login;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.bc.caibiao.R;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.Md5Encrypt;

/**
 * @author wangkai
 * @create 16/9/18 上午9:33
 * @Description :
 */
public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordContract.ForgetPasswordView> implements ForgetPasswordContract.ForgetPasswordPresenterImp {
    private String authCode;

    public ForgetPasswordPresenter(Context context) {
        super(context);
    }

    @Override
    public void submit(int page_type, String id, String name, String img, String type) {


        //判断手机号
        String mobile = mView.getMobile();
        if (TextUtils.isEmpty(mobile) || mobile.length() < 11) {
            mView.showToast(mContext.getString(R.string.toast_input_mobile));
            return;
        }
        //判断验证码
        String inputAuthCode = mView.getAuthCode();
        if (TextUtils.isEmpty(inputAuthCode)) {
            mView.showToast(mContext.getString(R.string.toast_input_auth_code));
            return;
        }

        if (ForgetPasswordActivity.PAGETYPE_BINDPHONE == page_type) {

            BCHttpRequest.getMemberInterface().bindRegistInfo(id, type, name, mobile, inputAuthCode, img)
                    .compose(HttpResponseHelper.<Void>getAllData())
                    .subscribe(new ResolveFailSubscribe<Void>(mContext, mContext.getString(R.string.progress_register)) {
                        @Override
                        protected void _onFail(FieldError fieldError) {
                            mView.showToast(fieldError.getMessage());
                        }

                        @Override
                        protected void _onNext(Void s) {
                            mView.showToast(mContext.getString(R.string.success_register));
                            mView.delayFinish(200);
                        }
                    });

        } else if (ForgetPasswordActivity.PAGETYPE_PSW == page_type) {

            BCHttpRequest.getMemberInterface().resetPassword(mobile, inputAuthCode)
                    .compose(HttpResponseHelper.<Void>getAllData())
                    .subscribe(new ResolveFailSubscribe<Void>(mContext, mContext.getString(R.string.progress_reset_pwd)) {
                        @Override
                        protected void _onFail(FieldError fieldError) {
                            mView.showToast(fieldError.getMessage());
                        }

                        @Override
                        protected void _onNext(Void s) {
                            mView.showToast(mContext.getString(R.string.success_change_psw));
                            mView.delayFinish(200);
                        }
                    });


        }


    }

    /**
     * 获取验证码
     */
    @Override
    public void getCheckCode() {
        String mobile = mView.getMobile();
        if (TextUtils.isEmpty(mobile) || mobile.length() < 11) {
            mView.showToast(mContext.getString(R.string.toast_input_mobile));
            return;
        }
//
        BCHttpRequest.getMemberInterface().getPhoneVerify("edit", mobile)
                .compose(HttpResponseHelper.<Void>getAllData())
                .subscribe(new ResolveFailSubscribe<Void>(mContext, mContext.getString(R.string.progress_get_auth_code)) {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        mView.showToast(fieldError.getMessage());
                        mView.changeCheckCodeText(mContext.getString(R.string.toast_resend));
                        mView.setCheckCodeCanClickAble(true);
                    }

                    @Override
                    protected void _onNext(Void s) {
                        mView.showToast(mContext.getString(R.string.success_get_auth_code));
//                        authCode = s;
                        mView.setCheckCodeCanClickAble(false);
                        startTimerTask();
                    }
                });
    }

    private void startTimerTask() {
        if (task != null) {
            task.cancelTimer();
        }
        task = new CountDownThread(handler, 60);
        task.startTimerTask();
    }

    @SuppressWarnings("ConstantConditions")
    private boolean isRightAuthCode() {
        String input = mView.getAuthCode();
        return true;
//        return Md5Encrypt.stringMD5(input).equals(authCode);
    }

    private CountDownThread task;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 > 0) {
                mView.changeCheckCodeText(String.format(mContext.getString(R.string.format_count_down), msg.arg1));
            } else {
                mView.changeCheckCodeText(mContext.getString(R.string.toast_resend));
                mView.setCheckCodeCanClickAble(true);
            }

        }
    };


    @Override
    public void destroy() {
        super.destroy();
        if (task != null)
            task.cancelTimer();
    }
}
