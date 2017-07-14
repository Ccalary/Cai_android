package com.bc.caibiao.adapter.QiMingModule;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomePageModel.RewardTaskModel;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.GlideTransFormer;
import com.bc.caibiao.utils.ImageLoader;
import com.bc.caibiao.utils.StringUtil;
import com.bc.caibiao.utils.TimeUtility;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;


/**
 * Created by chengyanfang on 2017/4/17.
 */

public class HomeRewardAdapter extends BaseRecyclerAdapter<RewardTaskModel> {

    public HomeRewardAdapter(Context context, ArrayList<RewardTaskModel> datas) {
        super(context, datas);
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_qiming_fragment_xuanshang;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        final RewardTaskModel aRewardTaskModel = mDatas.get(position);

        aItemViewHolder.mTitle.setText(aRewardTaskModel.taskTitle);


        Drawable leftDoltDrawable;
        if("1".equals(aRewardTaskModel.namedCategory)){
            aItemViewHolder.mType.setText("商标起名");
            leftDoltDrawable = mContext.getResources().getDrawable(R.drawable.shangbiao_drawable_dolt);
            leftDoltDrawable.setBounds(0, 0, leftDoltDrawable.getMinimumWidth(), leftDoltDrawable.getMinimumHeight());
            aItemViewHolder.mType.setCompoundDrawables(leftDoltDrawable,null,null,null);
        }else{
            aItemViewHolder.mType.setText("公司起名");
            leftDoltDrawable = mContext.getResources().getDrawable(R.drawable.company_drawable_dolt);
            leftDoltDrawable.setBounds(0, 0, leftDoltDrawable.getMinimumWidth(), leftDoltDrawable.getMinimumHeight());
            aItemViewHolder.mType.setCompoundDrawables(leftDoltDrawable,null,null,null);
        }

        if("1".equals(aRewardTaskModel.isXuanShangFenQian)){
            aItemViewHolder.mDate.setText("已截稿");
        }else{
            aItemViewHolder.mDate.setText(TimeUtility.getListTime(aRewardTaskModel.expireTime)+"截稿");
        }


        aItemViewHolder.mPrice.setText("¥"+ StringUtil.getFormatedFloatString(aRewardTaskModel.getPrice()));

        if(TextUtils.isEmpty(aRewardTaskModel.creatorPortrait)){
            Glide.with(mContext).load("").transform(new GlideTransFormer(mContext,AppUtil.dip2px(mContext,22))).placeholder(R.drawable.pic_morentouxiang).into(aItemViewHolder.mHeaderImg);
        }else{
            Glide.with(mContext).load(aRewardTaskModel.creatorPortrait).transform(new GlideTransFormer(mContext, AppUtil.dip2px(mContext,22))).placeholder(R.drawable.pic_morentouxiang).into(aItemViewHolder.mHeaderImg);
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

        public ImageView mHeaderImg;
        public TextView mTitle;
        public TextView mType;
        public TextView mDate;
        public TextView mPrice;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;

            mHeaderImg = (ImageView) arg0.findViewById(R.id.ivLogo);
            mTitle = (TextView) arg0.findViewById(R.id.tvTitle);
            mType = (TextView) arg0.findViewById(R.id.tvLabel);
            mDate = (TextView) arg0.findViewById(R.id.tvEndTime);
            mPrice = (TextView) arg0.findViewById(R.id.tvMoney);
        }

    }
}
