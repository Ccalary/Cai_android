package com.bc.caibiao.widget.ConvenientBanner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 *
 * @author:
 * @:
 */

public class StarDetailPageAdapter <T> extends RecyclingPagerAdapter {

    protected List<T> mDatas;
    private String headerUrl;
    protected CBViewHolderCreator holderCreator;

    public StarDetailPageAdapter(CBViewHolderCreator holderCreator, List<T> datas, String header) {
        this.holderCreator = holderCreator;
        this.mDatas = datas;
        this.headerUrl = header;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        Holder holder = null;
        if (view == null) {
            holder = (Holder) holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(holder);
        } else {
            holder = (Holder<T>) view.getTag();
        }
        if (mDatas != null && !mDatas.isEmpty())
            holder.UpdateUI(container.getContext(), position, this.headerUrl +  mDatas.get(position));

        return view;
    }

    @Override
    public int getCount() {
        if (mDatas == null) return 0;
        return mDatas.size();
    }

    /**
     * @param <T>
     *            任何你指定的对象
     */
    public interface Holder<T> {
        View createView(Context context);

        void UpdateUI(Context context, int position, T data);
    }


}
