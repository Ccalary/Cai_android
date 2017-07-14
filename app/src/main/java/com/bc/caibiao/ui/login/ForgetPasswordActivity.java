package com.bc.caibiao.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.view.EditTextLayout;
import com.bc.caibiao.view.TopBarLayout;


/**
 * @author wangkai
 * @create 16/9/18 上午9:33
 * @Description :
 */
public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordContract.ForgetPasswordView, ForgetPasswordPresenter> implements ForgetPasswordContract.ForgetPasswordView {


    public static int PAGETYPE_PSW = 1 << 0;

    public static int PAGETYPE_BINDPHONE = 1 << 1;


    private final static String EXTAR_PAGE_TYPE = "page_type";
    private final static String EXTRA_ID = "id";
    private final static String EXTRA_TYPE = "type";
    private final static String EXTRA_NAME = "name";
    private final static String EXTRA_IMG = "img";


    private EditText etMobile;
    private EditText etCheckCode;
    private android.widget.TextView btnCheckCode;
    private android.widget.TextView btnRegister;
    private TopBarLayout tbTopBar;


    private int mPageType;
    private String mId;
    private String mName;
    private String mType;
    private String mImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPageType = getIntent().getIntExtra(EXTAR_PAGE_TYPE, PAGETYPE_PSW);

        mId = getIntent().getStringExtra(EXTRA_ID);

        mName = getIntent().getStringExtra(EXTRA_NAME);

        mType = getIntent().getStringExtra(EXTRA_TYPE);

        mImg = getIntent().getStringExtra(EXTRA_IMG);


        setContentView(R.layout.activity_forget_password);
        initView();


    }

    private void initView() {
        tbTopBar = (TopBarLayout) findViewById(R.id.v_topbar);
        this.btnRegister = (TextView) findViewById(R.id.btnRegister);
        this.btnCheckCode = (TextView) findViewById(R.id.btnCheckCode);
        this.etCheckCode = (EditText) findViewById(R.id.etCheckCode);
        this.etMobile = (EditText) findViewById(R.id.etMobile);

        this.btnCheckCode.setOnClickListener(this);
        this.btnRegister.setOnClickListener(this);
        
        if (PAGETYPE_BINDPHONE == mPageType) {
            tbTopBar.setTitle("手机号注册");
        } else if (PAGETYPE_PSW == mPageType) {
            tbTopBar.setTitle("忘记密码");
        }


    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btnCheckCode:
                mPresenter.getCheckCode();
                break;
            case R.id.btnRegister:
                mPresenter.submit(mPageType, mId, mName, mImg, mType);
                break;
        }
    }

    @Override
    public ForgetPasswordPresenter initPresenter() {
        return new ForgetPasswordPresenter(mContext);
    }

    /**
     * 获得手机号
     *
     * @return 手机号
     */
    @Override
    public String getMobile() {
        return etMobile.getText().toString().trim();
    }

    /**
     * 获得验证码
     *
     * @return 验证码
     */
    @Override
    public String getAuthCode() {
        return etCheckCode.getText().toString().trim();
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
        btnCheckCode.setBackgroundColor(isClickAble ? Color.parseColor("#ffb739") : Color.parseColor("#d2d2d2"));
    }

    /**
     * 跳转新页面
     */
    @Override
    public void goToResetPwdActivity() {
        Intent intent = new Intent(mContext, ResetPasswordActivity.class);
        intent.putExtra(ResetPasswordActivity.MOBILE, getMobile());
        startActivity(intent);
        finish();
    }


    /**
     * 开启页面用于忘记密码
     */
    public static void startMeToForgetPsw(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        intent.putExtra(EXTAR_PAGE_TYPE, PAGETYPE_PSW);
        context.startActivity(intent);
    }

    /**
     * 开启页面用于绑定手机号
     */
    public static void startMeToBindPhone(Context context, String id, String type, String name, String img) {

        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        intent.putExtra(EXTAR_PAGE_TYPE, PAGETYPE_BINDPHONE);
        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_IMG, img);

        context.startActivity(intent);


    }


}
