package com.bc.caibiao.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.SectionTypeAdapter;
import com.bc.caibiao.model.HomePageModel.TaskSectionType;
import com.bc.caibiao.utils.SimpleAnimation;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class TaskSectionPop {

    private Context mContext;
    private PopupWindow mPopupWindow;
    private View parent;
    private int tag;
    private ArrayList<TaskSectionType> list;
    private PopupWindow.OnDismissListener mDismissListener;
    private AdapterView.OnItemClickListener mItemClickListener;
    SectionTypeAdapter mAdapter;

    public TaskSectionPop(Context mContext, View parent, ArrayList<TaskSectionType> list, PopupWindow.OnDismissListener mDismissListener,
                                AdapterView.OnItemClickListener mItemClickListener) {
        this.mContext = mContext;
        this.parent = parent;
        this.list = list;
        this.mDismissListener = mDismissListener;
        this.mItemClickListener = mItemClickListener;
        createPopupWindow(parent);
    }

    public void showPopupWindow() {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(parent);
        }
    }

    public void dismissPopupWindow() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    @SuppressWarnings("deprecation")
    private void createPopupWindow(View parent) {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(createMyUI(), parent.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        mPopupWindow.setFocusable(true);// 使其聚集
        mPopupWindow.setOutsideTouchable(true);// 设置是否允许在外点击消失
        // mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);//设置弹出窗体需要软键盘
        // mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//再设置模式，和Activity的一样，覆盖，调整大小。
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());// 响应返回键必须的语句。
        mPopupWindow.setAnimationStyle(SimpleAnimation.getPopWindowAnimationStyle());
        mPopupWindow.update();
        mPopupWindow.setOnDismissListener(mDismissListener);
        mPopupWindow.showAsDropDown(parent);
        // mPopupWindow.showAtLocation(parent,Gravity.CENTER | Gravity.CENTER,
        // 0,0);
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setIndex(int Index) {
        if (mAdapter != null) {
            mAdapter.setIndex(Index);
            mAdapter.notifyDataSetChanged();
        }
    }

    private View createMyUI() {
        View view = View.inflate(mContext, R.layout.fl_forum_pop_listview, null);
        ListView mListView = (ListView) view.findViewById(R.id.fl_forum_pop_listview);

        if (mAdapter == null) {
            mAdapter = new SectionTypeAdapter(mContext, list);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();

        }

        mListView.setId(tag);
        mListView.setOnItemClickListener(mItemClickListener);
        return view;
    }
}
