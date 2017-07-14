package com.bc.caibiao.ui.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.ProgressDialogUtils;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created  on 2017/4/19.
 * 修改密码
 */

public class ChangePwdActivity extends BaseActivity {
    /**
     * UI元素
     */
    private EditText mNewPwd;//新密码
    private EditText mReplyPwd;//重复密码
    private TextView mSubmit;//修改

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_pwd);
        mNewPwd = (EditText) findViewById(R.id.new_password);
        mReplyPwd = (EditText) findViewById(R.id.reply_password);
        mSubmit = (TextView) findViewById(R.id.tv_submit);
        mSubmit.setOnClickListener(this);
    }

    //修改密码
    private void modifyPasswordRequest() {
        final Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("memberId", RequestBody.create(MediaType.parse("text/plain"), SP.getInstance().getString(SPTag.TAG_MEMBER_ID)));
        bodyMap.put("firstname", RequestBody.create(MediaType.parse("text/plain"), SP.getInstance().getString(SPTag.TAG_MEMBER_NAME)));
        bodyMap.put("password", RequestBody.create(MediaType.parse("text/plain"), mNewPwd.getText().toString().trim()));

        BCHttpRequest.getMemberInterface().modifyUserBaseInfo(bodyMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BlankModel>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onNext(BlankModel result) {
                        dismissProgressHUD();
                        if (result.fieldErrors.isEmpty()) {
                            ToastUtils.showShort(ChangePwdActivity.this, "修改成功");
                            JPushInterface.setAlias(mContext, null, null);
                            finish();
                        } else {
                            ToastUtils.showShort(ChangePwdActivity.this, result.fieldErrors.get(0).getMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:

                if (TextUtils.isEmpty(mNewPwd.getText().toString().trim())) {
                    ToastUtils.showShort(this, "新密码不能为空");
                    return;
                }

                if (TextUtils.isEmpty(mReplyPwd.getText().toString().trim())) {
                    ToastUtils.showShort(this, "重复密码不能为空");
                    return;
                }

                //判断密码是否一致
                if (!mNewPwd.getText().toString().equals(mReplyPwd.getText().toString())) {
                    ToastUtils.showShort(ChangePwdActivity.this, "密码不一致");
                    return;
                }

                modifyPasswordRequest();
                break;
        }
    }

    /**
     * 统一实现progress
     *
     * @param tipMsg 提示文字
     */
    @Override
    public void showLoading(String tipMsg) {
        if (mDialog == null) {
            mDialog = new ProgressDialogUtils(mContext, tipMsg);
        }
        mDialog.show();
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
