package com.bc.caibiao.ui.login;

import com.bc.caibiao.model.Member;
import com.bc.caibiao.ui.BaseView;

/**
 * @author wangkai
 * @create 16/9/14 上午10:19
 * @Description :
 */
public interface LoginContract {

    interface LoginView extends BaseView {
        /**
         * 获取登录名
         *
         * @return 登录名
         */
        String getLoginName();

        /**
         * 获取密码
         *
         * @return 密码
         */
        String getPassWord();

        /**
         * 跳转新页面
         */
        void goToMainActivity();

        /**
         * 跳转绑定界面
         */
        void goToBindActivity(int registerMode, String userId, String userName, String userIcon);

    }

    interface LoginPresenterImp {

        String TYPE_QQ = "1";
        String TYPE_WX = "0";

        /**
         * 登录/注册等提交,通过LoginView获取手机/密码
         */
        void submit();


        void loginWx();

        void loginQQ();

    }
}
