package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModel;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModelZFB;
import com.bc.caibiao.model.MarkModel.TagList;
import com.bc.caibiao.model.MarkModel.TaskCategory;
import com.bc.caibiao.model.MarkModel.TaskCategoryList;
import com.bc.caibiao.model.MarkModel.TaskDate;
import com.bc.caibiao.model.MarkModel.TaskDateList;
import com.bc.caibiao.model.MarkModel.TaskPrice;
import com.bc.caibiao.model.MarkModel.TaskPriceList;
import com.bc.caibiao.model.MarkModel.TaskTag;
import com.bc.caibiao.model.TradeMarkModel;
import com.bc.caibiao.model.TradeMarkResponse;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.NetUtil;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.ChooseDialog;
import com.bc.caibiao.view.TagView;
import com.bc.caibiao.wxapi.AllPayRequestUtils;
import com.bc.caibiao.wxapi.PayEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bc.caibiao.wxapi.WXPayEntryActivity.PAYSUCCESS;

/**
 * 发布任务
 */

public class PublishTaskActivity extends BaseActivity implements ChooseDialog.OnClickItemPosition {

    //类型
    @Bind(R.id.type_group)
    RadioGroup mRadioGroup;
    //标题
    @Bind(R.id.etTaskTitle)
    EditText etTaskTitle;


    //行业
    @Bind(R.id.llCompanyGetName)
    LinearLayout llCompanyGetName;
    @Bind(R.id.tvIndustry)
    TextView tvIndustry;

    //分类
    @Bind(R.id.llBrandGetName)
    LinearLayout llBrandGetName;
    @Bind(R.id.tvCategory)
    TextView tvCategory;

    //分类查询
    @Bind(R.id.tvCategoryQuery)
    TextView tvCategoryQuery;

    //标签
    @Bind(R.id.llv_taglayout_recommand)
    LinearLayout mTaglayout;

    //要求内容
    @Bind(R.id.etDemand)
    EditText etDemand;
    //紫薯限制
    @Bind(R.id.tvWordNumber)
    TextView tvWordNumber;

    //截稿周期
    @Bind(R.id.tvEndTime)
    TextView tvEndTime;

    //悬赏金额
    @Bind(R.id.tvReward)
    TextView tvReward;

    //支付方式
    @Bind(R.id.pay_type_group)
    RadioGroup mPayTypeGroup;

    @Bind(R.id.tvPublish)
    TextView tvPublish;

    //分类：1、商标起名 2、公司起名
    int type = 1;

    //支付方式：1.支付宝、2、微信
    int payType = -1;

    //选择列表
    private ChooseDialog chooseCompanyDialog;
    private ChooseDialog chooseCategoryDialog;
    private ChooseDialog chooseEndTimeDialog;
    private ChooseDialog chooseRewardDialog;

    private List<String> companyStrs = new ArrayList<>();
    private List<String> categoryStrs = new ArrayList<>();
    private List<String> endTimeStrs = new ArrayList<>();
    private List<String> rewardStrs = new ArrayList<>();

    //要求标签
    ArrayList<TaskTag> mTopTags = new ArrayList();
    ArrayList<TaskDate> mTaskDateTags = new ArrayList();
    ArrayList<TaskPrice> mTaskPriceTags = new ArrayList();
    ArrayList<TradeMarkModel> mTaskCompanyTags = new ArrayList();
    ArrayList<TaskCategory> mTaskCategory  = new ArrayList();

    int companyIndex = -1,categoryIndex = -1,dateIndex = -1,rewardIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_task);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.tvBrandGetName){
                    type = 1;
                    llBrandGetName.setVisibility(View.VISIBLE);
                    llCompanyGetName.setVisibility(View.GONE);
                }else{
                    type = 2;
                    llBrandGetName.setVisibility(View.GONE);
                    llCompanyGetName.setVisibility(View.VISIBLE);
                }
            }
        });


        mPayTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.zfb_pay){
                    payType = 1;
                }else{
                    payType = 2;
                }
            }
        });

        //行业选择
        tvIndustry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCompanyDialog();
            }
        });

        //分类选择
        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialog();
            }
        });

        //截稿周期
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndTimeDialog();
            }
        });

        //悬赏金额
        tvReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRewardDialog();
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
                    ToastUtils.showShort(PublishTaskActivity.this,"限制在200字以内");
                    return;
                }
                tvWordNumber.setText(etDemand.getText().length()+"/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //分类查询
        tvCategoryQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PublishTaskActivity.this,CategoryListAct.class));
            }
        });

        //发布
        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPublish();
            }
        });


        //kefu
//        findViewById(R.id.icon_kefu).setVisibility(View.VISIBLE);
//        findViewById(R.id.icon_kefu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                forwardTokefuAct();
//            }
//        });
    }


    private void initData(){

        //请求标签
        BCHttpRequest.getQiMingInterface().getTagListApi("dg_task_tag")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TagList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TagList tagList) {
                        mTopTags.addAll(tagList.data);
                        setTopTagView();
                    }
                });

        //请求日期
        BCHttpRequest.getQiMingInterface().getTaskDateListApi("dg_task_days")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TaskDateList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TaskDateList taskDateList) {
                        mTaskDateTags.addAll(taskDateList.data);
                        for(int i = 0;i<mTaskDateTags.size();i++){
                            endTimeStrs.add(mTaskDateTags.get(i).itemContent);
                        }
                    }
                });

        //请求金额
        BCHttpRequest.getQiMingInterface().getTaskPriceListApi("dg_task_price")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TaskPriceList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TaskPriceList taskPriceList) {
                        mTaskPriceTags.addAll(taskPriceList.data);
                        for(int i = 0;i<mTaskPriceTags.size();i++){
                            rewardStrs.add(mTaskPriceTags.get(i).itemContent);
                        }
                    }
                });

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
                        mTaskCompanyTags.addAll(tradeMarkResponse.data.data);
                        for (int i=0;i<mTaskCompanyTags.size();i++){
                            companyStrs.add(mTaskCompanyTags.get(i).name);
                        }
                    }
                });


        //请求分类
        BCHttpRequest.getQiMingInterface().getTaskCategoryListApi(SP.getInstance().getString(SPTag.TAG_MEMBER_ID))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TaskCategoryList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TaskCategoryList taskPriceList) {
                        mTaskCategory.addAll(taskPriceList.data);
                        for(int i = 0;i<mTaskCategory.size();i++){
                            categoryStrs.add(mTaskCategory.get(i).getCategory_name());
                        }
                    }
                });
    }

    /**
     * 设置标签
     * */
    public void setTopTagView(){

        mTaglayout.removeAllViews();

        int size = mTopTags.size();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        layoutParams.setMargins(10, 0, 10, 0);

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        itemParams.setMargins(AppUtil.dip2px(this,4),AppUtil.dip2px(this,12),AppUtil.dip2px(this,4),AppUtil.dip2px(this,0));

        ArrayList<TagView> childBtns = new ArrayList<TagView>();

        int currentWidth = 0;
        for(int i = 0; i < size; i++){

            //1.初始化tagview
            TagView tagView = new TagView(this,mTopTags.get(i).itemContent,false,i,mTopTags.get(i).isSelected);
            tagView.getView().setLayoutParams(itemParams);
            tagView.getView().setTag(i);
            tagView.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTopTags.get((int)(Integer)v.getTag()).isSelected = !mTopTags.get((int)(Integer)v.getTag()).isSelected;
                    setTopTagView();
                }
            });
            childBtns.add(tagView);

            //2.判断当前的长度
            String item = mTopTags.get(i).itemContent;
            int length= item.length();
            currentWidth = currentWidth + (int)AppUtil.sp2px(this,14)*length + AppUtil.dip2px(this,20);
            currentWidth = currentWidth + AppUtil.dip2px(this,18);

            if(currentWidth > AppUtil.getWidth(this)){
                LinearLayout  horizLL = new LinearLayout(this);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for(TagView addBtn:childBtns.subList(0,childBtns.size()-1)){
                    horizLL.addView(addBtn.getView());
                }
                mTaglayout.addView(horizLL);

                childBtns.clear();
                currentWidth = 0;

                childBtns.add(tagView);
                currentWidth = currentWidth + (int)AppUtil.sp2px(this,14)*length + AppUtil.dip2px(this,20);
            }
        }


        LinearLayout.LayoutParams lastLinelayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        lastLinelayoutParams.setMargins(10, 0, 10, AppUtil.dip2px(this,10));

        //最后一行添加一下
        if(!childBtns.isEmpty()){
            LinearLayout horizLL = new LinearLayout(this);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);
            horizLL.setLayoutParams(lastLinelayoutParams);

            for(TagView addBtn:childBtns){
                horizLL.addView(addBtn.getView());
            }
            mTaglayout.addView(horizLL);
            childBtns.clear();
        }

    }

    /**
     * 显示行业Dialog
     * */
    private void showCompanyDialog(){
        if(companyStrs.isEmpty()){
            ToastUtils.showShort(this,"暂无行业");
        }

        if (chooseCompanyDialog == null) {
            chooseCompanyDialog = new ChooseDialog(this);
            chooseCompanyDialog.setDatas(companyStrs);
            chooseCompanyDialog.setOnClickItemPosition(this);
        }
        chooseCompanyDialog.show();
    }


    /**
     * 显示分类Dialog
     * */
    private void showCategoryDialog(){
        if(categoryStrs.isEmpty()){
            ToastUtils.showShort(this,"暂无分类");
        }

        if (chooseCategoryDialog == null) {
            chooseCategoryDialog = new ChooseDialog(this);
            chooseCategoryDialog.setDatas(categoryStrs);
            chooseCategoryDialog.setOnClickItemPosition(this);
        }
        chooseCategoryDialog.show();
    }


    /**
     * 显示截稿日期
     * */
    private void showEndTimeDialog(){
        if(endTimeStrs.isEmpty()){
            ToastUtils.showShort(this,"暂无可选项");
        }
        if (chooseEndTimeDialog == null) {
            chooseEndTimeDialog = new ChooseDialog(this);
            chooseEndTimeDialog.setDatas(endTimeStrs);
            chooseEndTimeDialog.setOnClickItemPosition(this);
        }
        chooseEndTimeDialog.show();
    }

    /**
     * 显示悬赏Dialog
     * */
    private void showRewardDialog(){
        if(rewardStrs.isEmpty()){
            ToastUtils.showShort(this,"暂无可选项");
        }
        if (chooseRewardDialog == null) {
            chooseRewardDialog = new ChooseDialog(this);
            chooseRewardDialog.setDatas(rewardStrs);
            chooseRewardDialog.setOnClickItemPosition(this);
        }
        chooseRewardDialog.show();
    }


    /**
     * 点击发布
     * */
    private void onClickPublish(){
        if(TextUtils.isEmpty(etTaskTitle.getText().toString().trim())){
            ToastUtils.showShort(this,"请输入标题");
            return;
        }

        if(type == 1){//商标
            if(categoryIndex == -1){
                ToastUtils.showShort(this,"请选择分类");
                return;
            }
        }else{//公司
            if(companyIndex == -1){
                ToastUtils.showShort(this,"请选择行业");
                return;
            }
        }

        if(TextUtils.isEmpty(getSelectedTag())){
            ToastUtils.showShort(this,"请选择需求标签");
            return;
        }

        if(TextUtils.isEmpty(etDemand.getText().toString().trim())){
            ToastUtils.showShort(this,"请输入具体需求");
            return;
        }

        if(dateIndex == -1){
            ToastUtils.showShort(this,"请选择截稿周期");
            return;
        }

        if(rewardIndex == -1){
            ToastUtils.showShort(this,"请选择悬赏金额");
            return;
        }

        if(payType == -1){
            ToastUtils.showShort(this,"请选择支付方式");
            return;
        }

        showProgressHUD(this,"发布中...");

        if(payType == 1){
            doPublishByZFB();
        }else{
            doPublishByWX();
        }
    }


    /**
     * 支付宝支付
     * */
    private void doPublishByZFB(){
        BCHttpRequest.getQiMingInterface().publishTaskByZFBApi(
                mTaskPriceTags.get(rewardIndex).getPublishPrice(),
                String.valueOf(payType),
                etTaskTitle.getText().toString().trim(),
                String.valueOf(type),
                type == 1? mTaskCategory.get(categoryIndex).getId() : mTaskCompanyTags.get(companyIndex).id,
                etDemand.getText().toString().trim(),
                getSelectedTag(),
                SP.getInstance().getString(SPTag.TAG_MEMBER_ID),
                mTaskDateTags.get(dateIndex).itemContent,
                "1",
                type == 1? mTaskCategory.get(categoryIndex).getCategory_name() : mTaskCompanyTags.get(companyIndex).name,
                NetUtil.getLocalIpAddress())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PublishTaskResultModelZFB>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                    }

                    @Override
                    public void onNext(PublishTaskResultModelZFB result) {
                        dismissProgressHUD();
                        if (result.fieldErrors.isEmpty()) {
                            AllPayRequestUtils.alipay(PublishTaskActivity.this, result.data, mHandler);
                        }
                        else {
                            ToastUtils.showShort(PublishTaskActivity.this, result.fieldErrors.get(0).getMessage());
                        }
                    }
                });
    }

    /**
     * 微信支付
     * */
    private void doPublishByWX(){

        BCHttpRequest.getQiMingInterface().publishTaskByWXApi(
                mTaskPriceTags.get(rewardIndex).getPublishPrice(),
                String.valueOf(payType),
                etTaskTitle.getText().toString().trim(),
                String.valueOf(type),
                type == 1? mTaskCategory.get(categoryIndex).getId() : mTaskCompanyTags.get(companyIndex).id,
                etDemand.getText().toString().trim(),
                getSelectedTag(),
                SP.getInstance().getString(SPTag.TAG_MEMBER_ID),
                mTaskDateTags.get(dateIndex).itemContent,
                "1",
                type == 1? mTaskCategory.get(categoryIndex).getCategory_name() : mTaskCompanyTags.get(companyIndex).name,
                NetUtil.getWifiIp(this))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PublishTaskResultModel>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                    }

                    @Override
                    public void onNext(PublishTaskResultModel result) {
                        dismissProgressHUD();
                        if (result.fieldErrors.isEmpty()) {
                            weiXinPay(result);
                        }
                        else {
                            ToastUtils.showShort(
                                    PublishTaskActivity.this,
                                    result.fieldErrors.get(0).getMessage());
                        }
                    }
                });

    }


    public String getSelectedTag()
    {
        String selectedTag = "";
        for(int i = 0;i<mTopTags.size();i++){
            if(mTopTags.get(i).isSelected){
                if(TextUtils.isEmpty(selectedTag)){
                    selectedTag = mTopTags.get(i).itemContent;
                }else{
                    selectedTag = selectedTag + "#" + mTopTags.get(i).itemContent;
                }
            }
        }

        return selectedTag;
    }


    //Dialog选择回调
    @Override
    public void onClickItem(int position, ChooseDialog dialog) {
        if (dialog == chooseCategoryDialog){
            tvCategory.setText(categoryStrs.get(position));
            categoryIndex = position;
        } else if (dialog == chooseEndTimeDialog){
            tvEndTime.setText(endTimeStrs.get(position)+"天");
            dateIndex = position;
        } else if (dialog == chooseRewardDialog){
            tvReward.setText(rewardStrs.get(position)+"元");
            rewardIndex = position;
        } else {
            tvIndustry.setText(companyStrs.get(position));
            companyIndex = position;
        }

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    /****************************支付回调****************************/
    @Override
    protected void onAlipaySuccess() {
        super.onAlipaySuccess();
        this.finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPublicStringEvent(PayEvent event) {
        if (PAYSUCCESS.equals(event.eventTitle)){
            this.finish();
        }
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
