package com.bc.caibiao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.MarkModel.MarkRecommandProduct;
import com.bumptech.glide.Glide;

public class ShangbiaoSuggestionAdapter extends AppBaseAdapter<MarkRecommandProduct> {

    boolean showPrice = false;

    public ShangbiaoSuggestionAdapter(Context mContext,boolean isShowPrice) {
        super(mContext);
        showPrice = isShowPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(getContext(), convertView, parent, R.layout.item_shangbiao_suggestion, position);
        MarkRecommandProduct model=getItem(position);

        ((TextView) holder.getView(R.id.tvName)).setText(model.name);

        Glide.with(getContext()).load(model.thumb).placeholder(R.drawable.icon_guoji).into((ImageView) holder.getView(R.id.ivPic));

        if(showPrice){
            holder.getView(R.id.tvPrice).setVisibility(View.VISIBLE);
            ((TextView) holder.getView(R.id.tvPrice)).setText(model.price);
        }else{
            holder.getView(R.id.tvPrice).setVisibility(View.GONE);
        }

        if(position == getCount()-1 && mLoadmoreInterface != null ){
            mLoadmoreInterface.loadmore();
        }

        return holder.getConvertView();
    }

    private ShangbiaoResultAdapter.LoadmoreInterface mLoadmoreInterface;

    public void setLoadmoreInterface(ShangbiaoResultAdapter.LoadmoreInterface loadmoreInterface) {
        mLoadmoreInterface = loadmoreInterface;
    }
}
