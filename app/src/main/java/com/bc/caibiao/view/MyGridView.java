package com.bc.caibiao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author wangkai
 * @Description: 重写GridView中onMeasure方法，用于实现GridView在ScrollView中正常显示
 * create at 2015/10/30 16:43
 */
public class MyGridView extends GridView {

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
