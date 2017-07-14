package com.bc.caibiao.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.utils.ScreenUtils;

/**
 * @author wangkai
 * @create 16/9/18 下午1:22
 * @Description :
 */
public class BottomTabsLayout extends LinearLayout {
    private String[] texts;
    private Integer[] unSelectPicIds;
    private Integer[] selectPicIds;
    private String selectColor, unSelectColor;
    private View mView;
    private LinearLayout llBottom;
    private LayoutInflater inflater;
    private int lastSelect;
    private onTabsSelectChangeListener mListener;
    private Context mContext;
    private int position;


    public BottomTabsLayout(Context context) {
        super(context);
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.layout_bottom_tabs, null);
        addView(mView);
    }

    public BottomTabsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.layout_bottom_tabs, null);
        addView(mView);
    }

    /**
     * @param texts          底部文字集合
     * @param unSelectColor  未选中的图片颜色
     * @param unSelectPicIds 没选中状态图片ID
     * @param selectColor    选中的文字颜色
     * @param selectPicIds   选择状态图片ID
     */
    public void setConfigs(String[] texts, String unSelectColor, Integer[] unSelectPicIds, String selectColor, Integer[] selectPicIds) {
        this.texts = texts;
        this.unSelectPicIds = unSelectPicIds;
        this.selectPicIds = selectPicIds;
        this.selectColor = selectColor;
        this.unSelectColor = unSelectColor;
        initView();
    }

    private void initView() {
        if (texts.length != unSelectColor.length() && texts.length != unSelectPicIds.length) {
            return;
        }
        llBottom = (LinearLayout) mView.findViewById(R.id.llBottom);
        int size = texts.length;

        for (int i = 0; i < size; i++) {
            View view = inflater.inflate(R.layout.item_bottom_tabs, null);
            /*设置底部文字*/
            TextView textView = (TextView) view.findViewById(R.id.tvTabs);

            /*通过设置TextView的宽度来实现等分*/
            ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
            layoutParams.width = ScreenUtils.getScreenWidth(mContext) / texts.length;
            textView.setLayoutParams(layoutParams);
            /*设置文字和默认颜色*/
            if (i < texts.length) {
                textView.setText(texts[i]);
                textView.setTextColor(Color.parseColor(unSelectColor));
            }
            /*设置图片*/
            ImageView imageView = (ImageView) view.findViewById(R.id.ivTabs);
            if (i < unSelectPicIds.length) {
                imageView.setImageResource(unSelectPicIds[i]);
            }
            /*设置点击事件*/
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelection(finalI);
                }
            });
            llBottom.addView(view);
        }
    }

    public void setSelection(int position) {
        if (position >= texts.length) {
            return;
        }
        this.position = position;
        if (mListener != null) {
            mListener.selectTabs(position);
        }
        if (position > 4) {
            return;
        }
        View childLast = llBottom.getChildAt(lastSelect);
        ((TextView) childLast.findViewById(R.id.tvTabs)).setTextColor(Color.parseColor(unSelectColor));
        ((ImageView) childLast.findViewById(R.id.ivTabs)).setImageResource(unSelectPicIds[lastSelect]);
        View childAt = llBottom.getChildAt(position);
        ((TextView) childAt.findViewById(R.id.tvTabs)).setTextColor(Color.parseColor(selectColor));
        ((ImageView) childAt.findViewById(R.id.ivTabs)).setImageResource(selectPicIds[position]);
        lastSelect = position;
        invalidate();
    }


    public interface onTabsSelectChangeListener {
        void selectTabs(int position);
    }

    public void setOnTabsSelectChangeListener(onTabsSelectChangeListener mListener) {
        this.mListener = mListener;
    }
}
