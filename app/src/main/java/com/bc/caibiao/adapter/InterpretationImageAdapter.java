package com.bc.caibiao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bc.caibiao.R;
import com.bc.caibiao.utils.ImageLoad;
import com.facebook.drawee.view.SimpleDraweeView;


public class InterpretationImageAdapter extends AppBaseAdapter<String> {
    public InterpretationImageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(getContext(), convertView, parent, R.layout.item_interpretation_image, position);
        String str=getItem(position);
        SimpleDraweeView pic=  (SimpleDraweeView)(holder.getView(R.id.ivInterpretation));
        if(position==0)
            ImageLoad.loadRes(pic,R.drawable.chuanzhaopian);
        else
        ImageLoad.loadLocalFile(pic,str);
        return holder.getConvertView();
    }
}
