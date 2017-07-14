package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chengyanfang on 2017/4/20.
 */

public class SendPostAct extends BaseActivity{

    String taskId;

    @Bind(R.id.etTaskTitle)
    EditText mTitle;

    @Bind(R.id.type_group)
    RadioGroup mPayTypeGroup;

    //要求内容
    @Bind(R.id.etDemand)
    EditText etDemand;
    //紫薯限制
    @Bind(R.id.tvWordNumber)
    TextView tvWordNumber;

    @Bind(R.id.forward_to_search)
    TextView tvForwardToSearch;

    int isSearch = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_post_layout);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void initView() {
        mPayTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.tvBrandGetName){
                    isSearch = 1;
                }else{
                    isSearch = 0;
                }
            }
        });


        etDemand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etDemand.getText().toString().length()==200){
                    etDemand.setText(etDemand.getText().toString().substring(0,199));
                    ToastUtils.showShort(SendPostAct.this,"限制在200字以内");
                    return;
                }
                tvWordNumber.setText(etDemand.getText().length()+"/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvForwardToSearch.setText(Html.fromHtml("<font color='#7b7b81'>前往</font><font color='#FF6600'>查询中心</font><font color='#7b7b81'>看看能否注册</font>"));

        tvForwardToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aIntent = new Intent(SendPostAct.this,SearchServiceAct.class);
                startActivity(aIntent);
            }
        });

        findViewById(R.id.tvPublish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPublish();
            }
        });
    }

    private void initData(){
        taskId = getIntent().getStringExtra("taskId");
    }

    /**
     * /**
     * 投稿
     * */
    public void onClickPublish(){
        if(TextUtils.isEmpty(mTitle.getText().toString().trim())){
            ToastUtils.showShort(this,"请输入标题");
            return;
        }

        if(TextUtils.isEmpty(etDemand.getText().toString().trim())){
            ToastUtils.showShort(this,"请输入释义说明");
            return;
        }

        showProgressHUD(this,"发布中...");

        final Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("memberId", RequestBody.create(MediaType.parse("text/plain"), SP.getInstance().getString(SPTag.TAG_MEMBER_ID)));
        bodyMap.put("brandName", RequestBody.create(MediaType.parse("text/plain"), mTitle.getText().toString()));
        bodyMap.put("taskId", RequestBody.create(MediaType.parse("text/plain"), taskId));
        bodyMap.put("reason", RequestBody.create(MediaType.parse("text/plain"), etDemand.getText().toString()));
        bodyMap.put("isCheck", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(isSearch)));

        BCHttpRequest.getQiMingInterface().touGaoApiNew(bodyMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BlankModel>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onNext(BlankModel result) {
                        if(result.fieldErrors.isEmpty()){
                            ToastUtils.showShort(SendPostAct.this,"提交成功");
                            finish();
                        }else{
                            ToastUtils.showShort(SendPostAct.this,result.fieldErrors.get(0).getMessage());
                        }
                        dismissProgressHUD();
                    }
                });
    }


}
