package com.bc.caibiao.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class QiMing_DaShiViewHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView picView;
    private TextView name;
    private TextView introduce;
    private TextView count;

    public QiMing_DaShiViewHolder(View itemView) {
        super(itemView);
        picView = (SimpleDraweeView) itemView.findViewById(R.id.ivLogo);
        name=(TextView) itemView.findViewById(R.id.tvDashiName);
        introduce=(TextView) itemView.findViewById(R.id.tvDashiIntroduce);
        count=(TextView) itemView.findViewById(R.id.tvCount);
    }

    public SimpleDraweeView getPicView() {
        return picView;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getIntroduce() {
        return introduce;
    }

    public void setIntroduce(TextView introduce) {
        this.introduce = introduce;
    }

    public TextView getCount() {
        return count;
    }

    public void setCount(TextView count) {
        this.count = count;
    }
}
