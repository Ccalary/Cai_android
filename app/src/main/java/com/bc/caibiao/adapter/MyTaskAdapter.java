package com.bc.caibiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomePageModel.PlaygroundTask;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.StringUtil;
import com.bc.caibiao.utils.TimeUtility;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/22.
 */

public class MyTaskAdapter extends BaseRecyclerAdapter<PlaygroundTask> {

    String mCelltype;


    public MyTaskAdapter(Context context, ArrayList<PlaygroundTask> datas,String type) {
        super(context, datas);
        mCelltype = type;
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_my_task;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        final PlaygroundTask aPlaygroundTask = mDatas.get(position);

        aItemViewHolder.mTitle.setText(aPlaygroundTask.taskTitle);

        if("1".equals(aPlaygroundTask.namedCategory)){
            aItemViewHolder.mType.setText("商标起名");
            aItemViewHolder.mType.setBackgroundResource(R.drawable.bg_tag_8f6fe9);
            aItemViewHolder.mType.setTextColor(mContext.getResources().getColor(R.color.color_8f6fe9));
        }else{
            aItemViewHolder.mType.setText("公司起名");
            aItemViewHolder.mType.setBackgroundResource(R.drawable.bg_tag_529ce7);
            aItemViewHolder.mType.setTextColor(mContext.getResources().getColor(R.color.color_529ce7));
        }

        aItemViewHolder.mPrice.setText("¥"+ StringUtil.getFormatedFloatString(aPlaygroundTask.getPrice()));


        if("1".equals(aPlaygroundTask.isXuanShangFenQian)){
            aItemViewHolder.mDate.setText("已经截稿");
        }else{
            aItemViewHolder.mDate.setText(TimeUtility.getListTime(aPlaygroundTask.expireTime)+"截稿");
        }


        //大师任务
        if("1".equals(mCelltype)||"4".equals(mCelltype)){
            aItemViewHolder.mSignCnt.setText(aPlaygroundTask.touGaoNum+ "人投稿");
        }else if("2".equals(mCelltype)){
            aItemViewHolder.mSignCnt.setTextColor(mContext.getResources().getColor(R.color.base_gray_al));
            if("1".equals(aPlaygroundTask.isXuanShangFenQian)){
                aItemViewHolder.mSignCnt.setText("已采纳");
            }else{
                aItemViewHolder.mSignCnt.setText("未采纳");
            }
        }else{
            aItemViewHolder.mSignCnt.setTextColor(mContext.getResources().getColor(R.color.base_gray));
            aItemViewHolder.mDate.setVisibility(View.GONE);
            if(TextUtils.isEmpty(aPlaygroundTask.dashiMemberName)||"null".equals(aPlaygroundTask.dashiMemberName)){
                aItemViewHolder.mSignCnt.setText("大师");
            }else{
                aItemViewHolder.mSignCnt.setText(Html.fromHtml("<font color='#FF6600'>"+aPlaygroundTask.dashiMemberName+ "</font><font color='#7b7b81'>大师</font>"));
            }
        }

        if("4".equals(mCelltype)){
            aItemViewHolder.mDate.setVisibility(View.GONE);
        }


        if (mItemClickListener != null) {
            aItemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(null, null, position, 0);
                }
            });
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public View mView;

        public TextView mTitle;
        public TextView mType;
        public TextView mDate;
        public TextView mPrice;
        public TextView mSignCnt;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;

            mTitle = (TextView) arg0.findViewById(R.id.tvTitle);
            mType = (TextView) arg0.findViewById(R.id.tvLabel);
            mDate = (TextView) arg0.findViewById(R.id.tvEndTime);
            mPrice = (TextView) arg0.findViewById(R.id.tvMoney);
            mSignCnt = (TextView) arg0.findViewById(R.id.tv_sing_cnt);
        }
    }
}
