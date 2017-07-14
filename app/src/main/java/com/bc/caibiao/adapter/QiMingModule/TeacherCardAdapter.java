package com.bc.caibiao.adapter.QiMingModule;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomePageModel.TeacherModel;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.view.TagView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class TeacherCardAdapter  extends BaseRecyclerAdapter<TeacherModel> {

    int width = 0;

    public TeacherCardAdapter(Context context, ArrayList<TeacherModel> datas) {
        super(context, datas);
        width = (AppUtil.getWidth(context) - AppUtil.dip2px(context,30))/2;
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_teacher_layout;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;

        TeacherModel aTeacherModel = mDatas.get(position);

        aItemViewHolder.photoImg.setLayoutParams(new RelativeLayout.LayoutParams(width,width));
        Glide.with(mContext).load(aTeacherModel.portrait).into(aItemViewHolder.photoImg);

        aItemViewHolder.name.setText(aTeacherModel.memberName);
        aItemViewHolder.desc.setText(aTeacherModel.memberExtend.provinceName);


        ArrayList<String> mTopTags = aTeacherModel.memberExtend.getTedianList();
        int size = mTopTags.size();

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        itemParams.setMargins(AppUtil.dip2px(mContext,4),AppUtil.dip2px(mContext,0),AppUtil.dip2px(mContext,4),AppUtil.dip2px(mContext,0));

        ArrayList<TagView> childBtns = new ArrayList<TagView>();

        int currentWidth = 0;
        for(int i = 0; i < size; i++){
            //1.初始化tagview
            TagView tagView = new TagView((Activity) mContext,mTopTags.get(i),false,i,true);
            tagView.getView().setLayoutParams(itemParams);
            tagView.getView().setTag(i);
            childBtns.add(tagView);

            //2.判断当前的长度
            String item = mTopTags.get(i);
            int length= item.length();
            currentWidth = currentWidth + (int)AppUtil.sp2px(mContext,14)*length + AppUtil.dip2px(mContext,20);
            currentWidth = currentWidth + AppUtil.dip2px(mContext,18);

            aItemViewHolder.tagView.addView(tagView.getView());

            if(currentWidth > width){
                break;
            }
        }

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
        public ImageView photoImg;
        public TextView name;
        public TextView desc;
        public LinearLayout tagView;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;
            photoImg = (ImageView) mView.findViewById(R.id.img1);
            name = (TextView) mView.findViewById(R.id.name1);
            desc = (TextView) mView.findViewById(R.id.desc1);
            tagView = (LinearLayout) mView.findViewById(R.id.llv_taglayout_recommand);
        }

    }
}
