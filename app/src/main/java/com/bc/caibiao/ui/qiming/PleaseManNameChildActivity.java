package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.DashiPackageAdapter;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModel;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModelZFB;
import com.bc.caibiao.model.HomePageModel.SignPrice;
import com.bc.caibiao.model.HomePageModel.SignPriceList;
import com.bc.caibiao.model.MarkModel.TaskCategory;
import com.bc.caibiao.model.MarkModel.TaskCategoryList;
import com.bc.caibiao.model.MarkModel.TaskPrice;
import com.bc.caibiao.model.MarkModel.TaskPriceList;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.ImageLoader;
import com.bc.caibiao.utils.NetUtil;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.StringUtil;
import com.bc.caibiao.utils.TimeUtil;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.ChooseDialog;
import com.bc.caibiao.view.MyListView;
import com.bc.caibiao.view.TagView;
import com.bc.caibiao.view.TopBarLayout;
import com.bc.caibiao.widget.DatePicker.PXFDatePickerYM;
import com.bc.caibiao.widget.MyScrollView;
import com.bc.caibiao.wxapi.AllPayRequestUtils;
import com.bc.caibiao.wxapi.PayEvent;
import com.bc.caibiao.wxapi.PayResult;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bc.caibiao.R.id.llBrandGetName;
import static com.bc.caibiao.R.id.llCompanyGetName;
import static com.bc.caibiao.R.id.tvCategory;
import static com.bc.caibiao.R.id.tvCategoryQuery;
import static com.bc.caibiao.R.id.tvIndustry;
import static com.bc.caibiao.wxapi.WXPayEntryActivity.PAYSUCCESS;

/**
 * Created by hanxing on 2017/4/19.
 */

/**
 * 请他起名
 */
public class PleaseManNameChildActivity extends BaseActivity implements ChooseDialog.OnClickItemPosition {

    @Bind(R.id.id_tablayout)
    TopBarLayout mTopLayout;

    @Bind(R.id.scrollview)
    MyScrollView myScrollView;

    //头像
    @Bind(R.id.iv_Marster_Pic)
    SimpleDraweeView mHeadPortrait;
    //名字
    @Bind(R.id.please_man_name_child_name)
    TextView mNameTv;
    //标签
    @Bind(R.id.tedian_layout)
    LinearLayout mLabelLayout;


    //商标起名
    @Bind(R.id.tvBrandGetName)
    RadioButton mTvBrandGetName;
    //公司起名
    @Bind(R.id.tvCompanyGetName)
    RadioButton mTvCompanyGetName;
    //
    @Bind(R.id.type_group)
    RadioGroup mTypeGroup;


    //标题
    @Bind(R.id.etTaskTitle)
    EditText mEtTaskTitle;
    //行业
    @Bind(llCompanyGetName)
    LinearLayout mLlCompanyGetName;
    //行业——请选择
    @Bind(tvIndustry)
    TextView mTvIndustry;
    //分类查询
    @Bind(tvCategoryQuery)
    TextView mTvCategoryQuery;
    //分类——请选择
    @Bind(tvCategory)
    TextView mTvCategory;
    //分类
    @Bind(llBrandGetName)
    LinearLayout mLlBrandGetName;


    //老板名字
    @Bind(R.id.please_man_name_child_boss_name)
    EditText mBossNameEdit;
    //字数
    @Bind(R.id.please_man_name_child_number)
    EditText mNumberEdit;
    //生日
    @Bind(R.id.please_man_name_child_birthday)
    TextView mBirthdayTv;
    //出生时刻
    @Bind(R.id.please_man_name_child_birth_time)
    TextView mBirthTimeTv;

    //要求内容
    @Bind(R.id.etDemand)
    EditText etDemand;
    //紫薯限制
    @Bind(R.id.tvWordNumber)
    TextView tvWordNumber;

    //支付方式
    @Bind(R.id.pay_type_group)
    RadioGroup mPayTypeGroup;

    @Bind(R.id.publich)
    Button tvPublish;

    DashiPackageAdapter mDashiPackageAdapter;

    //选择列表
    private ChooseDialog chooseCompanyDialog;
    private ChooseDialog chooseCategoryDialog;
    private ChooseDialog chooseHourDialog;
    private PXFDatePickerYM mPXFDatePickerYM;

    List<String> companyStrs = new ArrayList<>();
    List<String> categoryStrs = new ArrayList<>();
    List<String> birthHour = new ArrayList<>();
    ArrayList<TaskPrice> mTaskCompanyTags = new ArrayList();
    ArrayList<TaskCategory> mTaskCategory = new ArrayList();
    ArrayList<SignPrice> mSignPriceList = new ArrayList();

    //分类：1、商标起名 2、公司起名
    int type = 1;

    //支付方式：1.支付宝、2、微信
    int payType = -1;

    int companyIndex = -1, categoryIndex = -1, hourIndex = -1;

    String memberId;
    ArrayList<String> tagStrList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_please_man_name_child_layout);
        ButterKnife.bind(this);

        initView();
        initDatas();
    }

    private void initView() {
        mTopLayout.showLeft();
        mTopLayout.setTitle("请他起名");

        mTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.tvBrandGetName) {
                    type = 1;
                    mLlBrandGetName.setVisibility(View.VISIBLE);
                    mLlCompanyGetName.setVisibility(View.GONE);
                }
                else {
                    type = 2;
                    mLlBrandGetName.setVisibility(View.GONE);
                    mLlCompanyGetName.setVisibility(View.VISIBLE);
                }
            }
        });

        //行业选择
        mTvIndustry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCompanyDialog();
            }
        });

        //分类选择
        mTvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialog();
            }
        });

        //出生时刻
        mBirthTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHourDialog();
            }
        });

        //出生日期
        mBirthdayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBirthdayTv.getText().toString()) || mBirthdayTv.getText().toString().equals("请选择")) {
                    mPXFDatePickerYM.show(TimeUtil.getYMD(new Date()));
                }
                else {
                    mPXFDatePickerYM.show(mBirthdayTv.getText().toString());
                }
            }
        });

        //分类查询
        mTvCategoryQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PleaseManNameChildActivity.this, CategoryListAct.class));
            }
        });

        //出生日期
        mPXFDatePickerYM = new PXFDatePickerYM(this, new PXFDatePickerYM.ResultHandler() {
            @Override
            public void handle(String time) {
                mBirthdayTv.setText(time.split(" ")[0]);
            }
        }, "1926-01-01 00:00", "2080-01-01 00:00");
        mPXFDatePickerYM.showSpecificTime(2); // 不显示时和分
        mPXFDatePickerYM.setIsLoop(false);

        //详细要求
        etDemand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etDemand.getText().toString().length() == 200) {
                    etDemand.setText(etDemand.getText().toString().substring(0, 199));
                    ToastUtils.showShort(PleaseManNameChildActivity.this, "限制在200字以内");
                    return;
                }
                tvWordNumber.setText(etDemand.getText().length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mPayTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.zfb_pay) {
                    payType = 1;
                }
                else {
                    payType = 2;
                }
            }
        });

        findViewById(R.id.publich).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPush();
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

    private void initDatas() {
        ImageLoader.progressiveLoad(getIntent().getStringExtra("portrait"), mHeadPortrait);
        mNameTv.setText(getIntent().getStringExtra("name"));
        tagStrList = StringUtil.getTedianList(getIntent().getStringExtra("tagStr"));
        memberId = getIntent().getStringExtra("memberId");
        loadTedian();

        for (int i = 0; i < 24; i++) {
            birthHour.add(i + "");
        }

        //请求行业
        BCHttpRequest.getQiMingInterface().getTaskPriceListApi("dg_company_category")
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
                        mTaskCompanyTags.addAll(taskPriceList.data);
                        for (int i = 0; i < mTaskCompanyTags.size(); i++) {
                            companyStrs.add(mTaskCompanyTags.get(i).itemContent);
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
                        for (int i = 0; i < mTaskCategory.size(); i++) {
                            categoryStrs.add(mTaskCategory.get(i).getCategory_name());
                        }
                    }
                });

        //请求分类
        BCHttpRequest.getQiMingInterface().getSignPriceListApi(memberId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SignPriceList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SignPriceList signPriceList) {
                        mSignPriceList.addAll(signPriceList.data);

                        loadAdapter();
                    }
                });

    }


    private void loadTedian() {
        mLabelLayout.removeAllViews();

        int size = tagStrList.size();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        layoutParams.setMargins(10, 0, 10, 0);

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        itemParams.setMargins(AppUtil.dip2px(this, 4), AppUtil.dip2px(this, 12), AppUtil.dip2px(this, 4), AppUtil.dip2px(this, 0));

        ArrayList<TagView> childBtns = new ArrayList<TagView>();

        int currentWidth = 0;
        for (int i = 0; i < size; i++) {

            //1.初始化tagview
            TagView tagView = new TagView(this, tagStrList.get(i), false, i, true);
            tagView.getView().setLayoutParams(itemParams);
            tagView.getView().setTag(i);
            childBtns.add(tagView);

            //2.判断当前的长度
            String item = tagStrList.get(i);
            int length = item.length();
            currentWidth = currentWidth + (int) AppUtil.sp2px(this, 14) * length + AppUtil.dip2px(this,10);
            currentWidth = currentWidth + AppUtil.dip2px(this, 18);

            if (currentWidth > AppUtil.getWidth(this) - AppUtil.dip2px(this, 100)) {
                LinearLayout horizLL = new LinearLayout(this);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for (TagView addBtn : childBtns.subList(0, childBtns.size() - 1)) {
                    horizLL.addView(addBtn.getView());
                }
                mLabelLayout.addView(horizLL);

                childBtns.clear();
                currentWidth = 0;

                childBtns.add(tagView);
                currentWidth = currentWidth + (int) AppUtil.sp2px(this, 14) * length + AppUtil.dip2px(this, 10);
            }
        }

        LinearLayout.LayoutParams lastLinelayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        lastLinelayoutParams.setMargins(10, 0, 10, AppUtil.dip2px(this, 10));

        //最后一行添加一下
        if (!childBtns.isEmpty()) {
            LinearLayout horizLL = new LinearLayout(this);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);
            horizLL.setLayoutParams(lastLinelayoutParams);

            for (TagView addBtn : childBtns) {
                horizLL.addView(addBtn.getView());
            }
            mLabelLayout.addView(horizLL);
            childBtns.clear();
        }
    }


    /**
     * 显示行业Dialog
     */
    private void showCompanyDialog() {
        if (companyStrs.isEmpty()) {
            ToastUtils.showShort(this, "暂无行业");
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
     */
    private void showCategoryDialog() {
        if (categoryStrs.isEmpty()) {
            ToastUtils.showShort(this, "暂无分类");
        }

        if (chooseCategoryDialog == null) {
            chooseCategoryDialog = new ChooseDialog(this);
            chooseCategoryDialog.setDatas(categoryStrs);
            chooseCategoryDialog.setOnClickItemPosition(this);
        }
        chooseCategoryDialog.show();
    }

    /**
     * 显示出生时刻Dialog
     */
    private void showHourDialog() {

        if (chooseHourDialog == null) {
            chooseHourDialog = new ChooseDialog(this);
            chooseHourDialog.setDatas(birthHour);
            chooseHourDialog.setOnClickItemPosition(this);
        }
        chooseHourDialog.show();
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    //Dialog选择回调
    @Override
    public void onClickItem(int position, ChooseDialog dialog) {
        if (dialog == chooseCategoryDialog) {
            mTvCategory.setText(categoryStrs.get(position));
            categoryIndex = position;
        }
        else if (dialog == chooseCompanyDialog) {
            mTvIndustry.setText(companyStrs.get(position));
            companyIndex = position;
        }
        else {
            mBirthTimeTv.setText(birthHour.get(position)+"点");
            hourIndex = position + 1;
        }
    }


    @Bind(R.id.listview)
    MyListView myListView;

    private void loadAdapter() {
        if (mDashiPackageAdapter == null) {
            mDashiPackageAdapter = new DashiPackageAdapter(this, mSignPriceList);
            myListView.setAdapter(mDashiPackageAdapter);

            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < mSignPriceList.size(); i++) {
                        if (i == position) {
                            mSignPriceList.get(i).isSelected = true;
                        }
                        else {
                            mSignPriceList.get(i).isSelected = false;
                        }
                    }
                    mDashiPackageAdapter.notifyDataSetChanged();
                }
            });
        }
        else {
            mDashiPackageAdapter.notifyDataSetChanged();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myScrollView.scrollTo(0, 0);
            }
        }, 50);
    }

    private void onClickPush() {
        if (TextUtils.isEmpty(mEtTaskTitle.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入标题");
            return;
        }

        if (type == 1) {//商标
            if (categoryIndex == -1) {
                ToastUtils.showShort(this, "请选择分类");
                return;
            }
        }
        else {//公司
            if (companyIndex == -1) {
                ToastUtils.showShort(this, "请选择行业");
                return;
            }
        }

        if (TextUtils.isEmpty(etDemand.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入具体需求");
            return;
        }


        if (TextUtils.isEmpty(mBossNameEdit.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入老板名字");
            return;
        }

        if (TextUtils.isEmpty(mNumberEdit.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入字数");
            return;
        }


        if (TextUtils.isEmpty(mBirthdayTv.getText().toString()) || mBirthdayTv.getText().toString().equals("请选择")) {
            ToastUtils.showShort(this, "请选择生日");
            return;
        }

        if (hourIndex == -1) {
            ToastUtils.showShort(this, "请选择出生时刻");
            return;
        }

        if (payType == -1) {
            ToastUtils.showShort(this, "请选择支付方式");
            return;
        }

        if (getSelectPackages().size() == 0) {
            ToastUtils.showShort(this, "请选择套餐");
            return;
        }

        doPublish();

    }

    private ArrayList<SignPrice> getSelectPackages() {
        ArrayList<SignPrice> selectedPrice = new ArrayList<>();
        for (SignPrice signPrice : mSignPriceList) {
            if (signPrice.isSelected) {
                selectedPrice.add(signPrice);
            }
        }
        return selectedPrice;
    }


    private void doPublish() {

        showProgressHUD(this, "发布中");

        //支付方式：1.支付宝、2、微信
        if (payType == 1) {
            useZFBPay();
        }
        else {
            useWXPay();
        }
    }

    /**
     * 使用支付宝支付
     */
    private void useZFBPay() {
        BCHttpRequest.getQiMingInterface().pleaseTeacherSignZFB(
                type == 1 ? mTaskCategory.get(categoryIndex).getCategory_name() : mTaskCompanyTags.get(companyIndex).itemContent,
                NetUtil.getWifiIp(this),
                String.valueOf(type),
                String.valueOf(payType),
                mEtTaskTitle.getText().toString().trim(),
                "2",
                String.valueOf(getSelectPackages().get(0).getPrice()),
                SP.getInstance().getString(SPTag.TAG_MEMBER_ID),
                TimeUtil.getDesTimeStamp(mBirthdayTv.getText().toString()),
                String.valueOf(hourIndex),
                mBossNameEdit.getText().toString().trim(),
                mNumberEdit.getText().toString().trim(),
                etDemand.getText().toString().trim(),
                memberId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PublishTaskResultModelZFB>() {
                    @Override
                    public void onCompleted() {

                        dismissProgressHUD();

                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onNext(PublishTaskResultModelZFB result) {
                        dismissProgressHUD();
                        if (result.fieldErrors.isEmpty()) {

                            AllPayRequestUtils.alipay(PleaseManNameChildActivity.this, result.data, mHandler);
                        }
                        else {
                            ToastUtils.showShort(PleaseManNameChildActivity.this, result.fieldErrors.get(0).getMessage());
                        }
                    }
                });
    }

    /**
     * 使用微信支付
     */
    private void useWXPay() {
        BCHttpRequest.getQiMingInterface().pleaseTeacherSign(
                type == 1 ? mTaskCategory.get(categoryIndex).getCategory_name() : mTaskCompanyTags.get(companyIndex).itemContent,
                NetUtil.getWifiIp(this),
                String.valueOf(type),
                String.valueOf(payType),
                mEtTaskTitle.getText().toString().trim(),
                "2",
                String.valueOf(getSelectPackages().get(0).getPrice()),
                SP.getInstance().getString(SPTag.TAG_MEMBER_ID),
                TimeUtil.getDesTimeStamp(mBirthdayTv.getText().toString()),
                String.valueOf(hourIndex),
                mBossNameEdit.getText().toString().trim(),
                mNumberEdit.getText().toString().trim(),
                etDemand.getText().toString().trim(),
                memberId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PublishTaskResultModel>() {
                    @Override
                    public void onCompleted() {

                        dismissProgressHUD();

                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onNext(PublishTaskResultModel result) {
                        dismissProgressHUD();
                        if (result.fieldErrors.isEmpty()) {

                            weiXinPay(result);

                        }
                        else {
                            ToastUtils.showShort(
                                    PleaseManNameChildActivity.this,
                                    result.fieldErrors.get(0).getMessage());
                        }
                    }
                });
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
