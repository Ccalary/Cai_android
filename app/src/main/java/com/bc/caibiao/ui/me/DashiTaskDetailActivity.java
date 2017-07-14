package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.TaskContributorListAdapter;
import com.bc.caibiao.adapter.TaskDemandGridAdapter;
import com.bc.caibiao.model.DictionaryItem;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.qiming.ContributorActivity;
import com.bc.caibiao.view.AskQuestionDialog;
import com.bc.caibiao.view.CustomListView;
import com.bc.caibiao.view.MyGridView;
import com.bc.caibiao.view.TopBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务详情
 */

public class DashiTaskDetailActivity extends BaseActivity {
    private TopBarLayout topBar;
    private TextView tvLabel;
    private TextView tvTaskTitle;
    private TextView tvPrice;
    private TextView tvTrusteeship;
    private TextView tvSubmissionNum;
    private TextView tvEndTime;
    private TextView tvTaskId;
    private TextView tvDate;
    private TextView tvIndustry;
    private MyGridView gvProperty;
    private TextView tvTaskTip;
    private TextView tvPublisher;
    private TextView tvAllContributor;
    private CustomListView clvContributor;
    private TextView tvSubmit;
    private List<String> list = new ArrayList<>();
    private TaskContributorListAdapter adapter;
    private boolean isFromMe=false;

    private AskQuestionDialog askQuestionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashi_task_detail);
        initView();
    }

    private void initView() {
        isFromMe=getIntent().getBooleanExtra("isFromMe",false);
        this.tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        this.tvSubmit.setOnClickListener(this);
//        this.clvContributor = (CustomListView) findViewById(R.id.clvContributor);
        adapter = new TaskContributorListAdapter(mContext);
        list.add("");
        list.add("");
        adapter.setList(list);
//        clvContributor.setAdapter(adapter);
//        this.tvAllContributor = (TextView) findViewById(R.id.tvAllContributor);
        this.tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        this.tvTaskTip = (TextView) findViewById(R.id.tvTaskTip);
//        this.gvProperty = (MyGridView) findViewById(R.id.gvProperty);
        this.tvIndustry = (TextView) findViewById(R.id.tvIndustry);
        this.tvDate = (TextView) findViewById(R.id.tvDate);
        this.tvTaskId = (TextView) findViewById(R.id.tvTaskId);
        this.tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        this.tvSubmissionNum = (TextView) findViewById(R.id.tvSubmissionNum);
        this.tvTrusteeship = (TextView) findViewById(R.id.tvTrusteeship);
        this.tvPrice = (TextView) findViewById(R.id.tvPrice);
        this.tvTaskTitle = (TextView) findViewById(R.id.tvTaskTitle);
        this.tvLabel = (TextView) findViewById(R.id.tvLabel);
        this.topBar = (TopBarLayout) findViewById(R.id.topBar);

        if(isFromMe){
            this.tvSubmit.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:
                if(askQuestionDialog==null){
                    askQuestionDialog=new AskQuestionDialog(this);
                }
                askQuestionDialog.show();
                break;
            case R.id.llRight:

                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
