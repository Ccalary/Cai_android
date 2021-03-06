package com.bc.caibiao.adapter.QiMingModule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.MarkModel.TaskCategory;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class CategoryChildDetailAdapter extends BaseRecyclerAdapter<TaskCategory> {

    public CategoryChildDetailAdapter(Context context, ArrayList<TaskCategory> datas) {
        super(context, datas);
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_smart;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new CategoryAdapter.ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final CategoryAdapter.ItemViewHolder aItemViewHolder = (CategoryAdapter.ItemViewHolder) viewHolder;

        String numStr=mDatas.get(position).getCategory_trademark_code();
        if(isNull(numStr)){
            numStr=mDatas.get(position).getCategory_group_number();
            if(isNull(numStr)){
                numStr=mDatas.get(position).getCategory_number();
            }
        }
        aItemViewHolder.mTitle.setText(mDatas.get(position).getCategory_name()+numStr);
        aItemViewHolder.mDesc.setVisibility(View.GONE);
        aItemViewHolder.mArrow.setVisibility(View.GONE);

        if (mItemClickListener != null) {
            aItemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(null, null, position, 0);
                }
            });
        }
    }

    private boolean isNull(String str){
        if(str==null||str.equals("")||str.equals("null")){
            return true;
        }
        return false;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public View mView;
        public TextView mTitle;
        public TextView mDesc;
        public ImageView mArrow;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;
            mTitle = (TextView) arg0.findViewById(R.id.tv_na);
            mDesc = (TextView) arg0.findViewById(R.id.tv_re);
            mArrow = (ImageView) arg0.findViewById(R.id.iv_arrow);
        }

    }
}
