package com.bc.caibiao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.qiming.MarsterDetailActivity;

/**
 * Created by Administrator on 2017/3/22.
 */

public class ImageAdpter extends BaseAdapter {
    private Context mContext;
    private Integer[]   mImageIds   ={
                    R.drawable.icon_shangchuan,
                    R.drawable.icon_shangchuan,
                    R.drawable.icon_shangchuan,
                    R.drawable.icon_shangchuan,
                    R.drawable.icon_shangchuan,
                    R.drawable.icon_shangchuan,
                    R.drawable.icon_shangchuan,
                    R.drawable.icon_shangchuan,

            };

    public ImageAdpter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mImageIds.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null)
        {
            // 给ImageView设置资源
            imageView = new ImageView(mContext);
          /*  // 设置布局 图片120×120显示
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));*/
            // 设置显示比例类型
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mImageIds[position]);
        return imageView;
    }
    }

