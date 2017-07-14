package com.bc.caibiao.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bc.caibiao.utils.Config;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hanxing on 2017/4/20.
 *
 * 微信的返回结果类
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    public static final String PAYSUCCESS = "paysuccess";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Config.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        Log.d("TAGG", "onPayFinish, errCode = " + resp.errCode);

        //0 成功 展示成功页面
        if (resp.errCode == 0) {
            Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new PayEvent(PAYSUCCESS));
            finish();
        }
        //-1 错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
        else if (resp.errCode == -1) {
            Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
            finish();
        }
        //-2 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
        else if (resp.errCode == -2) {
            Toast.makeText(this, "取消支付", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
