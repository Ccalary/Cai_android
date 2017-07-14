package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.MasterExampleAdapter;
import com.bc.caibiao.model.HomePageModel.DashiDetailModel;
import com.bc.caibiao.model.HomePageModel.TeacherModel;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.login.LoginActivity;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.ImageLoader;
import com.bc.caibiao.utils.MarkModuleUtil;
import com.bc.caibiao.view.TagView;
import com.bc.caibiao.widget.MNoScrollGridView;
import com.bc.caibiao.widget.MyScrollView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MarsterDetailActivity extends BaseActivity{

    @Bind(R.id.scrollview)
    MyScrollView myScrollView;

    @Bind(R.id.iv_Marster_Pic)
    SimpleDraweeView mIvPic;

    @Bind(R.id.tv_marster_Name)
    TextView mTvName;

    @Bind(R.id.tedian_layout)
    LinearLayout mTedianLayout;

    @Bind(R.id.dashi_desc)
    TextView mDesc;

    @Bind(R.id.grid_user_center)
    MNoScrollGridView mNoScrollGridView;

    TeacherModel mDashiDetailModel;
    String memberId;
    ArrayList<String> mTopTags = new ArrayList();
    MasterExampleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marster_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.ivLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.seemore_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDashiDetailModel == null)return;

                Intent aIntent = new Intent(MarsterDetailActivity.this,MasterDescAct.class);
                aIntent.putExtra("desc",mDashiDetailModel.memberExtend.introduce);
                startActivity(aIntent);
            }
        });

        findViewById(R.id.ll_qiming).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //是否登录
                if(!MarkModuleUtil.isLogin()){
                    Intent aIntent = new Intent(MarsterDetailActivity.this, LoginActivity.class);
                    startActivity(aIntent);
                    return;
                }


                Intent aIntent = new Intent(MarsterDetailActivity.this,PleaseManNameChildActivity.class);
                aIntent.putExtra("memberId",mDashiDetailModel.memberId);
                aIntent.putExtra("portrait",mDashiDetailModel.portrait);
                aIntent.putExtra("name",mDashiDetailModel.memberName);
                aIntent.putExtra("tagStr",mDashiDetailModel.memberExtend.teDian);
                startActivity(aIntent);
            }
        });
    }

    private void initData(){
        memberId = getIntent().getStringExtra("memberId");
        requestData();
    }

    private void requestData(){

        showProgressHUD(this,"加载中");

        BCHttpRequest.getQiMingInterface().getTeacherDetail(memberId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DashiDetailModel>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onNext(DashiDetailModel dashiDetailModel) {
                        dismissProgressHUD();
                        mDashiDetailModel = dashiDetailModel.data;
                        loadData();
                    }
                });
    }

    private void loadData(){
        ImageLoader.progressiveLoad(mDashiDetailModel.portrait,mIvPic);
        mTvName.setText(mDashiDetailModel.memberName);
        mTopTags = mDashiDetailModel.memberExtend.getTedianList();
        loadTedian();
        mDesc.setText(mDashiDetailModel.memberExtend.introduce);

        loadAdapter();
    }

    private void loadTedian(){
        mTedianLayout.removeAllViews();

        int size = mTopTags.size();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        layoutParams.setMargins(10, 0, 10, 0);

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        itemParams.setMargins(AppUtil.dip2px(this,4),AppUtil.dip2px(this,12),AppUtil.dip2px(this,4),AppUtil.dip2px(this,0));

        ArrayList<TagView> childBtns = new ArrayList<TagView>();

        int currentWidth = 0;
        for(int i = 0; i < size; i++){

            //1.初始化tagview
            TagView tagView = new TagView(this,mTopTags.get(i),false,i,true);
            tagView.getView().setLayoutParams(itemParams);
            tagView.getView().setTag(i);
            childBtns.add(tagView);

            //2.判断当前的长度
            String item = mTopTags.get(i);
            int length= item.length();
            currentWidth = currentWidth + (int)AppUtil.sp2px(this,14)*length + AppUtil.dip2px(this,10);
            currentWidth = currentWidth + AppUtil.dip2px(this,18);

            if(currentWidth > AppUtil.getWidth(this) - AppUtil.dip2px(mContext,20)){
                LinearLayout  horizLL = new LinearLayout(this);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for(TagView addBtn:childBtns.subList(0,childBtns.size()-1)){
                    horizLL.addView(addBtn.getView());
                }
                mTedianLayout.addView(horizLL);

                childBtns.clear();
                currentWidth = 0;

                childBtns.add(tagView);
                currentWidth = currentWidth + (int)AppUtil.sp2px(this,14)*length + AppUtil.dip2px(this,10);
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
            mTedianLayout.addView(horizLL);
            childBtns.clear();
        }
    }


    private void loadAdapter(){
        mAdapter = new MasterExampleAdapter(this, mDashiDetailModel.caseList);
        mNoScrollGridView.setAdapter(mAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myScrollView.scrollTo(0,0);
            }
        },50);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


}
