package com.bc.caibiao.ui.shangbiao;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.MarkModel.MarkDetail;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.ImageLoader;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import rx.Observer;

/**
 *
 * 商标详情页面
 *
 * @author chengyandfang
 * */

public class ShangbiaoDetailActivity extends BaseActivity {

    /**
     * UI元素
     * */
    SimpleDraweeView mIconIv;
    TextView mMarkName;
    ImageView mIsFollow;

    //注册号
    TextView cxkey;
    //国际分类
    TextView intcls;
    //商标名称
    TextView tmname_cell;

    //初审公告期号
    TextView regIssue;
    //初审日期
    TextView appdate;
    //注册日期
    TextView regdate;

    //申请人名称
    TextView appname;
    //申请人地址
    TextView appaddr;
    //代理机构
    TextView agentname;
    //核定使用商品或服务
    LinearLayout goods;
    //商品流程
    LinearLayout flow;

    String cxkeyStr,intclsStr;
    MarkDetail mMarkDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shang_biao_detail);

        initView();
        initData();
        requestData();
    }

    private void initView(){
        mIconIv = (SimpleDraweeView)findViewById(R.id.imageView);
        mIsFollow = (ImageView)findViewById(R.id.follow);
        mMarkName = (TextView) findViewById(R.id.name);

        cxkey = (TextView) findViewById(R.id.cxkey);
        intcls = (TextView) findViewById(R.id.intcls);
        tmname_cell = (TextView) findViewById(R.id.tmname_cell);

        regIssue = (TextView) findViewById(R.id.regIssue);
        appdate = (TextView) findViewById(R.id.appdate);
        regdate = (TextView) findViewById(R.id.regdate);

        appname = (TextView) findViewById(R.id.appname);
        appaddr = (TextView) findViewById(R.id.appaddr);
        agentname = (TextView) findViewById(R.id.agentname);

        goods = (LinearLayout) findViewById(R.id.goods);
        flow = (LinearLayout) findViewById(R.id.flow);
    }


    private void initData(){
        cxkeyStr = getIntent().getStringExtra("cxkeyNum");
        intclsStr = getIntent().getStringExtra("intcls");
    }

    /**
     * 请求数据
     * */
    private void requestData(){
        showLoading("加载中");

        BCHttpRequest.getMarkInterface()
                .getMarkDetailApi(SP.getInstance().getString(SPTag.TAG_MEMBER_ID),cxkeyStr,intclsStr)
                .compose(HttpResponseHelper.<MarkDetail>getAllData())
                .subscribe(new Observer<MarkDetail>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        ToastUtils.showShort(ShangbiaoDetailActivity.this,"网络错误");
                    }

                    @Override
                    public void onNext(MarkDetail markDetail) {
                        dismissLoading();
                        mMarkDetail = markDetail;
                        refreshUI();
                    }
                });

    }

    /**
     * 刷新页面
     * */
    private void refreshUI(){
        //商标图片
        ImageLoader.progressiveLoad(mMarkDetail.image,mIconIv);

        mMarkName.setText(mMarkDetail.tmname);
        cxkey.setText(mMarkDetail.cxkey);
        intcls.setText(mMarkDetail.intcls);
        tmname_cell.setText(mMarkDetail.tmname);
        regIssue.setText(mMarkDetail.regIssue);
        appdate.setText(mMarkDetail.appdate);
        regdate.setText(mMarkDetail.regdate);
        appname.setText(mMarkDetail.appname);
        appaddr.setText(mMarkDetail.appaddr);
        agentname.setText(mMarkDetail.agentname);

        if(mMarkDetail.follow.equals("false")){
            mIsFollow.setBackgroundResource(R.drawable.icon_xin_n);
        }else{
            mIsFollow.setBackgroundResource(R.drawable.icon_xin);
        }

        for(int i = 0;i<mMarkDetail.goods.size();i++){
            LinearLayout linearLayout = getLinearLayout();
            linearLayout.addView(getTextView(mMarkDetail.goods.get(i).goodsCode));
            linearLayout.addView(getTextView(mMarkDetail.goods.get(i).goodsName));
            goods.addView(linearLayout);
        }


        for(int i = 0;i<mMarkDetail.flow.size();i++){
            LinearLayout linearLayout = getLinearLayout();
            linearLayout.addView(getTextView(mMarkDetail.flow.get(i).flowDate));
            linearLayout.addView(getTextView(mMarkDetail.flow.get(i).flowName));
            flow.addView(linearLayout);
        }

    }


    private LinearLayout getLinearLayout(){
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setWeightSum(2);
        LinearLayout.LayoutParams linearLayoutLP =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutLP.setMargins(0, AppUtil.dip2px(this,12f),0,0);
        linearLayout.setLayoutParams(linearLayoutLP);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return  linearLayout;
    }

    private TextView getTextView(String title){
        TextView atext = new TextView(this);
        atext.setTextSize(getResources().getDimension(R.dimen.bc_6_px));
        LinearLayout.LayoutParams linearLayoutLP =  new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1);
        atext.setLayoutParams(linearLayoutLP);
        atext.setText(title);
        return atext;
    }

    @Override
    public void OnClick(View v) {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
