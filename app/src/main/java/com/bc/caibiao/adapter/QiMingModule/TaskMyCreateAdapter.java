package com.bc.caibiao.adapter.QiMingModule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomePageModel.TouGaoer;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.ImageLoader;
import com.bc.caibiao.utils.TimeUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/22.
 */

public class TaskMyCreateAdapter  extends BaseRecyclerAdapter<TouGaoer> {

    String isXuanshangjieshu = "0";

    public void setIsXuanshangjieshu(String isXuanshangjieshu) {
        this.isXuanshangjieshu = isXuanshangjieshu;
    }

    public TaskMyCreateAdapter(Context context, ArrayList<TouGaoer> datas, String xuanshangjieshu) {
        super(context, datas);
        isXuanshangjieshu = xuanshangjieshu;
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_mytask_tougao_layout;
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
        aItemViewHolder.mDate.setText(TimeUtil.getFormatTimeFromTimestamp(aTaskTouGao.createdTimestamp*1000,"yyyy-MM-dd"));


        if("1".equals(aTaskTouGao.isUsed)){
            aItemViewHolder.mType.setText("已采纳");
            aItemViewHolder.mType.setTextColor(mContext.getResources().getColor(R.color.orange));

            aItemViewHolder.mResistBrand.setVisibility(View.VISIBLE);
            aItemViewHolder.mResistBrand.setBackgroundResource(R.drawable.shape_stroke_zcsb);
            aItemViewHolder.task_state.setText("商标注册");
            aItemViewHolder.task_state.setTextColor(mContext.getResources().getColor(R.color.zcsb_color));

            aItemViewHolder.mResistBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mBuyTaskCallback != null){
                        mBuyTaskCallback.toBuyTask(position);
                    }
                }
            });
        }else{

            if("0".equals(isXuanshangjieshu)){
                aItemViewHolder.mResistBrand.setVisibility(View.VISIBLE);
                aItemViewHolder.mResistBrand.setBackgroundResource(R.drawable.shape_accept_bg);
                aItemViewHolder.task_state.setText("采 纳");
                aItemViewHolder.task_state.setTextColor(mContext.getResources().getColor(R.color.orange));
                aItemViewHolder.mResistBrand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mAcceptTaskCallback!=null){
                            mAcceptTaskCallback.toAcceptTask(position);
                        }
                    }
                });


            }else{
                aItemViewHolder.mResistBrand.setVisibility(View.GONE);
            }
            aItemViewHolder.mType.setText("已查看");
        }

        aItemViewHolder.mStateName.setText("名字："+aTaskTouGao.brandName);
        aItemViewHolder.mStatedesc.setText("释义："+aTaskTouGao.reason);

        if(aTaskTouGao.creatorPortrait != null){
            ImageLoader.progressiveLoad(aTaskTouGao.creatorPortrait,aItemViewHolder.mHeaderImg);
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
        public TextView mDate;
        public TextView mType;
        public LinearLayout mResistBrand;

        public TextView mStateName;
        public TextView mStatedesc;
        public TextView task_state;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;
            mHeaderImg = (SimpleDraweeView) arg0.findViewById(R.id.ivLogo);
            mTitle = (TextView) arg0.findViewById(R.id.tvTitle);
            mDate = (TextView) arg0.findViewById(R.id.tvLabel);
            mType = (TextView) arg0.findViewById(R.id.tvEndTime);
            mResistBrand = (LinearLayout) arg0.findViewById(R.id.recheck_zcsb);

            mStateName = (TextView) arg0.findViewById(R.id.tv_name_true);
            mStatedesc = (TextView) arg0.findViewById(R.id.tv_desc_true);
            task_state = (TextView) arg0.findViewById(R.id.task_state);
        }

    }

    public DoSignInfoAdapter.BuyTaskCallback mBuyTaskCallback;

    public AcceptTaskCallback mAcceptTaskCallback;
    public interface AcceptTaskCallback{
        public void toAcceptTask(int index);
    }
}
