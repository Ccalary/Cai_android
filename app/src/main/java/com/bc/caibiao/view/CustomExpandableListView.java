package com.bc.caibiao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

import com.bc.caibiao.utils.ScreenUtils;

public class CustomExpandableListView extends ExpandableListView {

    private Context context;

    public CustomExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public CustomExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomExpandableListView(Context context) {
        super(context);
        this.context = context;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(ScreenUtils.getScreenWidth_v2(context), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(ScreenUtils.getScreenHeight_v2(context), MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
