package com.bc.caibiao.ui.shangbiao;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.qiming.SmartLsitActivity;
import com.bc.caibiao.ui.qiming.SmartNameActivity;
import com.bc.caibiao.ui.qiming.TaskListActivity;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.popupwindow.AskForSignPop;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class ShangbiaoHomeBanner {

    Context mContext;
    View mView;

    public ShangbiaoHomeBanner(Context aContext) {
        this.mContext = aContext;
        getView();
    }

    public View getView() {
        if (mView == null) {
            LayoutInflater aLayoutInflater = LayoutInflater.from(mContext);
            this.mView = aLayoutInflater.inflate(R.layout.headerview_shangbiao, null);
        }
        initUI();
        return mView;
    }

    @SuppressWarnings("rawtypes")
    private void initUI() {
        mView.findViewById(R.id.llChaxun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AskForSignPop(mContext, mView, "", new AskForSignPop.FreeCheckCallback() {
                    @Override
                    public void doCheck(String brandName, String contactName, String mobile) {
                        doBuyCheck(brandName,contactName,mobile);
                    }
                }).setTitle("购买商标").show();
            }
        });

        mView.findViewById(R.id.llFenlei).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ShangbiaoClassificationActivity.class));
            }
        });

        mView.findViewById(R.id.llChushou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ShangbiaoSaleActivity.class));
            }
        });
    }

    /**
     * 发起请求申请
     * */
    private void doBuyCheck(String brandName, String contactName, String mobile){
        BCHttpRequest.getQiMingInterface().getbuyTrademarkApi(brandName,contactName,mobile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BlankModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BlankModel result) {
                        if(result.fieldErrors.isEmpty()){
                            ToastUtils.showShort(mContext,"提交成功");
                        }else{
                            ToastUtils.showShort(mContext,result.fieldErrors.get(0).getMessage());
                        }
                    }
                });
    }

}
