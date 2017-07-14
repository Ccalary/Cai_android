package com.bc.caibiao.ui.login;

import com.bc.caibiao.ui.BaseView;

/**
 * @author wangkai
 * @create 16/9/18 上午9:33
 * @Description :
 */
public interface ForgetPasswordContract {
    interface ForgetPasswordView extends BaseView {

        /**
         * 获得手机号
         *
         * @return 手机号
         */
        String getMobile();

        /**
         * 获得验证码
         *
         * @return 验证码
         */
        String getAuthCode();

        /**
         * 改变验证码按钮中的文字
         *
         * @param text 显示的文字
         */
        void changeCheckCodeText(String text);

        /**
         * 设置验证码按钮是否可点，并改变背景
         *
         * @param isClickAble 是否可点--true为可以
         */
        void setCheckCodeCanClickAble(boolean isClickAble);

        /**
         * 跳转新页面
         */
        void goToResetPwdActivity();

    }

    interface ForgetPasswordPresenterImp {
        /**
         * 提交
         */
        void submit(int page_type, String id, String name, String img, String type);

        /**
         * 获取验证码
         */
        void getCheckCode();
    }
}
