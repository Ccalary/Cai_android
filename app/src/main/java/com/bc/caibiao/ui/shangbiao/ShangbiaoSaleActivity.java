package com.bc.caibiao.ui.shangbiao;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.BaseTestModel;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 出售商标
 * @author chengyanfang
 * */
public class ShangbiaoSaleActivity extends BaseActivity {

    @Bind(R.id.etName)
    EditText etName;

    @Bind(R.id.etRegCode)
    EditText etRegCode;

    @Bind(R.id.etPrice)
    EditText etPrice;

    @Bind(R.id.etUserName)
    EditText etUserName;

    @Bind(R.id.etPhone)
    EditText etPhone;

    @Bind(R.id.tvSubmit)
    TextView tvSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangbiao_sale);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSubmit();
            }
        });
    }


    //进行销售
    private void doSubmit(){
        if(TextUtils.isEmpty(etName.getText().toString().trim())){
            ToastUtils.showShort(this,"请输入商标名称");
            return;
        }

        if(TextUtils.isEmpty(etPrice.getText().toString().trim())){
            ToastUtils.showShort(this,"请输入商标价格");
            return;
        }

        if(TextUtils.isEmpty(etUserName.getText().toString().trim())){
            ToastUtils.showShort(this,"请输入你的姓名");
            return;
        }

        if(TextUtils.isEmpty(etPhone.getText().toString().trim())){
            ToastUtils.showShort(this,"请输入你的手机号");
            return;
        }


        BCHttpRequest.getMarkInterface().getSellMarkApi(etName.getText().toString().trim()
                ,etPrice.getText().toString().trim()
                ,etUserName.getText().toString().trim()
                ,etPhone.getText().toString().trim())
                .compose(HttpResponseHelper.<BaseTestModel>getAllData())
                .subscribe(new ResolveFailSubscribe<BaseTestModel>(mContext, mContext.getString(R.string.progress_login)) {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        ToastUtils.showShort(ShangbiaoSaleActivity.this,fieldError.getMessage());
                    }

                    @Override
                    protected void _onNext(BaseTestModel member) {
                        ToastUtils.showShort(ShangbiaoSaleActivity.this,"提交成功");
                        finish();
                    }
                });
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
