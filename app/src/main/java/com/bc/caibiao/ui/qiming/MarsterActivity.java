package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.MarsterAdapter;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MarsterActivity extends BaseActivity implements MarsterAdapter.OnItemClickListener {

    private RecyclerView mRecyclerMarster;
    private MarsterAdapter mMarsterAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marster);

//        materListPresent = new MaterListPresenter(this);
//        materListPresent.getMaster(1,10);


        try {
            //获取所有任务
            OkHttpClient build = new OkHttpClient.Builder().build();

            RequestBody body = new FormBody.Builder()
                    .add("pageOn", "1")
                    .add("pageSize", "10")
                    .build();
            Request build1 = new Request.Builder()
                    .post(body)
                    .url(URLConfig.BASEADDRESS + "task/listDaShiMembers")
                    .build();

            Response execute = build.newCall(build1).execute();
            String string = execute.body().string();


        } catch (IOException e) {
            e.printStackTrace();
        }


        initView();

    }

    @Override
    public void OnClick(View v) {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void initView() {
        mRecyclerMarster = (RecyclerView) findViewById(R.id.recycler_marster);
        mRecyclerMarster.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        mMarsterAdapter = new MarsterAdapter(this);
        mMarsterAdapter.setOnItemClickListener(this);
        mRecyclerMarster.setAdapter(mMarsterAdapter);

    }




    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(this, MarsterDetailActivity.class));
    }


}
