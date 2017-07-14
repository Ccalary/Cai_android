package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.Industry;
import com.bc.caibiao.adapter.QiMingModule.IndustryList;
import com.bc.caibiao.model.TradeMarkModel;
import com.bc.caibiao.model.TradeMarkResponse;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.ChooseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SmartNameActivity extends BaseActivity implements ChooseDialog.OnClickItemPosition {

    private ChooseDialog chooseCategoryDialog;
    private List<TradeMarkModel> mIndustyList = new ArrayList<>();
    private List<String> mIndustyStr = new ArrayList<>();

    @Bind(R.id.tvIndustry)
    TextView tvIndustry;

    @Bind(R.id.tv_key)
    EditText mKeyEt;

    int brandIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_name);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void initView() {

        //按行业查询
        findViewById(R.id.ll_industry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByBrand();
            }
        });

        //按关键字查询
        findViewById(R.id.ll_key).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByKeyword();
            }
        });


        //显示行业
        findViewById(R.id.llIndustry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIndustryDialog();
            }
        });

    }

    private void initData() {
        BCHttpRequest.getQiMingInterface().getIndustryDataNewApi("dg_company_category")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TradeMarkResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TradeMarkResponse tradeMarkResponse) {
                        mIndustyList.addAll(tradeMarkResponse.data.data);
                        for (int i=0;i<mIndustyList.size();i++){
                            mIndustyStr.add(mIndustyList.get(i).name);
                        }
                    }
                });
    }

    @Override
    public void onClickItem(int position, ChooseDialog dialog) {
        if (dialog == chooseCategoryDialog){
            brandIndex = position;
            tvIndustry.setText(mIndustyStr.get(position));
        }
    }


    private void showIndustryDialog() {
        if (mIndustyStr.size() == 0) {
            ToastUtils.showShort(this, "暂无行业可选");
            return;
        }

        if (chooseCategoryDialog == null) {
            chooseCategoryDialog = new ChooseDialog(this);
            chooseCategoryDialog.setDatas(mIndustyStr);
            chooseCategoryDialog.setOnClickItemPosition(this);
        }
        chooseCategoryDialog.show();
    }


    private void searchByBrand() {
        if (brandIndex < 0) {
            ToastUtils.showShort(this, "请选择行业");
            return;
        }

        Intent aIntent = new Intent(this, SmartLsitActivity.class);
        aIntent.putExtra("searchType",1);
        aIntent.putExtra("companyType",mIndustyList.get(brandIndex).id);
        startActivity(aIntent);
    }

    private void searchByKeyword() {
        if (TextUtils.isEmpty(mKeyEt.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入关键字");
            return;
        }

        Intent aIntent = new Intent(this, SmartLsitActivity.class);
        aIntent.putExtra("searchType",2);
        aIntent.putExtra("title",mKeyEt.getText().toString().trim());
        startActivity(aIntent);
    }
}
