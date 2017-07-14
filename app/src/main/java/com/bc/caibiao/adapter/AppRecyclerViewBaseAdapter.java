package com.bc.caibiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * AppRecyclerViewBaseAdapter
 * ver 1.0
 *
 * @param <T> Data type
 * @param <H> a ViewHolder which extend RecyclerView.ViewHolder
 */
public abstract class AppRecyclerViewBaseAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {
    private List<T> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public AppRecyclerViewBaseAdapter(Context mContext) {
        this.mData = new ArrayList<>();
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return this.mData.size();
    }

    public void setList(List<T> data) {
        clearList();
        this.mData.addAll(data);
    }

    public void setListAndRefresh(List<T> data) {
        setList(data);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.mData.clear();
    }

    public void clearListAndRefresh() {
        clearList();
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        this.mData.add(item);
    }

    public void addItemAndRefresh(T item) {
        addItem(item);
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        this.mData.remove(this.mData.get(index));
    }

    public void removeItemAndRefresh(int index) {
        removeItem(index);
        notifyDataSetChanged();
    }

    public void setItemAtPosition(int position, T object) {
        this.mData.set(position, object);
    }

    public void setItemAtPositionAndRefresh(int position, T object) {
        setItemAtPosition(position, object);
        notifyDataSetChanged();
    }

    public T getItemAtPosition(int position) {
        return this.mData.get(position);
    }

    public T getItem(int position) {
        return getItemAtPosition(position);
    }

    public Context getContext() {
        return this.mContext;
    }

    public LayoutInflater getInflater() {
        return this.inflater;
    }

    public View getInflatedView(int layoutId, ViewGroup parent) {
        return this.inflater.inflate(layoutId, parent, false);
    }
}
