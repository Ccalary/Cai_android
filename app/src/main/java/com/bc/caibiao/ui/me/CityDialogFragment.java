package com.bc.caibiao.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.AppBaseAdapter;
import com.bc.caibiao.adapter.ViewHolder;
import com.bc.caibiao.model.CityBean;
import com.bc.caibiao.model.ProvinceBean;

import java.io.Serializable;
import java.util.List;

public class CityDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView listView;
    private CityAdapter adapter;

    AdapterView.OnItemClickListener l;

    List<CityBean> list;

    public static CityDialogFragment getInstance(List<CityBean> list){
        CityDialogFragment fragment=new CityDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("list", (Serializable) list);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setL(AdapterView.OnItemClickListener l) {
        this.l = l;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list= (List<CityBean>) getArguments().getSerializable("list");
        adapter=new CityAdapter(getActivity());
        adapter.setList(list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_single_listview,container,false);
        listView=(ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(l!=null){
            l.onItemClick(parent,view,position,id);
        }
        dismiss();
    }

    private class CityAdapter extends AppBaseAdapter<CityBean>{

        public CityAdapter(Context mContext) {
            super(mContext);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CityBean bean=getItem(position);
            ViewHolder holder=ViewHolder.get(getContext(),convertView,parent,android.R.layout.simple_list_item_1,position);
            ((TextView) holder.getView(android.R.id.text1)).setText(bean.getCityName());
            return holder.getConvertView();
        }
    }
}
