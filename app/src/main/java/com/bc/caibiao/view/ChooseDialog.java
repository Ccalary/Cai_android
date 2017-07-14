package com.bc.caibiao.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bc.caibiao.R;
import com.bc.caibiao.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */


public class ChooseDialog extends Dialog {
    private ListView lvData;
    private List<String> datas;
    private OnClickItemPosition onClickItemPosition;

    public ChooseDialog(Context context) {
        super(context, R.style.dialog_bottom_full);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_choose, null);
        setContentView(inflate);
        lvData = (ListView) findViewById(R.id.lvData);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogWindow.setWindowAnimations(R.style.AnimBottom);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ScreenUtils.getScreenWidth(context);
        dialogWindow.setAttributes(lp);
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onClickItemPosition != null) {
                    onClickItemPosition.onClickItem(position, ChooseDialog.this);
                }
                dismiss();
            }
        });
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
        lvData.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_dialog_choose, datas));
        if (datas.size() * 40 > 250) {
            Window dialogWindow = getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.height = ScreenUtils.dip2px(getContext(), 250);
            dialogWindow.setAttributes(lp);
        }
    }

    public void setOnClickItemPosition(OnClickItemPosition onClickItemPosition) {
        this.onClickItemPosition = onClickItemPosition;
    }

    public interface OnClickItemPosition {
        void onClickItem(int position, ChooseDialog chooseDialog);
    }
}
