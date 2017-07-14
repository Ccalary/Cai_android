package com.bc.caibiao.ui.login;

/**
 * @author wangkai
 * @create 16/9/14 下午2:32
 * @Description :
 */
public interface RegisterContract {
    interface RegisterView extends LoginContract.LoginView {
        /**
         * 获得验证码
         *
         * @return 验证码
         */
        String getCheckCode();

        /**
         * 获得确认密码
         *
         * @return 确认密码
         */
        String getSurePassword();

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
         * @return 是否同意了用户协议
         */
        boolean isSelectAgreement();


    }

    interface RegisterPresenterImp extends LoginContract.LoginPresenterImp {

        /**
         * 获取验证码
         */
        void getCheckCode();

    }

}
