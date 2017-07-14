package com.bc.caibiao.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.utils.AppUtil;

public class ChoiceView extends FrameLayout implements Checkable {
    private TextView mTextView;
    private boolean mChecked;

    public ChoiceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextView=(TextView) findViewById(R.id.title);
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        mTextView.setSelected(mChecked);
//        imageView.setVisibility(checked ? View.VISIBLE : View.GONE);
    }
 
    @Override
    public boolean isChecked() {
        return mChecked;
    }
 
    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}