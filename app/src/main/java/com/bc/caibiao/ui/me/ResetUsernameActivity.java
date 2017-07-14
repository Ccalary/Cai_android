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
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created  by  panghuan on 2017/4/22.
 * 修改名字
 */

public class ResetUsernameActivity extends BaseActivity {
    @Bind(R.id.tvRight)
    TextView mTvRight;

    @Bind(R.id.new_username)
    EditText mModifyUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_username);

        mTvRight = (TextView) findViewById(R.id.tvRight);
        mTvRight.setOnClickListener(this);

        mModifyUsername = (EditText) findViewById(R.id.new_username);
        String username = getIntent().getExtras().getString("USERNAME");
        mModifyUsername.setText(username);
    }

    //修改名字
    private void modifyUserNameRequest() {
        final Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("memberId", RequestBody.create(MediaType.parse("text/plain"), SP.getInstance().getString(SPTag.TAG_MEMBER_ID)));
        bodyMap.put("firstname", RequestBody.create(MediaType.parse("text/plain"), mModifyUsername.getText().toString().trim()));

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
                            ToastUtils.showShort(ResetUsernameActivity.this, "修改成功");
                            SP.getInstance().saveString(SPTag.TAG_MEMBER_NAME, mModifyUsername.getText().toString().trim());
                            finish();
                        } else {
                            ToastUtils.showShort(ResetUsernameActivity.this, result.fieldErrors.get(0).getMessage());
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRight:
                if (TextUtils.isEmpty(mModifyUsername.getText().toString().trim())) {
                    ToastUtils.showShort(this, "名字不能为空");
                    return;
                }

                modifyUserNameRequest();
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
