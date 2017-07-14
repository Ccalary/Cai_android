package com.bc.caibiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.MarkModel.MarkSearchResult;
import com.bc.caibiao.model.ShangbiaoResultModel;
import com.bc.caibiao.ui.shangbiao.ShangbiaoDetailActivity;
import com.bc.caibiao.utils.ImageLoader;
import com.facebook.drawee.view.SimpleDraweeView;

public class ShangbiaoResultAdapter extends AppBaseAdapter<MarkSearchResult> {

    public ShangbiaoResultAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(getContext(), convertView, parent, R.layout.item_shangbiao_result, position);
        MarkSearchResult model=getItem(position);

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickAtItemCallback != null){
                    mOnClickAtItemCallback.onClickAt(position);
                }
            }
        });

        //商标名称
        ((TextView) holder.getView(R.id.tvName)).setText(model.tmname);

        ((TextView) holder.getView(R.id.tvDesc)).setText(model.tmlaw);

        //商标图片
        ImageLoader.progressiveLoad(model.image,(SimpleDraweeView) holder.getView(R.id.ivPic));


        //是否追随
        if(model.follow.equals("false")){
            ((ImageView) holder.getView(R.id.is_follow)).setBackgroundResource(R.drawable.icon_xin_n);
        }else{
            ((ImageView) holder.getView(R.id.is_follow)).setBackgroundResource(R.drawable.icon_xin);
        }

        //进行追随
        holder.getView(R.id.rlv_follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFollowInterface!=null)
                {
                    mFollowInterface.doChangeFollow(position);
                }
            }
        });



        if(position == getCount()-1 && mLoadmoreInterface != null ){
            mLoadmoreInterface.loadmore();
        }


        return holder.getConvertView();
    }



    private LoadmoreInterface mLoadmoreInterface;

    public void setLoadmoreInterface(LoadmoreInterface loadmoreInterface) {
        mLoadmoreInterface = loadmoreInterface;
    }

    public interface LoadmoreInterface{
        void loadmore();
    }




    public OnClickAtItemCallback mOnClickAtItemCallback;

    public void setmOnClickAtItemCallback(OnClickAtItemCallback mOnClickAtItemCallback) {
        this.mOnClickAtItemCallback = mOnClickAtItemCallback;
    }

    public interface OnClickAtItemCallback{
        void onClickAt(int position);
    }

    public FollowInterface mFollowInterface;

    public interface FollowInterface{
        void doChangeFollow(int index);
    }

}
