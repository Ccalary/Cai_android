package com.bc.caibiao.ui.me;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomePageModel.TaskDetailInnerModel;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.ImageLoader;
import com.bc.caibiao.utils.StringUtil;
import com.bc.caibiao.utils.TimeUtil;
import com.bc.caibiao.view.TagView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by chengyanfang on 2017/4/22.
 */

public class DoSignHeaderView  {

    Context mContext;
    View mView;

    TextView mLabelView,mTitleView,mPriceView;


    TextView mContent1,mContent2,mContent3,mContent4,mContent5,mContent6,mDateTime;

    RelativeLayout mDashiLayout;

    //头像
    SimpleDraweeView mHeadPortrait;
    //名字
    TextView mNameTv;
    //标签
    LinearLayout mLabelLayout;

    ArrayList<String> tagStrList = new ArrayList<>();

    public DoSignHeaderView(Context aContext) {
        this.mContext = aContext;
        getView();
    }

    public View getView() {
        if (mView == null) {
            LayoutInflater aLayoutInflater = LayoutInflater.from(mContext);
            this.mView = aLayoutInflater.inflate(R.layout.headerview_dosign_header, null);
        }
        initUI();
        return mView;
    }

    @SuppressWarnings("rawtypes")
    private void initUI() {

        mLabelView = (TextView)mView.findViewById(R.id.tvLabel);
        mTitleView = (TextView)mView.findViewById(R.id.tvTitle);
        mPriceView = (TextView)mView.findViewById(R.id.tvMoney);

        mContent1 = (TextView)mView.findViewById(R.id.second_title);
        mDateTime = (TextView)mView.findViewById(R.id.time_tv);
        mContent2 = (TextView)mView.findViewById(R.id.second_title2);
        mContent3 = (TextView)mView.findViewById(R.id.second_title3);
        mContent4 = (TextView)mView.findViewById(R.id.second_title4);
        mContent5 = (TextView)mView.findViewById(R.id.second_title5);
        mContent6 = (TextView)mView.findViewById(R.id.second_title6);

        mDashiLayout = (RelativeLayout) mView.findViewById(R.id.rlv_dashi_layout);

        mHeadPortrait = (SimpleDraweeView)mView.findViewById(R.id.iv_Marster_Pic);
        mNameTv = (TextView) mView.findViewById(R.id.please_man_name_child_name);
        mLabelLayout = (LinearLayout) mView.findViewById(R.id.tedian_layout);
    }

    @SuppressWarnings("unchecked")
    public void setViewData(TaskDetailInnerModel taskDetailModel) {
        if("1".equals(taskDetailModel.namedCategory)){
            mLabelView.setText("商标起名");
            mLabelView.setBackgroundResource(R.drawable.bg_tag_8f6fe9);
            mLabelView.setTextColor(mContext.getResources().getColor(R.color.color_8f6fe9));
        }else{
            mLabelView.setText("公司起名");
            mLabelView.setBackgroundResource(R.drawable.bg_tag_529ce7);
            mLabelView.setTextColor(mContext.getResources().getColor(R.color.color_529ce7));
        }

        mTitleView.setText(taskDetailModel.taskTitle);
        mPriceView.setText("¥"+taskDetailModel.getFenPrice());


        mContent1.setText(taskDetailModel.taskCode);
        mContent2.setText(taskDetailModel.categoryName);
        mContent3.setText(taskDetailModel.wordsNum);
        mContent4.setText(taskDetailModel.birthhour);
        mContent5.setText(taskDetailModel.requireDetail);
        mContent6.setText(taskDetailModel.creatorName);
        mDateTime.setText(TimeUtil.getFormatTimeFromTimestamp(taskDetailModel.createdTimestamp*1000,"yyyy-MM-dd"));

    }

    public void setDashiInfo(TaskDetailInnerModel taskDetailModel){
        if(!TextUtils.isEmpty(taskDetailModel.dashiPortrait)){
            ImageLoader.progressiveLoad(taskDetailModel.dashiPortrait, mHeadPortrait);
        }
        mNameTv.setText(taskDetailModel.dashiName);
        String[] tags = taskDetailModel.teDian.split("#");
        for(int i = 0;i<tags.length;i++){
            tagStrList.add(tags[i]);
        }

        loadTedian();
    }

    public void dismissDashiInfo(){
        mDashiLayout.setVisibility(View.GONE);
    }


    private void loadTedian() {
        mLabelLayout.removeAllViews();

        int size = tagStrList.size();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        layoutParams.setMargins(10, 0, 10, 0);

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        itemParams.setMargins(AppUtil.dip2px(mContext, 4), AppUtil.dip2px(mContext, 12), AppUtil.dip2px(mContext, 4), AppUtil.dip2px(mContext, 0));

        ArrayList<TagView> childBtns = new ArrayList<TagView>();

        int currentWidth = 0;
        for (int i = 0; i < size; i++) {

            //1.初始化tagview
            TagView tagView = new TagView((Activity) mContext, tagStrList.get(i), false, i, true);
            tagView.getView().setLayoutParams(itemParams);
            tagView.getView().setTag(i);
            childBtns.add(tagView);

            //2.判断当前的长度
            String item = tagStrList.get(i);
            int length = item.length();
            currentWidth = currentWidth + (int) AppUtil.sp2px(mContext, 14) * length + AppUtil.dip2px(mContext, 10);
            currentWidth = currentWidth + AppUtil.dip2px(mContext, 18);

            if (currentWidth > AppUtil.getWidth(mContext) - AppUtil.dip2px(mContext, 80)) {
                LinearLayout horizLL = new LinearLayout(mContext);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for (TagView addBtn : childBtns.subList(0, childBtns.size() - 1)) {
                    horizLL.addView(addBtn.getView());
                }
                mLabelLayout.addView(horizLL);

                childBtns.clear();
                currentWidth = 0;

                childBtns.add(tagView);
                currentWidth = currentWidth + (int) AppUtil.sp2px(mContext, 14) * length + AppUtil.dip2px(mContext, 20);
            }
        }

        LinearLayout.LayoutParams lastLinelayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        lastLinelayoutParams.setMargins(10, 0, 10, AppUtil.dip2px(mContext, 10));

        //最后一行添加一下
        if (!childBtns.isEmpty()) {
            LinearLayout horizLL = new LinearLayout(mContext);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);
            horizLL.setLayoutParams(lastLinelayoutParams);

            for (TagView addBtn : childBtns) {
                horizLL.addView(addBtn.getView());
            }
            mLabelLayout.addView(horizLL);
            childBtns.clear();
        }
    }

}

