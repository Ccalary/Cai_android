package com.bc.caibiao.ui.qiming;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomePageModel.TaskDetailInnerModel;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.TimeUtil;
import com.bc.caibiao.utils.TimeUtility;
import com.bc.caibiao.view.TagView;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/20.
 */

public class TaskDetailHeaderView  {

    TextView tvLabel;
    TextView tvTaskTitle;
    TextView tvPrice;


    TextView tvSubmissionNum;
    TextView tvEndTime;


    TextView tvTaskId;
    TextView tvDate;


    TextView tvIndustry_info;


    LinearLayout llv_taglayout_recommand;
    TextView tvTaskTip_more;

    TextView tv_publisher;
    TextView tv_sendcnt;


    ArrayList<String > mTopTags = new ArrayList();

    Context mContext;
    View mView;

    public TaskDetailHeaderView(Context aContext) {
        this.mContext = aContext;
        getView();
    }

    public View getView() {
        if (mView == null) {
            LayoutInflater aLayoutInflater = LayoutInflater.from(mContext);
            this.mView = aLayoutInflater.inflate(R.layout.headerview_taskdetail_view, null);
        }
        initUI();
        return mView;
    }

    @SuppressWarnings("rawtypes")
    private void initUI() {
        tvLabel = (TextView) mView.findViewById(R.id.tvLabel);
        tvTaskTitle = (TextView) mView.findViewById(R.id.tvTaskTitle);
        tvPrice = (TextView) mView.findViewById(R.id.tvPrice);

        tvSubmissionNum = (TextView) mView.findViewById(R.id.tvSubmissionNum);
        tvEndTime = (TextView) mView.findViewById(R.id.tvEndTime);

        tvTaskId = (TextView) mView.findViewById(R.id.tvTaskId);
        tvDate = (TextView) mView.findViewById(R.id.tvDate);

        tvIndustry_info = (TextView) mView.findViewById(R.id.tvIndustry_info);

        llv_taglayout_recommand = (LinearLayout) mView.findViewById(R.id.llv_taglayout_recommand);
        tvTaskTip_more = (TextView) mView.findViewById(R.id.tvTaskTip_more);

        tv_publisher = (TextView) mView.findViewById(R.id.tv_publisher);
        tv_sendcnt = (TextView) mView.findViewById(R.id.tv_sendcnt);
    }

    public void setData(TaskDetailInnerModel taskDetailInnerModel){
        if("1".equals(taskDetailInnerModel.taskMode)){
            tvLabel.setText("商标起名");
        }else{
            tvLabel.setText("公司起名");
        }

        tvTaskTitle.setText(taskDetailInnerModel.taskTitle);
        tvPrice.setText("¥"+taskDetailInnerModel.getFenPrice());

        tvSubmissionNum.setText(taskDetailInnerModel.touGaoNum + "个投稿");

        if("1".equals(taskDetailInnerModel.isXuanShangFenQian)){
            tvEndTime.setText("已截稿");
        }else{
            tvEndTime.setText(TimeUtility.getListTime(taskDetailInnerModel.expireTime)+"截稿");
        }


        tvTaskId.setText("任务编号："+taskDetailInnerModel.taskCode);
        tvDate.setText(TimeUtil.getFormatTimeFromTimestamp(taskDetailInnerModel.createdTimestamp*1000,"yyyy-MM-dd"));

        tvIndustry_info.setText("所属行业："+taskDetailInnerModel.categoryName);
        tvTaskTip_more.setText(taskDetailInnerModel.requireDetail);

        mTopTags = taskDetailInnerModel.getTedianList();
        setTagLayout();

        tv_publisher.setText("发布者:"+taskDetailInnerModel.creatorName);

        tv_sendcnt.setText("所有投稿("+taskDetailInnerModel.touGaoNum+")");
    }



    /**
     * 设置标签
     * */
    public void setTagLayout(){

        llv_taglayout_recommand.removeAllViews();

        int size = mTopTags.size();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        layoutParams.setMargins(10, 0, 10, 0);

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        itemParams.setMargins(AppUtil.dip2px(mContext,4),AppUtil.dip2px(mContext,12),AppUtil.dip2px(mContext,4),AppUtil.dip2px(mContext,0));

        ArrayList<TagView> childBtns = new ArrayList<TagView>();

        int currentWidth = 0;
        for(int i = 0; i < size; i++){

            //1.初始化tagview
            TagView tagView = new TagView((Activity) mContext,mTopTags.get(i),false,i,true);
            tagView.getView().setLayoutParams(itemParams);
            tagView.getView().setTag(i);
            childBtns.add(tagView);

            //2.判断当前的长度
            String item = mTopTags.get(i);
            int length= item.length();
            currentWidth = currentWidth + (int)AppUtil.sp2px(mContext,14)*length + AppUtil.dip2px(mContext,10);
            currentWidth = currentWidth + AppUtil.dip2px(mContext,18);

            if(currentWidth > AppUtil.getWidth(mContext)){
                LinearLayout  horizLL = new LinearLayout(mContext);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for(TagView addBtn:childBtns.subList(0,childBtns.size()-1)){
                    horizLL.addView(addBtn.getView());
                }
                llv_taglayout_recommand.addView(horizLL);

                childBtns.clear();
                currentWidth = 0;

                childBtns.add(tagView);
                currentWidth = currentWidth + (int)AppUtil.sp2px(mContext,14)*length + AppUtil.dip2px(mContext,20);
            }
        }


        LinearLayout.LayoutParams lastLinelayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        lastLinelayoutParams.setMargins(10, 0, 10, AppUtil.dip2px(mContext,10));

        //最后一行添加一下
        if(!childBtns.isEmpty()){
            LinearLayout horizLL = new LinearLayout(mContext);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);
            horizLL.setLayoutParams(lastLinelayoutParams);

            for(TagView addBtn:childBtns){
                horizLL.addView(addBtn.getView());
            }
            llv_taglayout_recommand.addView(horizLL);
            childBtns.clear();
        }

    }


}
