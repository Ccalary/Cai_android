package com.bc.caibiao.adapter.QiMingModule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.model.HomePageModel.TaskDetailInnerModel;
import com.bc.caibiao.model.HomePageModel.TouGaoAnswer;
import com.bc.caibiao.model.HomePageModel.TouGaoer;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.TimeUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/22.
 */

public class DoSignInfoAdapter extends BaseRecyclerAdapter<TouGaoer> {

    int width = 0;
    boolean isFromDashi;

    public DoSignInfoAdapter(Context context, ArrayList<TouGaoer> datas,boolean fromDashi) {
        super(context, datas);
        width = (AppUtil.getWidth(context) - AppUtil.dip2px(context,10))/4;
        isFromDashi = fromDashi;
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_task_detail_tougaoer;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;

        TouGaoer aTouGaoer = mDatas.get(position);

        aItemViewHolder.mName.setText(aTouGaoer.brandName);
        aItemViewHolder.mDesc.setText(aTouGaoer.reason);

        if(isFromDashi && "1".equals(aTouGaoer.isCanAnswer)){
            aItemViewHolder.mCallBack.setVisibility(View.VISIBLE);
            aItemViewHolder.mCallBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mDoCallCallback != null){
                        mDoCallCallback.doCallback(position);
                    }
                }
            });
        }else if(!isFromDashi && "1".equals(aTouGaoer.isCanAsk)){
            aItemViewHolder.mCallBack.setVisibility(View.VISIBLE);
            aItemViewHolder.mCallBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mDoCallCallback != null){
                        mDoCallCallback.doCallback(position);
                    }
                }
            });
        }else{
            aItemViewHolder.mCallBack.setVisibility(View.GONE);
        }

        if (mItemClickListener != null) {
            aItemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(null, null, position, 0);
                }
            });
        }

        addGridViewCell(aItemViewHolder.mImgGridView,aTouGaoer.picPaths);

        addCommentLine(aItemViewHolder.mAnswerView,aTouGaoer.taskAskAnswerList);


        aItemViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRemoveCallback != null){
                    mRemoveCallback.doRemoveAt(position);
                }
            }
        });

        aItemViewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEdtiCallback != null){
                    mEdtiCallback.doEditAt(position);
                }
            }
        });

        aItemViewHolder.mSignBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBuyTaskCallback != null){
                    mBuyTaskCallback.toBuyTask(position);
                }
            }
        });

        if(isFromDashi){
            aItemViewHolder.mDelete.setVisibility(View.VISIBLE);
            aItemViewHolder.mEdit.setVisibility(View.VISIBLE);
            aItemViewHolder.mSignBorrow.setVisibility(View.GONE);
            aItemViewHolder.mCallBack.setText("回复");
        }else{
            aItemViewHolder.mDelete.setVisibility(View.GONE);
            aItemViewHolder.mEdit.setVisibility(View.GONE);
            aItemViewHolder.mSignBorrow.setVisibility(View.VISIBLE);
            aItemViewHolder.mCallBack.setText("提问");
        }


    }


    //添加聊天记录
    private void addCommentLine(LinearLayout commentLinerview,ArrayList<TouGaoAnswer> taskAskAnswerList){
        commentLinerview.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AppUtil.dip2px(mContext,40)); // 每行的水平LinearLayout
        layoutParams.setMargins(0, 0, 0, 0);

        for(int i = 0;i<taskAskAnswerList.size();i++){
            LinearLayout cellLinerlayout = new LinearLayout(mContext);
            cellLinerlayout.setOrientation(LinearLayout.HORIZONTAL);
            cellLinerlayout.setLayoutParams(layoutParams);
            commentLinerview.addView(cellLinerlayout);

            TextView titleTv = new TextView(mContext);
            titleTv.setTextColor(mContext.getResources().getColor(R.color.base_gray_al));
            titleTv.setTextSize(14);
            titleTv.setWidth(AppUtil.dip2px(mContext,AppUtil.dip2px(mContext,30)));
            titleTv.setGravity(Gravity.CENTER_VERTICAL);
            titleTv.setLines(1);
            if("1".equals(taskAskAnswerList.get(i).modelType)){
                titleTv.setText(taskAskAnswerList.get(i).creatorName+"提问：");
            }else{
                titleTv.setText(taskAskAnswerList.get(i).creatorName+"回复：");
            }


            cellLinerlayout.addView(titleTv);

            TextView contentTv = new TextView(mContext);
            contentTv.setTextColor(mContext.getResources().getColor(R.color.base_gray));
            contentTv.setTextSize(14);
            contentTv.setGravity(Gravity.CENTER_VERTICAL);
            contentTv.setLines(1);
            contentTv.setText(taskAskAnswerList.get(i).content);
            cellLinerlayout.addView(contentTv);
        }
    }


    //添加图片
    private void addGridViewCell(LinearLayout imgGridView,ArrayList<String> picPaths){

        imgGridView.removeAllViews();

        int lineNum = picPaths.size()/4;

        int otherCell = picPaths.size() % 4;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        layoutParams.setMargins(0, 0, 0, 0);

        for(int i = 0; i< lineNum;i++){
            LinearLayout cellLinerlayout = new LinearLayout(mContext);
            cellLinerlayout.setOrientation(LinearLayout.HORIZONTAL);
            cellLinerlayout.setLayoutParams(layoutParams);
            imgGridView.addView(cellLinerlayout);

            for(int j = 0;j<4;j++){
                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(width,width));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(mContext).load(URLConfig.baseUrl_pic_oss + picPaths.get(i*4+j)).placeholder(R.drawable.pic_01).into(imageView);
                cellLinerlayout.addView(imageView);
            }
        }

        if(otherCell>0){
            LinearLayout cellLinerlayout = new LinearLayout(mContext);
            cellLinerlayout.setOrientation(LinearLayout.HORIZONTAL);
            cellLinerlayout.setLayoutParams(layoutParams);
            imgGridView.addView(cellLinerlayout);

            for(int j = 0;j<otherCell;j++){
                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(width,width));
                Glide.with(mContext).load(URLConfig.baseUrl_pic_oss + picPaths.get(lineNum*4+j)).placeholder(R.drawable.pic_01).into(imageView);
                cellLinerlayout.addView(imageView);
            }
        }

    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public View mView;
        public TextView mName;
        public TextView mDesc;
        public ImageView mDelete;
        public ImageView mEdit;
        public TextView mCallBack;
        public LinearLayout mImgGridView;
        public LinearLayout mAnswerView;
        public LinearLayout mSignBorrow;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;
            mName = (TextView) arg0.findViewById(R.id.name);
            mDesc = (TextView) arg0.findViewById(R.id.desc);
            mDelete = (ImageView) arg0.findViewById(R.id.close_img);
            mEdit = (ImageView) arg0.findViewById(R.id.edit_img);

            mCallBack = (TextView) arg0.findViewById(R.id.tv_callback);
            mImgGridView = (LinearLayout) arg0.findViewById(R.id.grid_img_layout);
            mAnswerView = (LinearLayout) arg0.findViewById(R.id.liner_answer_layout);
            mSignBorrow = (LinearLayout) arg0.findViewById(R.id.recheck_zcsb);
        }

    }

    public interface EdtiCallback{
        public void doEditAt(int index);
    }

    public interface DoCallCallback{
        public void doCallback(int index);
    }

    public interface BuyTaskCallback{
        public void toBuyTask(int index);
    }

    public interface RemoveCallback{
        public void doRemoveAt(int index);
    }

    public EdtiCallback mEdtiCallback;
    public RemoveCallback mRemoveCallback;
    public DoCallCallback mDoCallCallback;
    public BuyTaskCallback mBuyTaskCallback;
}
