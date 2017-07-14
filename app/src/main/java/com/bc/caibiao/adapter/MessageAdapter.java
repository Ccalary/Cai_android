package com.bc.caibiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.Message;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 收藏的商标Adapter
 */

public class MessageAdapter extends BaseRecyclerAdapter<Message> {
    private SimpleDateFormat df = new SimpleDateFormat("hh:mm");

    public MessageAdapter(Context context, ArrayList<Message> datas) {
        super(context, datas);
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_message;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        final Message message = mDatas.get(position);
        aItemViewHolder.mTvMessageContent.setText(message.getMessageContent());
        Date date = new Date(message.getCreatedTimestamp() * 1000l);
        aItemViewHolder.mTvCreatedTimestamp.setText(df.format(date));

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

        TextView mTvMessageContent, mTvCreatedTimestamp;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;

            mTvMessageContent = (TextView) itemView.findViewById(R.id.tv_message_content);
            mTvCreatedTimestamp = (TextView) itemView.findViewById(R.id.tv_createtime);
        }
    }
}
