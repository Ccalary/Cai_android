package com.bc.caibiao.wxapi;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.bc.caibiao.utils.Config;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by hanxing on 2017/4/20.
 */

public class AllPayRequestUtils {


    /**判断用户是否安装微信*/
   public static boolean isWXAppInstalledAndSupported(Context context) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp(Config.APP_ID);

        boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled() && msgApi.isWXAppSupportAPI();

        return sIsWXAppInstalledAndSupported;
    }

    /**
     * 微信支付
     * https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_12&index=2
     *
     * @param appid     微信分配的公众账号ID
     * @param partnerid 微信支付分配的商户号
     * @param prepayid  微信返回的支付交易会话ID
     * @param noncestr  随机字符串，不长于32位。推荐随机数生成算法 https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=4_3
     * @param timestamp 时间戳，请见接口规则-参数规定 https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=4_2
     * @param packageX  暂填写固定值Sign=WXPay
     * @param sign      签名，详见签名生成算法
     * @param extData   额外的标记，未知
     */
    public static void wechatPay(Context context, String appid, String partnerid, String prepayid, String noncestr, String timestamp, String packageX, String sign) {

        IWXAPI api = WXAPIFactory.createWXAPI(context, Config.APP_ID);
        PayReq req = new PayReq();
        req.appId = appid;
        req.partnerId = partnerid;
        req.prepayId = prepayid;
        req.nonceStr = noncestr;
        req.timeStamp = timestamp;
        req.packageValue = packageX;
        req.sign = sign;
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信

        api.registerApp(Config.APP_ID);

        api.sendReq(req);
    }


    public static final int SDK_PAY_FLAG = 1;

    /**
     * 支付宝支付
     *
     * @param payInfo 服务器端返回的订单信息
     */
    public static void alipay(final Activity activity, final String payInfo, final Handler mHandler) {
        Runnable payRunnable = new Runnable() {

            @Override public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
