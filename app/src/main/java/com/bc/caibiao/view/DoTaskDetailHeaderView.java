package com.bc.caibiao.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomePageModel.TaskDetailInnerModel;
import com.bc.caibiao.utils.TimeUtil;

/**
 * Created by chengyanfang on 2017/4/22.
 */

public class DoTaskDetailHeaderView  {

    Context mContext;
    View mView;

    TextView mLabelView,mTitleView,mPriceView;


    TextView mContent1,mContent2,mContent3,mContent4,mContent5,mContent6,mDateTime;

    public DoTaskDetailHeaderView(Context aContext) {
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
}
