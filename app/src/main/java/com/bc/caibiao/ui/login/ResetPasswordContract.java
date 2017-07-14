package com.bc.caibiao.ui.login;


import com.bc.caibiao.ui.BaseView;

/**
 * @author wangkai
 * @create 16/9/18 上午10:03
 * @Description :
 */
public interface ResetPasswordContract {
    interface ResetPasswordView extends BaseView {
        /**
         * 获取密码
         *
         * @return
         */
        String getPassword();

        /**
         * 获取确认密码
         *
         * @return
         */
        String getSurePassword();

        /**
         * 获得手机号
         *
         * @return
         */
        String getMobile();
    }

    interface ResetPasswordPresenterImp {
        /**
         * 提交修改密码
         */
        void commit();
    }
}
