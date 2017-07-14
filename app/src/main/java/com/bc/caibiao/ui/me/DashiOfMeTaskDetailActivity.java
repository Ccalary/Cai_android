package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.DashiQiMingAdapter;
import com.bc.caibiao.adapter.TaskContributorListAdapter;
import com.bc.caibiao.model.DashiQimingModel;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.view.AskQuestionDialog;
import com.bc.caibiao.view.CustomListView;
import com.bc.caibiao.view.MyGridView;
import com.bc.caibiao.view.MyListView;
import com.bc.caibiao.view.TopBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务详情
 */

public class DashiOfMeTaskDetailActivity extends BaseActivity {
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

    private AskQuestionDialog askQuestionDialog;
    private TextView tvFinish;
    private TextView tvWordCount;
    private TextView tvBirthTime;
    private com.bc.caibiao.view.MyListView lvQiming;

    private DashiQiMingAdapter dashiQiMingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashi_me_task_detail);
        this.tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        this.lvQiming = (MyListView) findViewById(R.id.lvQiming);
        this.tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        this.tvTaskTip = (TextView) findViewById(R.id.tvTaskTip);
        this.tvBirthTime = (TextView) findViewById(R.id.tvBirthTime);
        this.tvWordCount = (TextView) findViewById(R.id.tvWordCount);
        this.tvIndustry = (TextView) findViewById(R.id.tvIndustry);
        this.tvDate = (TextView) findViewById(R.id.tvDate);
        this.tvTaskId = (TextView) findViewById(R.id.tvTaskId);
        this.tvFinish = (TextView) findViewById(R.id.tvFinish);
        this.tvPrice = (TextView) findViewById(R.id.tvPrice);
        this.tvTaskTitle = (TextView) findViewById(R.id.tvTaskTitle);
        this.tvLabel = (TextView) findViewById(R.id.tvLabel);
        this.topBar = (TopBarLayout) findViewById(R.id.topBar);
        initView();
    }

    private void initView() {
        this.tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        this.tvSubmit.setOnClickListener(this);
        adapter = new TaskContributorListAdapter(mContext);
        list.add("");
        list.add("");
        adapter.setList(list);
//        clvContributor.setAdapter(adapter);
        this.tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        this.tvTaskTip = (TextView) findViewById(R.id.tvTaskTip);
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
        dashiQiMingAdapter=new DashiQiMingAdapter(this);
        List<DashiQimingModel> list=new ArrayList<>();
        list.add(new DashiQimingModel());
        dashiQiMingAdapter.addList(list);
        lvQiming.setAdapter(dashiQiMingAdapter);
    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:
                Intent addIntent=new Intent(DashiOfMeTaskDetailActivity.this,DashiQimingEditActivity.class);
                addIntent.putExtra("isAdd",true);
                       startActivity(addIntent);
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
