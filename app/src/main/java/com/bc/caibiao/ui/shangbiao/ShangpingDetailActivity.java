package com.bc.caibiao.ui.shangbiao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.model.MarkModel.MarkDetailModel;
import com.bc.caibiao.model.MarkModel.ProductDetail;
import com.bc.caibiao.model.MarkModel.TaskPriceList;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.StringUtil;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShangpingDetailActivity extends BaseActivity {

    @Bind(R.id.sdvPic)
    ImageView sdvPic;

    @Bind(R.id.tvName)
    TextView tvName;

    @Bind(R.id.tvPrice)
    TextView tvPrice;

    @Bind(R.id.wvDetial)
    WebView mWebview;

    ProductDetail mProductDetail;

    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangping_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.rlv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buyIntent=new Intent(ShangpingDetailActivity.this,ShangpingBuyActivity.class);

                buyIntent.putExtra("imgPath",mProductDetail.thumb);
                buyIntent.putExtra("cntType",mProductDetail.stock);
                buyIntent.putExtra("productId",productId);
                buyIntent.putExtra("priceFen",mProductDetail.getPriceFen());

                startActivity(buyIntent);
            }
        });
    }

    private void initData(){
        productId = getIntent().getStringExtra("productId");

        showProgressHUD(this,"加载中");

        BCHttpRequest.getMarkInterface().getMarkDetail(productId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MarkDetailModel>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onNext(MarkDetailModel markDetailModel) {
                        dismissProgressHUD();
                        mProductDetail = markDetailModel.data;
                        loadData();
                    }
                });
    }


    private void loadData(){

        findViewById(R.id.content_scrollview).setVisibility(View.VISIBLE);

        Glide.with(this).load(mProductDetail.thumb).placeholder(R.drawable.icon_guoji).into(sdvPic);

        tvName.setText(mProductDetail.name);

        tvPrice.setText("¥"+ StringUtil.getFormatedFloatString(mProductDetail.price));

        mWebview.loadDataWithBaseURL(null,getHtmlInfo(), "text/html", "utf-8", null);

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    private String getHtmlInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head> <style>img{max-width:340px !important;}</style> </head>");
        sb.append("<body>");

        sb.append(mProductDetail.description);

        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!BaseApplication.isAddedFloatView){
            BaseApplication.getInstance().addFloatView();
        }
    }

    @Override
    public void finish() {
        super.finish();
        BaseApplication.getInstance().removeFloatView();
    }
}
