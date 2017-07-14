package com.bc.caibiao.base;

/**
 * @author wangkai
 * @Description : 常量
 * create at 2015/11/21 10:59
 */
public class Constant {
    /**
     * 拍照的RequestCode
     */
    public static final int PHOTO = 10000;
    /**
     * 相册的RequestCode
     */
    public static final int ALBUM = 20000;
    /**
     * 裁剪的RequestCode
     */
    public static final int CROP = 30000;

    /**
     * 登录模块堆栈管理KEY
     */
    public static final String ACTIVITY_STACK_LOGIN = "activity_stack_login";

    //// FIXME: 2016/11/3 设置微信支付APP_ID
    public static String APP_ID;

    //一页条数
    public static int PAGESIZE = 10;

    //退出标识
    public static String LOGOUT = "LOGOUT";

    public static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    public static final int REQUEST_CODE_CHOOSE_PHOTO_IDLEFT = 1000;
    public static final int REQUEST_CODE_CHOOSE_PHOTO = 1001;

    /**
     * 获取验证码相关
     */
    public class AuthCode {
        /**
         * 验证码用途
         */
        public class Operation {
            /* 注册 */
            public static final short REGISTER = 1;
            /* 修改手机号 */
            public static final short MODIFY_MOBILE = 2;
            /* 找回密码 或 设置支付密码 */
            public static final short FIND_PASSWORD = 3;
        }

        /**
         * 验证码获取形式
         */
        public class CodeType {
            /* 短信形式 */
            public static final short SMS = 1;
            /* 语音形式 */
            public static final short VOICE = 2;
        }


    }

    //======================================
    // 商城：排序方式
    public static final int ORDERBY_SALE_ASC = 1;
    public static final int ORDERBY_SALE_DESC = 2;
    public static final int ORDERBY_PRICE_ASC = 3;
    public static final int ORDERBY_PRICE_DESC = 4;
    public static final int ORDERBY_TIME_ASC = 5;
    public static final int ORDERBY_TIME_DESC = 6;
    //======================================

}