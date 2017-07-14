package com.bc.caibiao.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bc.caibiao.R;
import com.bc.caibiao.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */



public class AskQuestionDialog extends Dialog {
    private ClearEditText etQuest;
    private OnClickItemPosition onClickItemPosition;

    public AskQuestionDialog(Context context) {
        super(context,R.style.dialog_bottom_full);
        View inflate = LayoutInflater.from(context).inflate(R.layout.popup_ask_for_brand_layout, null);
        setContentView(inflate);
        etQuest=(ClearEditText)findViewById(R.id.etQuest);
        Window dialogWindow =getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogWindow.setWindowAnimations(R.style.AnimBottom);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width= ScreenUtils.getScreenWidth(context);
        dialogWindow.setAttributes(lp);
    }


    public void setOnClickItemPosition(OnClickItemPosition onClickItemPosition) {
        this.onClickItemPosition = onClickItemPosition;
    }

    public interface OnClickItemPosition{
       void onClickItem(int position, AskQuestionDialog chooseDialog);
    }

    @Override
    public void show() {
        super.show();
        etQuest.postDelayed(new Runnable() {
            @Override
            public void run() {
                showInput(etQuest);
            }
        },100);

    }

    /**
     * 显示键盘
     */
    public void showInput(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }
}
