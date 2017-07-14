package com.bc.caibiao.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.CoverFlowAdapter;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.model.HomePageModel.HomePageModel;
import com.bc.caibiao.ui.qiming.BigTeacherListAct;
import com.bc.caibiao.ui.qiming.MarsterDetailActivity;
import com.bc.caibiao.ui.qiming.SmartNameActivity;
import com.bc.caibiao.ui.qiming.TaskListActivity;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.Config;
import com.bc.caibiao.widget.ConvenientBanner.CBViewHolderCreator;
import com.bc.caibiao.widget.ConvenientBanner.ConvenientBanner;
import com.bc.caibiao.widget.ConvenientBanner.NetworkImageHolderView;
import com.bumptech.glide.Glide;

/**
 * Created by chengyanfang on 2017/4/17.
 */

public class HomeBannerView {

    Context mContext;
    View mView;
    HomePageModel mHomePageModel;
    ConvenientBanner convenientBanner;

    OnePageGallery gallery = null;
    CoverFlowAdapter mAdapter;

    public HomeBannerView(Context aContext) {
        this.mContext = aContext;
        getView();
    }

    public View getView() {
        if (mView == null) {
            LayoutInflater aLayoutInflater = LayoutInflater.from(mContext);
            this.mView = aLayoutInflater.inflate(R.layout.headerview_qiming, null);
        }
        initUI();
        return mView;
    }

    @SuppressWarnings("rawtypes")
    private void initUI() {
        convenientBanner = (ConvenientBanner) mView.findViewById(R.id.convenientBanner);
        convenientBanner.setLayoutParams(new RelativeLayout.LayoutParams(AppUtil.getWidth(mContext), AppUtil.dip2px(mContext,170)));

        gallery = (OnePageGallery) mView.findViewById(R.id.gallery1);
        gallery.setSpacing(AppUtil.dip2px(mContext,15));
        //自助起名
        mView.findViewById(R.id.llChaxun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SmartNameActivity.class));
            }
        });

        //悬赏起名
        mView.findViewById(R.id.llFenlei).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, TaskListActivity.class));
            }
        });

        //大师起名
        mView.findViewById(R.id.llChushou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BigTeacherListAct.class));
            }
        });

        //查看更多大师
        mView.findViewById(R.id.tv_seemore_teacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BigTeacherListAct.class));
            }
        });

        //查看更多任务
        mView.findViewById(R.id.see_more_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, TaskListActivity.class));
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void setViewData(HomePageModel homePageModel) {
        mHomePageModel = homePageModel;

        //设置Banner
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, homePageModel.listAds, URLConfig.baseUrl_pic_oss)
                .setPageIndicator(new int[] { R.drawable.jjs_img_point_normal, R.drawable.jjs_img_point_pressed })
                .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);


        setDashiGallery();

        startScrolling();
    }

    public RelativeLayout.LayoutParams getLayoutParams() {
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lps.setMargins(AppUtil.dip2px(mContext, -5), AppUtil.dip2px(mContext, -5), AppUtil.dip2px(mContext, -5), 0);
        return lps;
    }

    public void startScrolling() {
        if (convenientBanner != null) {
            convenientBanner.startTurning(4000);
        }
    }

    public void stopScrolling() {
        if (convenientBanner != null) {
            convenientBanner.stopTurning();
        }
    }


    private void setDashiGallery(){
        mAdapter =  new CoverFlowAdapter(mContext);
        mAdapter.setData(mHomePageModel.listDaShi);
        gallery.setAdapter(mAdapter);
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemSelected(AdapterView<?> parent, View v,int position, long id) {
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(v, "scaleY", 0.7f, 1.05f);
                imgScaleUpYAnim.setDuration(600);
                //imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
                ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(v, "scaleX", 0.7f, 1.05f);
                imgScaleUpXAnim.setDuration(600);
                animatorSet.playTogether(imgScaleUpYAnim,imgScaleUpXAnim);
                animatorSet.start();

                for(int i = 0;i < parent.getChildCount();i++){
                    if(parent.getChildAt(i) != v){
                        View s = parent.getChildAt(i);
                        ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(s, "scaleY", 1.05f, 0.7f);
                        imgScaleDownYAnim.setDuration(100);
                        //imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
                        ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(s, "scaleX", 1.05f, 0.7f);
                        imgScaleDownXAnim.setDuration(100);
                        animatorSet.playTogether(imgScaleDownXAnim,imgScaleDownYAnim);
                        animatorSet.start();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent aIntent = new Intent(mContext,MarsterDetailActivity.class);
                aIntent.putExtra("memberId",mHomePageModel.listDaShi.get(position).memberId);
                mContext.startActivity(aIntent);
            }
        });

        gallery.setSelection(1,true);
    }

}
