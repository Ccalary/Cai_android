package com.bc.caibiao.utils;

import android.content.Context;

/**
 * @author wangkai
 * @create 16/9/23 上午9:48
 * @Description :
 */
public class ShareAction {
    private String title;
    private String picUrl;
    private String contentUrl;
    private Context context;
    private String strPlatform;
    private String subtitle;

//    public ShareAction(Context context, String title, String picUrl, String contentUrl, String platform) {
//        BCL.e(title);
//        BCL.e(picUrl);
//        BCL.e(contentUrl);
//        BCL.e(platform);
//        this.title = title;
//        this.picUrl = picUrl;
//        this.contentUrl = contentUrl;
//        this.context = context;
//        this.strPlatform = platform;
//        if (!isWeChatAvailable(context)) {
//            ToastUtils.showShort(context, "请先安装微信");
//            return;
//        }
//        share();
//    }
//
//    public ShareAction(Context context, String title, String subtitle, String picUrl, String contentUrl, String platform) {
//        BCL.e(title);
//        BCL.e(picUrl);
//        BCL.e(contentUrl);
//        BCL.e(platform);
//        this.title = title;
//        this.picUrl = picUrl;
//        this.contentUrl = contentUrl;
//        this.context = context;
//        this.strPlatform = platform;
//        this.subtitle=subtitle;
//        if (!isWeChatAvailable(context)) {
//            ToastUtils.showShort(context, "请先安装微信");
//            return;
//        }
//        share();
//    }

//    private void share() {
//        Platform.ShareParams sp = new Platform.ShareParams();
//        sp.setTitle(title);
//        sp.setImageUrl(URLConfig.baseUrl_pic_oss + picUrl);//网络图片rul
//        sp.setTitleUrl(contentUrl);
//        sp.setUrl(contentUrl);
//        if(!TextUtils.isEmpty(subtitle)){
//            sp.setText(subtitle);
//        }
//
//        sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
//        Platform platform = ShareSDK.getPlatform(strPlatform);
//        platform.setPlatformActionListener(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                ToastUtils.showShort(context, "分享成功");
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                ToastUtils.showShort(context, "分享失败");
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                ToastUtils.showShort(context, "分享取消");
//            }
//        });
//        platform.share(sp);
//    }
//
//    public static boolean isWeChatAvailable(Context context) {
//        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
//        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
//        if (pinfo != null) {
//            for (int i = 0; i < pinfo.size(); i++) {
//                String pn = pinfo.get(i).packageName;
//                if (pn.equals("com.tencent.mm")) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
}
