package com.bc.caibiao.ui.login;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.bc.caibiao.R;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.Md5Encrypt;
import com.bc.caibiao.utils.SP;
import com.google.gson.Gson;


/**
 * @author wangkai
 * @create 16/9/14 下午2:32
 * @Description :
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.RegisterView> implements RegisterContract.RegisterPresenterImp {
    private String authCode;

    public RegisterPresenter(Context context) {
        super(context);
    }

    /**
     * 登录/注册等提交,通过RegisterView获取手机/密码
     */
    @Override
    public void submit() {
        //判断手机号
        String mobile = mView.getLoginName();
        if (TextUtils.isEmpty(mobile) || mobile.length() < 11) {
            mView.showToast(mContext.getString(R.string.toast_input_mobile));
            return;
        }
        //判断验证码
        String inputAuthCode = mView.getCheckCode();
        if (TextUtils.isEmpty(inputAuthCode)) {
            mView.showToast(mContext.getString(R.string.toast_input_auth_code));
            return;
        }
//        if (inputAuthCode.length() < 5 || !isRightAuthCode()) {
//            mView.showToast(mContext.getString(R.string.toast_input_right_auth_code));
//            return;
//        }
        //判断密码
        String password = mView.getPassWord();
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            mView.showToast(mContext.getString(R.string.toast_input_pwd));
            return;
        }
//        String surePassword = mView.getSurePassword();
//        if (TextUtils.isEmpty(surePassword) || surePassword.length() < 6) {
//            mView.showToast(mContext.getString(R.string.toast_input_sure_pwd));
//            return;
//        }
//        if (!password.equals(surePassword)) {
//            mView.showToast(mContext.getString(R.string.toast_pwd_not_equal));
//            return;
//        }
        if (!mView.isSelectAgreement()) {
            mView.showToast(mContext.getString(R.string.toast_agreement));
            return;
        }

        String name = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);

        BCHttpRequest.getMemberInterface().register(name, mobile, password, inputAuthCode)
                .compose(HttpResponseHelper.<Member>getAllData())
                .subscribe(new ResolveFailSubscribe<Member>(mContext, mContext.getString(R.string.progress_register)) {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        mView.showToast(fieldError.getMessage());
                    }

                    @Override
                    protected void _onNext(Member member) {
                        mView.showToast(mContext.getString(R.string.success_register));
                        saveMember(member);
                        mView.goToMainActivity();
                        mView.delayFinish(200);
                    }
                });
    }

    @Override
    public void loginWx() {

    }

    @Override
    public void loginQQ() {

    }

    /**
     * 保存Member对象
     *
     * @param member 登录后返回的Member对象
     */
    public void saveMember(Member member) {
        SP.getInstance().saveString(SPTag.TAG_MEMBER, new Gson().toJson(member));
        SP.getInstance().saveString(SPTag.TAG_MEMBER_ID, String.valueOf(member.getMemberId()));
        SP.getInstance().saveString(SPTag.TAG_MEMBER_NAME, String.valueOf(member.getMemberName()));
    }

//    /**
//     * 第三方登录
//     * @param platform
//     */
//    @Override
//    public void thirdLogin(Platform platform) {
//
//    }

    /**
     * 获取验证码
     */
    @Override
    public void getCheckCode() {
        String mobile = mView.getLoginName();
        if (TextUtils.isEmpty(mobile) || mobile.length() < 11) {
            mView.showToast(mContext.getString(R.string.toast_input_mobile));
            return;
        }

        BCHttpRequest.getMemberInterface().getPhoneVerify("", mobile)
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
        String input = mView.getCheckCode();
        return Md5Encrypt.stringMD5(input).equals(authCode);
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
