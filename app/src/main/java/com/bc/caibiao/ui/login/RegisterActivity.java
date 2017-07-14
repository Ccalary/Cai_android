package com.bc.caibiao.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.CommonWebActivity;
import com.bc.caibiao.ui.MainActivity;
import com.bc.caibiao.utils.ActivityStack;
import com.bc.caibiao.view.EditTextLayout;


/**
 * @author wangkai
 * @create 16/9/14 下午2:32
 * @Description :
 */
public class RegisterActivity extends BaseActivity<RegisterContract.RegisterView, RegisterPresenter> implements RegisterContract.RegisterView {
    private EditText etMobile;
    private EditText etCheckCode;
    private TextView btnCheckCode;
    private EditText etPassword;
    private EditText etSurePassword;
    private TextView btnRegister;
    private TextView tvRegisterAgreement;
    private CheckBox cbAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addToMap(Constant.ACTIVITY_STACK_LOGIN, this);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        this.cbAgreement = (CheckBox) findViewById(R.id.cbAgreement);
        this.btnRegister = (TextView) findViewById(R.id.btnRegister);
//        this.etSurePassword = (EditText) findViewById(R.id.etSurePassword);
        this.etPassword = (EditText) findViewById(R.id.textPassword);
        this.btnCheckCode = (TextView) findViewById(R.id.btnCheckCode);
        this.etCheckCode = (EditText) findViewById(R.id.etCheckCode);
        this.etMobile = (EditText) findViewById(R.id.etMobile);
        this.tvRegisterAgreement = (TextView) findViewById(R.id.tvRegisterAgreement);
        this.tvRegisterAgreement.setOnClickListener(this);
        this.btnCheckCode.setOnClickListener(this);
        this.btnRegister.setOnClickListener(this);
    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btnCheckCode:
                mPresenter.getCheckCode();
                break;
            case R.id.btnRegister:
                mPresenter.submit();
                break;
            case R.id.tvRegisterAgreement:
                Intent intent = new Intent(mContext, CommonWebActivity.class);
                intent.putExtra(CommonWebActivity.INTENT_TITLE,
                        "才标网用户协议");
                intent.putExtra(CommonWebActivity.INTENT_URL, "http://www.58caibiao.com:8788/index.html");
                startActivity(intent);
                break;
        }
    }

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter(mContext);
    }

    /**
     * 获得验证码
     *
     * @return 验证码
     */
    @Override
    public String getCheckCode() {
        return etCheckCode.getText().toString().trim();
    }

    /**
     * 获得确认密码
     *
     * @return 确认密码
     */
    @Override
    public String getSurePassword() {
        return etSurePassword.getText().toString().trim();
    }

    /**
     * 改变验证码按钮中的文字
     *
     * @param text 显示的文字
     */
    @Override
    public void changeCheckCodeText(String text) {
        btnCheckCode.setText(text);
    }

    /**
     * 设置验证码按钮是否可点，并改变背景
     *
     * @param isClickAble 是否可点--true为可以
     */
    @Override
    public void setCheckCodeCanClickAble(boolean isClickAble) {
        btnCheckCode.setOnClickListener(isClickAble ? this : null);
//        btnCheckCode.setBackground(getResources().getDrawable(isClickAble ? R.drawable.shape_solid_red : R.drawable.shape_solid_grey));
    }

    @Override
    public boolean isSelectAgreement() {
        return cbAgreement.isChecked();
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
        startActivity(new Intent(mContext, MainActivity.class));
        ActivityStack.getInstance().finishActivityMap(Constant.ACTIVITY_STACK_LOGIN);
        // FIXME: 16/10/9 丁二让改的
//        finish();
    }

    /**
     * 登陆界面需要跳转的
     *
     * @param registerMode
     * @param userId
     * @param userName
     * @param userIcon
     */
    @Override
    public void goToBindActivity(int registerMode, String userId, String userName, String userIcon) {

    }
}
