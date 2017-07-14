package com.bc.caibiao.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bc.caibiao.R;


/**
 * Created by Administrator on 2017/3/23.
 */

public class MyDialog extends Dialog implements View.OnClickListener {

    Context context;
    public MyDialog(Context context) {
        super(context);
        this.context = context;
    }
    /**
     * 自定义布局的构造方法
     * @param context
     * @param resLayout
     */
    public MyDialog(Context context,int resLayout){
        super(context);
        this.context = context;

    }
    /**
     * 自定义主题及布局的构造方法
     * @param context
     * @param theme
     * @param resLayout
     */
    public MyDialog(Context context, int theme,int resLayout){
        super(context, theme);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog);

        View viewById = findViewById(R.id.dialog_title_image);
        viewById.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }
}
