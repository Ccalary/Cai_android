package com.bc.caibiao.request;

import com.bc.caibiao.request.subscribe.QiMingInterface;

/**
 * @author wangkai
 * @create 16/9/14 下午2:02
 * @Description :
 */
public class BCHttpRequest {
    private static MemberInterface mMemberInterface;
    private static VersionInterface versionInterface;
    private static OtherInterface otherInterface;
    private static MarkInterface mMarkInterface;
    private static QiMingInterface mQiMingInterface;
    private static AccountInterface mAccountInterface;
    private static OrderInterface mOrderInterface;
    private static MessageInterface mMessageInterface;
    private static CornerMarkInterface mCornerMarkInterface;
    /**
     * @return 商标模块接口
     *
     * @author Chengyanfang
     * */
    public static MarkInterface getMarkInterface(){
        if (mMarkInterface == null) {
            mMarkInterface = ApiConfig.getDefault().create(MarkInterface.class);
        }
        return mMarkInterface;
    }


    /**
     * @return 起名模块接口
     *
     * @author Chengyanfang
     * */
    public static QiMingInterface getQiMingInterface(){
        if (mQiMingInterface == null) {
            mQiMingInterface = ApiConfig.getDefault().create(QiMingInterface.class);
        }
        return mQiMingInterface;
    }


    /**
     * @return 会员模块请求接口
     */
    public static MemberInterface getMemberInterface() {
        if (mMemberInterface == null) {
            mMemberInterface = ApiConfig.getDefault().create(MemberInterface.class);
        }
        return mMemberInterface;
    }

    /**
     * @return 检查更新请求接口
     */
    public static VersionInterface getVersionInterface() {
        if (versionInterface == null) {
            versionInterface = ApiConfig.getDefault().create(VersionInterface.class);
        }
        return versionInterface;
    }

    public static OtherInterface getOtherInterface() {
        if (otherInterface == null) {
            otherInterface = ApiConfig.getDefault().create(OtherInterface.class);
        }
        return otherInterface;
    }

    public static AccountInterface getAccountInterface() {
        if (mAccountInterface == null) {
            mAccountInterface = ApiConfig.getDefault().create(AccountInterface.class);
        }
        return mAccountInterface;
    }

    public static OrderInterface getOrderInterface() {
        if (mOrderInterface == null) {
            mOrderInterface = ApiConfig.getDefault().create(OrderInterface.class);
        }
        return mOrderInterface;
    }


    public static MessageInterface getMessageInterface() {
        if (mMessageInterface == null) {
            mMessageInterface = ApiConfig.getDefault().create(MessageInterface.class);
        }
        return mMessageInterface;
    }

    public static CornerMarkInterface getCornerMarkInterface() {
        if (mCornerMarkInterface == null) {
            mCornerMarkInterface = ApiConfig.getDefault().create(CornerMarkInterface.class);
        }
        return mCornerMarkInterface;
    }
}
