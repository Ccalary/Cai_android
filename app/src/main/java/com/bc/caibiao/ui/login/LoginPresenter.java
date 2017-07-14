package com.bc.caibiao.ui.login;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.bc.caibiao.BuildConfig;
import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.BaseSubscribe;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.BCL;
import com.bc.caibiao.utils.DeviceInfoUtils;
import com.bc.caibiao.utils.SP;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @author wangkai
 * @create 16/9/14 上午10:37
 * @Description :
 */
public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenterImp {
    private int registerMode;

    private Handler mHandler = new Handler();


    public LoginPresenter(Context context) {
        super(context);
    }

    /**
     * 登录,通过LoginView获取手机/密码
     */
    @Override
    public void submit() {
        String mobile;
        String passWord;
        mobile = mView.getLoginName();
        passWord = mView.getPassWord();
        if (TextUtils.isEmpty(mobile) || mobile.length() < 11) {
            mView.showToast(mContext.getString(R.string.toast_input_mobile));
            return;
        }
        if (TextUtils.isEmpty(passWord) || passWord.length() < 6) {
            mView.showToast(mContext.getString(R.string.toast_input_pwd));
            return;
        }
        BCHttpRequest.getMemberInterface().login(mobile, passWord)
                .compose(HttpResponseHelper.<Member>getAllData())
                .subscribe(new ResolveFailSubscribe<Member>(mContext, mContext.getString(R.string.progress_login)) {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        mView.showToast(fieldError.getMessage());
                    }

                    @Override
                    protected void _onNext(Member member) {
                        SP.getInstance().saveString(SPTag.TAG_MEMBER, new Gson().toJson(member));
                        SP.getInstance().saveString(SPTag.TAG_MEMBER_ID, String.valueOf(member.getMemberId()));
                        SP.getInstance().saveString(SPTag.TAG_MEMBER_NAME, String.valueOf(member.getMemberName()));

                        registerJPush();

                        mView.showToast(mContext.getString(R.string.success_login));
                        mView.goToMainActivity();
                    }
                });
    }


    private void loginQQWX(final String oId, final String type, final String name, final String img) {

        BCHttpRequest.getMemberInterface().loginByWeixinOpenId(oId, type, name, img)
                .compose(HttpResponseHelper.<Member>getAllData())
                .subscribe(new ResolveFailSubscribe<Member>(mContext, mContext.getString(R.string.progress_login)) {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        mView.showToast(fieldError.getMessage());
                    }

                    @Override
                    protected void _onNext(Member member) {
                        SP.getInstance().saveString(SPTag.TAG_MEMBER, new Gson().toJson(member));
                        SP.getInstance().saveString(SPTag.TAG_MEMBER_ID, String.valueOf(member.getMemberId()));
                        SP.getInstance().saveString(SPTag.TAG_MEMBER_NAME, String.valueOf(member.getMemberName()));
                        mView.showToast(mContext.getString(R.string.success_login));
                        registerJPush();
                        mView.goToMainActivity();

                        /**
                         * isBandPhone 1 需要绑定
                         *              0 无需绑定
                         */
                        if (member.getIsBandPhone() == 1) {
                            mView.goToBindActivity(TYPE_WX.equals(type) ? 0 : 1, oId, name, img);
                        }

                    }
                });

    }

    @Override
    public void loginWx() {


        Platform WX = ShareSDK.getPlatform(mContext, Wechat.NAME);

        WX.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(final Platform platform, int i, final HashMap<String, Object> hashMap) {
                Log.v("cb", "授权完成" + platform.getDb().exportData());
//                for (Object key : hashMap.keySet()) {
//                    Log.v("cb", (String) key + "/" + hashMap.get(key));
//                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginQQWX(platform.getDb().getUserId(), TYPE_WX, platform.getDb().get("nickname"), platform.getDb().get("icon"));
                    }
                });


            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {
            }
        });


        if (WX.isAuthValid()) {
            WX.removeAccount(true);
        }

        WX.SSOSetting(false);
        WX.showUser(null);


        WX.authorize();

    }

    @Override
    public void loginQQ() {


        Platform qq = ShareSDK.getPlatform(mContext, QQ.NAME);

        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(final Platform platform, int i, final HashMap<String, Object> hashMap) {
                Log.v("cb", "授权完成" + platform.getDb().exportData());
//                for (Object key : hashMap.keySet()) {
//                    Log.v("cb", (String) key + "/" + hashMap.get(key));
//                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginQQWX(platform.getDb().getUserId(), TYPE_QQ, platform.getDb().get("nickname"), platform.getDb().get("icon"));
                    }
                });


            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {
            }
        });


        if (qq.isAuthValid()) {
            qq.removeAccount(true);
        }

        qq.SSOSetting(false);
        qq.showUser(null);


        qq.authorize();
    }

    private void registerJPush(){
        String deviceId = DeviceInfoUtils.getDeviceId(mContext);
        JPushInterface.setAlias(mContext, DeviceInfoUtils.getDeviceId(mContext), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                BCL.e(s);
            }
        });

        BCHttpRequest.getMessageInterface().setMessageDeviceInfo(SP.getInstance().getString(SPTag.TAG_MEMBER_ID), deviceId, 1)
                .compose(HttpResponseHelper.<String>getAllData())
                .subscribe(new BaseSubscribe<String>(mContext) {
                    @Override
                    protected void _onNext(String s) {
                        BCL.e(s);
                    }
                });
    }

}
