package com.bc.caibiao.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.MainActivity;
import com.bc.caibiao.utils.ActivityStack;

/**
 * @author wangkai
 * @create 16/9/14 上午10:16
 * @Description :123
 */
public class LoginActivity extends BaseActivity<LoginContract.LoginView, LoginPresenter> implements LoginContract.LoginView {
    private android.widget.TextView btnLogin;
    private android.widget.TextView tvRegister;
    private android.widget.TextView tvForgetPwd;
    private EditText etMobile;
    private EditText etPassword;
    private android.widget.ImageView ivWeChat;
    private ImageView ivQQ;
    private ImageView ivWeiBo;

    private View vLoginWx;
    private View vLoginQQ;

    //登录完之后是否需要返回
    //Add By Chengyanfang
    boolean isNeedBack = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        ActivityStack.getInstance().addToMap(Constant.ACTIVITY_STACK_LOGIN, this);

        //Add By Chengyanfang
        isNeedBack = getIntent().getBooleanExtra("isNeedBack", false);
    }

    @SuppressWarnings("ConstantConditions")
    private void initView() {
        vLoginQQ = findViewById(R.id.login_qq);
        vLoginWx = findViewById(R.id.login_wx);
        this.tvForgetPwd = (TextView) findViewById(R.id.tvForgetPwd);
        this.tvForgetPwd.setOnClickListener(this);
        this.tvRegister = (TextView) findViewById(R.id.tvRegister);
        this.tvRegister.setOnClickListener(this);
        this.btnLogin = (TextView) findViewById(R.id.tvLogin);
        this.btnLogin.setOnClickListener(this);
        this.etPassword = (EditText) findViewById(R.id.etPassword);
        this.etMobile = (EditText) findViewById(R.id.etMobile);

        vLoginWx.setOnClickListener(this);
        vLoginQQ.setOnClickListener(this);
    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tvLogin:
                mPresenter.submit();
                break;
            case R.id.tvRegister:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
            case R.id.tvForgetPwd:
                ForgetPasswordActivity.startMeToForgetPsw(mContext);
                break;
            case R.id.login_qq:
                mPresenter.loginQQ();
                break;
            case R.id.login_wx:
                mPresenter.loginWx();
                break;
        }
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(mContext);
    }

    /**
     * 获取登录名
     *
     * @return 登录名
     */
    @Override
    public String getLoginName() {
        return etMobile.getText().toString().trim();
    }

    /**
     * 获取密码
     *
     * @return 密码
     */
    @Override
    public String getPassWord() {
        return etPassword.getText().toString().trim();
    }

    /**
     * 跳转新页面
     */
    @Override
    public void goToMainActivity() {
        //Add By Chengyanfang
//        if (!isNeedBack) {
//            startActivity(new Intent(mContext, MainActivity.class));
//        }
        finish();
    }

    @Override
    public void goToBindActivity(int registerMode, String userId, String userName, String userIcon) {
        ForgetPasswordActivity.startMeToBindPhone(this, userId, registerMode + "", userName, userIcon);
    }

}
