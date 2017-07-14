package com.bc.caibiao.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 带删除线的TextView
 */
public class DelLineTextView extends TextView {
    public DelLineTextView(Context context) {
        super(context);
    }

    public DelLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DelLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        getPaint().setAntiAlias(true);
        super.onDraw(canvas);
    }
}
