package com.bc.caibiao.adapter.QiMingModule;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.utils.AppUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/22.
 */

public class SignPhotoAdapter  extends BaseAdapter<String> {

    int width = 0;

    public SignPhotoAdapter(Activity context, ArrayList<String> objects) {
        super(context, objects);
        width = (AppUtil.getWidth(mContext)-AppUtil.dip2px(mContext,10))/4;
    }

    @Override
    protected int getResourceId() {
        return R.layout.dashi_example_item;
    }

    @Override
    protected void setViewData(View convertView, Holder holder, final int position) {
        final ViewHolder aHolder = (ViewHolder) holder;
        if("拍照".equals(mDatas.get(position))){
            Glide.with(mContext).load(R.drawable.icon_shangchuan).into(aHolder.mUserCenterImg);
            aHolder.mDeleteImg.setVisibility(View.GONE);
        }else{
            aHolder.mDeleteImg.setVisibility(View.VISIBLE);


            if(mDatas.get(position).startsWith("/storage")){
                Glide.with(mContext).load(mDatas.get(position)).into(aHolder.mUserCenterImg);
            }else{
                Glide.with(mContext).load(URLConfig.baseUrl_pic_oss+mDatas.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).into(aHolder.mUserCenterImg);
            }
            aHolder.mDeleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mDeletePhotoCallBack != null){
                        mDeletePhotoCallBack.deleteAt(position);
                    }
                }
            });
        }

        aHolder.mUserCenterImg.setLayoutParams(new RelativeLayout.LayoutParams(width,width));
    }

    @Override
    protected Holder getViewHolder(View convertView) {
        ViewHolder aHolder = new ViewHolder();
        aHolder.mUserCenterImg = (ImageView) convertView.findViewById(R.id.img);
        aHolder.mDeleteImg = (ImageView) convertView.findViewById(R.id.close_img);
        return aHolder;
    }

    static class ViewHolder implements Holder {
        ImageView mUserCenterImg;
        ImageView mDeleteImg;
    }

    private DeletePhotoCallBack mDeletePhotoCallBack;

    public void setmDeletePhotoCallBack(DeletePhotoCallBack deletePhotoCallBack) {
        this.mDeletePhotoCallBack = deletePhotoCallBack;
    }

    public interface  DeletePhotoCallBack{
        void deleteAt(int index);
    }
}