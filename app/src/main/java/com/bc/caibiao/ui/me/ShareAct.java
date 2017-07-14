package com.bc.caibiao.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.view.popupwindow.ShareDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chengyanfang on 2017/4/23.
 */

public class ShareAct  extends BaseActivity {

    @Bind(R.id.userName)
    TextView mUserName;

    ShareDialog mShareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_layout);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mUserName.setText("我是"+SP.getInstance().getString(SPTag.TAG_MEMBER_NAME));
        findViewById(R.id.ivRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare(ShareAct.this,null,false);
            }
        });

        findViewById(R.id.ivLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void showShare(Context context, String platformToShare, boolean showContentEdit) {

        mShareDialog = new ShareDialog(this);
        mShareDialog.show();

//        OnekeyShare oks = new OnekeyShare();
//        oks.setSilent(!showContentEdit);
//        if (platformToShare != null) {
//            oks.setPlatform(platformToShare);
//        }
//        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
//        oks.setTheme(OnekeyShareTheme.CLASSIC);
//        // 令编辑页面显示为Dialog模式
//        oks.setDialogMode();
//        // 在自动授权时可以禁用SSO方式
//        oks.disableSSOWhenAuthorize();
//        oks.setTitle("商标注册，首选才标。");
//        oks.setTitleUrl("http://www.58caibiao.com/?route=mobile/home");
//        oks.setText("申请不成功，免费重新申报。");
//
//        oks.setUrl("http://www.58caibiao.com/?route=mobile/home"); //微信不绕过审核分享链接
//        oks.setComment("申请不成功，免费重新申报。"); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
//
//        oks.setSite("商标注册，首选才标。");  //QZone分享完之后返回应用时提示框上显示的名称
//        oks.setSiteUrl("http://www.58caibiao.com/?route=mobile/home");//QZone分享参数
//        oks.show(context);

    }

    private void initData(){

    }



    @Override
    public BasePresenter initPresenter() {
        return null;
    }


}
