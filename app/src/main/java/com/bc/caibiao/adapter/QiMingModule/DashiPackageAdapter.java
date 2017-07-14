package com.bc.caibiao.adapter.QiMingModule;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.model.HomePageModel.SignPrice;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.Config;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/20.
 */

public class DashiPackageAdapter  extends BaseAdapter<SignPrice> {

    int width = 0;

    public DashiPackageAdapter(Activity context, ArrayList<SignPrice> objects) {
        super(context, objects);
        width = (AppUtil.getWidth(mContext)-AppUtil.dip2px(mContext,48))/3;
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_dashi_sign_package;
    }

    @Override
    protected void setViewData(View convertView, Holder holder, int position) {
        final ViewHolder aHolder = (ViewHolder) holder;

        SignPrice signPrice = mDatas.get(position);

        if(signPrice.isSelected){
            aHolder.selectedImg.setBackgroundResource(R.drawable.btn_weichaxun_s);
        }else{
            aHolder.selectedImg.setBackgroundResource(R.drawable.btn_weichaxun_n);
        }

        aHolder.title.setText(signPrice.getYuanPrice()+"元");

        if (signPrice.picList.size()==0){
            aHolder.img1.setVisibility(View.GONE);
            aHolder.img2.setVisibility(View.GONE);
            aHolder.img3.setVisibility(View.GONE);
        }else if(signPrice.picList.size()==1){
            aHolder.img1.setVisibility(View.VISIBLE);
            aHolder.img2.setVisibility(View.GONE);
            aHolder.img3.setVisibility(View.GONE);

            RelativeLayout.LayoutParams lps1 = new RelativeLayout.LayoutParams(width,width);
            aHolder.img1.setLayoutParams(lps1);
            lps1.setMargins(AppUtil.dip2px(mContext,8),0,0,0);
            Glide.with(mContext).load(URLConfig.baseUrl_pic_oss+signPrice.picList.get(0).picPath).placeholder(R.drawable.pic_01).into(aHolder.img1);
        }else if(signPrice.picList.size() == 2){
            aHolder.img1.setVisibility(View.VISIBLE);
            aHolder.img2.setVisibility(View.VISIBLE);
            aHolder.img3.setVisibility(View.GONE);

            RelativeLayout.LayoutParams lps1 = new RelativeLayout.LayoutParams(width,width);
            aHolder.img1.setLayoutParams(lps1);
            lps1.setMargins(AppUtil.dip2px(mContext,8),0,0,0);
            Glide.with(mContext).load(URLConfig.baseUrl_pic_oss+signPrice.picList.get(0).picPath).placeholder(R.drawable.pic_01).into(aHolder.img1);

            RelativeLayout.LayoutParams lps2 = new RelativeLayout.LayoutParams(width,width);
            aHolder.img2.setLayoutParams(lps2);
            lps2.setMargins(AppUtil.dip2px(mContext,16)+width,0,0,0);
            Glide.with(mContext).load(URLConfig.baseUrl_pic_oss+signPrice.picList.get(1).picPath).placeholder(R.drawable.pic_01).into(aHolder.img2);
        }else if(signPrice.picList.size() == 3){
            aHolder.img1.setVisibility(View.VISIBLE);
            aHolder.img2.setVisibility(View.VISIBLE);
            aHolder.img3.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams lps1 = new RelativeLayout.LayoutParams(width,width);
            aHolder.img1.setLayoutParams(lps1);
            lps1.setMargins(AppUtil.dip2px(mContext,8),0,0,0);
            Glide.with(mContext).load(URLConfig.baseUrl_pic_oss+signPrice.picList.get(0).picPath).placeholder(R.drawable.pic_01).into(aHolder.img1);

            RelativeLayout.LayoutParams lps2 = new RelativeLayout.LayoutParams(width,width);
            aHolder.img2.setLayoutParams(lps2);
            lps2.setMargins(AppUtil.dip2px(mContext,16)+width,0,0,0);
            Glide.with(mContext).load(URLConfig.baseUrl_pic_oss+signPrice.picList.get(1).picPath).placeholder(R.drawable.pic_01).into(aHolder.img2);

            RelativeLayout.LayoutParams lps3 = new RelativeLayout.LayoutParams(width,width);
            aHolder.img3.setLayoutParams(lps3);
            lps3.setMargins(AppUtil.dip2px(mContext,24)+width,0,0,0);
            Glide.with(mContext).load(URLConfig.baseUrl_pic_oss+signPrice.picList.get(2).picPath).placeholder(R.drawable.pic_01).into(aHolder.img3);
        }

    }

    @Override
    protected Holder getViewHolder(View convertView) {
        ViewHolder aHolder = new ViewHolder();
        aHolder.selectedImg = (ImageView) convertView.findViewById(R.id.selecte_img);
        aHolder.title = (TextView) convertView.findViewById(R.id.title);
        aHolder.img1 = (ImageView) convertView.findViewById(R.id.img1);
        aHolder.img2 = (ImageView) convertView.findViewById(R.id.img2);
        aHolder.img3 = (ImageView) convertView.findViewById(R.id.img3);
        return aHolder;
    }

    static class ViewHolder implements Holder {
        ImageView selectedImg;
        TextView title;
        ImageView img1;
        ImageView img2;
        ImageView img3;
    }
}
