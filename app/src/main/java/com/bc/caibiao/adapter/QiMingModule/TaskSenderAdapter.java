package com.bc.caibiao.adapter.QiMingModule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.TouGaoer;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.ImageLoader;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.TimeUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/20.
 */

public class TaskSenderAdapter extends BaseRecyclerAdapter<TouGaoer> {

    public TaskSenderAdapter(Context context, ArrayList<TouGaoer> datas) {
        super(context, datas);
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_task_tougao_layout;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        final TouGaoer aTaskTouGao = mDatas.get(position);

        aItemViewHolder.mTitle.setText(aTaskTouGao.creatorName);
        aItemViewHolder.mType.setText(TimeUtil.getFormatTimeFromTimestamp(aTaskTouGao.createdTimestamp*1000,"yyyy-MM-dd"));


        if("1".equals(aTaskTouGao.isCheck)){
            aItemViewHolder.mDate.setText("已查看");
        }

        if(aTaskTouGao.creatorId.equals(SP.getInstance().getString(SPTag.TAG_MEMBER_ID))){
            aItemViewHolder.mStateLayout.setVisibility(View.VISIBLE);


            if("1".equals(aTaskTouGao.isUsed)){
                aItemViewHolder.mStatestate.setText("已采纳");
            }else{
                aItemViewHolder.mStatestate.setText("未采纳");
            }

            aItemViewHolder.mStateName.setText("名称："+aTaskTouGao.brandName);

            aItemViewHolder.mStatedesc.setText("释义："+aTaskTouGao.reason);


        }else{
            aItemViewHolder.mStateLayout.setVisibility(View.GONE);
        }


        if(!TextUtils.isEmpty(aTaskTouGao.creatorPortrait)){
            ImageLoader.progressiveLoad(aTaskTouGao.creatorPortrait,aItemViewHolder.mHeaderImg);
        }else{
            aItemViewHolder.mHeaderImg.setBackgroundResource(R.drawable.pic_morentouxiang);
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
        public SimpleDraweeView mHeaderImg;
        public TextView mTitle;
        public TextView mType;
        public TextView mDate;

        public TextView mStateName;
        public TextView mStatedesc;
        public TextView mStatestate;

        public RelativeLayout mStateLayout;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;
            mHeaderImg = (SimpleDraweeView) arg0.findViewById(R.id.ivLogo);
            mTitle = (TextView) arg0.findViewById(R.id.tvTitle);
            mType = (TextView) arg0.findViewById(R.id.tvLabel);
            mDate = (TextView) arg0.findViewById(R.id.tvEndTime);

            mStateName = (TextView) arg0.findViewById(R.id.tv_name);
            mStatedesc = (TextView) arg0.findViewById(R.id.tv_desc);
            mStatestate = (TextView) arg0.findViewById(R.id.tv_state);

            mStateLayout = (RelativeLayout) arg0.findViewById(R.id.bottom_layout);
        }

    }
}
