package com.bc.caibiao.ui.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.ui.BaseFragment;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.MainActivity;
import com.bc.caibiao.utils.SP;

/**
 * Created by chengyanfang on 2017/4/24.
 */

public class GuideFragment3  extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.guide_layout3, container, false);
        return mView;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    @Override
    protected void initView() {
        super.initView();
        mView.findViewById(R.id.enterin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                SP.getInstance().saveString(SPTag.TAG_ISFIRST_COME, "1");
                getActivity().finish();
            }
        });
    }
}
