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

public class CategoryAdapter  extends BaseRecyclerAdapter<TaskCategory> {

    public CategoryAdapter(Context context, ArrayList<TaskCategory> datas) {
        super(context, datas);
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_smart;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        final TaskCategory aTaskCategory = mDatas.get(position);

        aItemViewHolder.mTitle.setText("第 "+aTaskCategory.getCategory_number()+" 类   "+aTaskCategory.getCategory_name());
        aItemViewHolder.mDesc.setVisibility(View.GONE);
        aItemViewHolder.mArrow.setVisibility(View.VISIBLE);

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
