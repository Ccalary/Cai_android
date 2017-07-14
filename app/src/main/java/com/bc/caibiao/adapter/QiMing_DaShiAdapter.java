package com.bc.caibiao.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.holder.QiMing_DaShiViewHolder;
import com.bc.caibiao.model.HomeDaShiTestModel;
import com.bc.caibiao.utils.ImageLoad;

import java.util.ArrayList;
import java.util.List;

public class QiMing_DaShiAdapter extends RecyclerView.Adapter<QiMing_DaShiViewHolder> {

    private List<HomeDaShiTestModel> items;

    public void setListAndRefresh(List<HomeDaShiTestModel> listAndRefresh) {
        this.items.clear();
        this.items.addAll(listAndRefresh);
        notifyDataSetChanged();
    }

    public void setList(List<HomeDaShiTestModel> listAndRefresh) {
        this.items.clear();
        this.items.addAll(listAndRefresh);
    }

    public QiMing_DaShiAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public QiMing_DaShiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_qiming_fragment_dashi, parent, false);
        return new QiMing_DaShiViewHolder(view);
    }

    public HomeDaShiTestModel getItem(int index) {
        return items.get(index);
    }

    @Override
    public void onBindViewHolder(final QiMing_DaShiViewHolder holder, final int position) {
        HomeDaShiTestModel s = getItem(position);
        ImageLoad.loadURL(holder.getPicView(), s.imgLogo);
        holder.getName().setText(s.dashiName);
        holder.getCount().setText(s.dashiSucCount);
        holder.getIntroduce().setText(s.dashiIntroduce);
        holder.itemView.setTag(s);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
