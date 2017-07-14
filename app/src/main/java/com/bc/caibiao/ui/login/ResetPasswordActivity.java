package com.bc.caibiao.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.view.EditTextLayout;

/**
 * @author wangkai
 * @create 16/9/18 上午10:03
 * @Description :
 */
public class ResetPasswordActivity extends BaseActivity<ResetPasswordContract.ResetPasswordView, ResetPasswordPresenter> implements ResetPasswordContract.ResetPasswordView {
    public static final String MOBILE = "mobile";
    private EditText etPassword;
    private EditText etSurePassword;
    private android.widget.TextView btnFinish;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initIntent();
        this.btnFinish = (TextView) findViewById(R.id.btnFinish);
        this.etSurePassword = (EditText) findViewById(R.id.etSurePassword);
        this.etPassword = (EditText) findViewById(R.id.etPassword);
        this.btnFinish.setOnClickListener(this);
    }

    private void initIntent() {
        if (getIntent() != null) {
            mobile = getIntent().getStringExtra(MOBILE);
        } else {
            showToast("重设密码错误");
        }
    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btnFinish:
                mPresenter.commit();
                break;
        }
    }

    @Override
    public ResetPasswordPresenter initPresenter() {
        return new ResetPasswordPresenter(mContext);
    }

    /**
     * 获取密码
     *
     * @return
     */
    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    /**
     * 获取确认密码
     *
     * @return
     */
    @Override
    public String getSurePassword() {
        return etSurePassword.getText().toString().trim();
    }

    /**
     * 获得手机号
     *
     * @return
     */
    @Override
    public String getMobile() {
        return mobile;
    }
}
