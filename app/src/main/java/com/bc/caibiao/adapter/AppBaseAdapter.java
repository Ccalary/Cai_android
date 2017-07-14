package com.bc.caibiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * AppBaseAdapter
 * ver 1.2.2
 *
 * @param <T> 数据类型
 */
public abstract class AppBaseAdapter<T> extends BaseAdapter {

    public List<T> list = new ArrayList<T>();
    public LayoutInflater inflater;
    private Context mContext;

    public AppBaseAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(T object) {
        list.add(object);
    }

    public void addItemAndRefresh(T object) {
        addItem(object);
        refresh();
    }

    public void addList(List<T> arrayList) {
        list.addAll(arrayList);
    }

    /**
     * 向list中添加数据
     *
     * @param arrayList
     */
    public final void addListAndRefresh(List<T> arrayList) {
        if (arrayList != null) {
            addList(arrayList);
            refresh();
        }
    }

    /**
     * 清空list集合
     */
    public void clearList() {
        if (list != null && list.size() > 0) {
            list.clear();
        }
    }

    public void clearListAndRefresh() {
        clearList();
        refresh();
    }

    /**
     * 移除指定位置的对象
     *
     * @param index
     */
    public void removeItem(int index) {
        list.remove(list.get(index));
    }

    /**
     * 移除指定位置的对象
     *
     * @param index
     */
    public void removeItemAndRefresh(int index) {
        removeItem(index);
        refresh();
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public void setItemAtPosition(int position, T object) {
        list.set(position, object);
    }

    public void setItemAtPositionAndRefresh(int position, T object) {
        setItemAtPosition(position, object);
        refresh();
    }

    public void setList(List<T> tmpList) {
        clearList();
        list.addAll(tmpList);
    }

    public final void setListAndRefresh(List<T> tmpList) {
        if (tmpList != null) {
            setList(tmpList);
            refresh();
        } else {
            //clear data
            clearList();
            refresh();
        }
    }

    public List<T> getList() {
        return this.list;
    }
}