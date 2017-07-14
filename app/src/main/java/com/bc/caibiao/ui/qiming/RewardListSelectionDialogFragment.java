package com.bc.caibiao.ui.qiming;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.AppBaseAdapter;
import com.bc.caibiao.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RewardListSelectionDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView listView;
    private Adapter adapter;

    private List<InnerBean> data = new ArrayList<>();

    private static final String[] money = new String[]{
            "金额", "金额从低到高", "金额从高到低"
    };

    private static final String[] category = new String[]{
            "分类", "商标起名", "公司起名"
    };

    private static final String[] time = new String[]{
            "时间", "按发布时间最早", "按发布时间最晚"
    };

    private static final String[] people = new String[]{
            "人气", "投稿人最少", "投稿人最多"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        adapter = new Adapter(getActivity());
        int type = bundle.getInt("type", 0);
        int selection = bundle.getInt("selection", 0);
        setDataResource(type, selection);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_single_listview, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.refresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bc_white)));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = win.getAttributes();
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);

    }

    private void setDataResource(int type, int selection) {
        String[] tmp;
        switch (type) {
            case 0:
                tmp = money;
                break;
            case 1:
                tmp = category;
                break;
            case 2:
                tmp = time;
                break;
            case 3:
                tmp = people;
                break;
            default:
                tmp = money;
                break;
        }
        for (int i = 0; i < tmp.length; i++) {
            InnerBean bean = new InnerBean();
            bean.s = tmp[i];
            bean.selected = (i == selection);
            data.add(bean);
        }
        adapter.setList(data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    private class Adapter extends AppBaseAdapter<InnerBean> {

        public Adapter(Context mContext) {
            super(mContext);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            InnerBean bean = getItem(position);
            ViewHolder holder = ViewHolder.get(getContext(), convertView, parent, android.R.layout.simple_list_item_1, position);
            ((TextView) holder.getView(android.R.id.text1)).setText(bean.s);
            return holder.getConvertView();
        }
    }

    private class InnerBean {
        String s;
        boolean selected = false;
    }

}
