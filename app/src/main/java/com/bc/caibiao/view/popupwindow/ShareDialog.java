package com.bc.caibiao.view.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bc.caibiao.R;
import com.bc.caibiao.utils.BitmapScale;
import com.bc.caibiao.utils.ScreenUtils;
import com.bc.caibiao.utils.ToastUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.tencent.qq.*;
import cn.sharesdk.wechat.moments.WechatMoments;

import android.os.Handler.Callback;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;


/**
 * Created by chengyanfang on 2017/4/24.
 */

public class ShareDialog  extends Dialog implements PlatformActionListener,
        Callback{

    /** 朋友圈分享对象 */
    private Platform platform_circle;
    /** 微信好友分享对象 */
    private Platform platform_wxFriend;
    /** QQ空间分享对象 */
    private Platform platform_qzone;
    /** QQ好友分享对象 */
    private Platform platform_qqFriend;


    String share_title = "商标注册，首选才标。";
    String share_desc = "申请不成功，免费重新申报。";
    String share_url = "http://www.58caibiao.com/?route=mobile/home";

    Context mContext;

    public ShareDialog(Context context) {
        super(context, R.style.dialog_bottom_full);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        setContentView(inflate);

        mContext = context;

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogWindow.setWindowAnimations(R.style.AnimBottom);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ScreenUtils.getScreenWidth(context);
        lp.height = ScreenUtils.dip2px(getContext(), 80);
        dialogWindow.setAttributes(lp);



        findViewById(R.id.wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                platform_wxFriend = ShareSDK.getPlatform(mContext, Wechat.NAME);
                Wechat.ShareParams sp = new Wechat.ShareParams();
                sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
                sp.setTitle(share_title);
                sp.setText(share_desc);
                sp.setImageData(getBitmap());
                sp.setUrl(share_url);
                platform_wxFriend.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                platform_wxFriend.share(sp);
                dismiss();
            }
        });

        findViewById(R.id.wx_cycle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                platform_circle = ShareSDK.getPlatform(mContext, WechatMoments.NAME);
                WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
                sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
                sp.setTitle(share_title);
                sp.setText(share_desc);
                sp.setImageData(getBitmap());
                sp.setUrl(share_url);
                platform_circle.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                platform_circle.share(sp);
                dismiss();
            }
        });

        findViewById(R.id.qq_rlv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                platform_qqFriend = ShareSDK.getPlatform(mContext, QQ.NAME);
                QQ.ShareParams sp = new QQ.ShareParams();
                sp.setTitle(share_title);
                sp.setText(share_desc);
                sp.setTitleUrl(share_url);
                sp.setImageData(getBitmap());
                sp.setSite(share_title);
                sp.setSiteUrl(share_url);

                platform_qqFriend.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                platform_qqFriend.share(sp);

                dismiss();
            }
        });

        findViewById(R.id.qqzone_rlv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                platform_qzone = ShareSDK.getPlatform(mContext, QZone.NAME);
                QZone.ShareParams sp = new QZone.ShareParams();
                sp.setTitle(share_title);
                sp.setText(share_desc);
                sp.setTitleUrl(share_url);
                sp.setImageData(getBitmap());
                sp.setSite(share_title);
                sp.setSiteUrl(share_url);

                platform_qzone.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                // 执行图文分享
                platform_qzone.share(sp);

                dismiss();
            }
        });
    }

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1) {
                // 1代表分享成功，2代表分享失败，3代表分享取消
                case 1:
                    // 成功
                    ToastUtils.showShort(mContext, "分享成功");
                    break;
                case 2:
                    ToastUtils.showShort(mContext, "分享失败");
                    break;

            }
            return false;
        }

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }

    private int THUMB_SIZE = 75;

    private Bitmap getBitmap(){
        Bitmap bitmap = drawableToBitmap(mContext.getResources().getDrawable(R.mipmap.ic_launcher));

        Bitmap scaleBitmap = BitmapScale.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, BitmapScale.ScalingLogic.CROP);
        return scaleBitmap;
    }

    /** drawable -> bitmap */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bm = bd.getBitmap();
        return bm;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

