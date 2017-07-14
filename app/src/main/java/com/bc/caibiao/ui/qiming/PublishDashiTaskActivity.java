package com.bc.caibiao.ui.qiming;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.DashiPriceAdapter;
import com.bc.caibiao.model.DashiPriceModel;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.TimeUtil;
import com.bc.caibiao.view.ChooseDialog;
import com.bc.caibiao.view.MyListView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublishDashiTaskActivity extends BaseActivity implements View.OnClickListener, ChooseDialog.OnClickItemPosition {


    private DashiPriceAdapter dashiPriceAdapter;

    List<DashiPriceModel> list;

    private ChooseDialog chooseTimeDialog;
    private ChooseDialog chooseCategoryDialog;
    private TimePickerView timePickerView;

    private MyListView lvTaocan;
    private TextView tvBirthTime;
    private TextView tvBirthDate;
    private TextView tvCategory;

    private List<String> timeStrs = new ArrayList<>();
    private List<String> categoryStrs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dashi_task);
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
        lvTaocan = (MyListView) findViewById(R.id.lvTaocan);
        tvBirthTime = (TextView) findViewById(R.id.tvBirthTime);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvBirthDate = (TextView) findViewById(R.id.tvBirthDate);

        dashiPriceAdapter = new DashiPriceAdapter(this);
        list = DashiPriceModel.generateData();
        dashiPriceAdapter.addList(list);
        lvTaocan.setAdapter(dashiPriceAdapter);
        tvBirthTime.setOnClickListener(this);
        tvCategory.setOnClickListener(this);
        tvBirthDate.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tvBirthTime:
                if (chooseTimeDialog == null) {
                    chooseTimeDialog = new ChooseDialog(this);
                    for (int i = 0; i < 24; i++) {
                        timeStrs.add(i + "点");
                    }
                    chooseTimeDialog.setDatas(timeStrs);
                    chooseTimeDialog.setOnClickItemPosition(this);
                }
                chooseTimeDialog.show();
                break;
            case R.id.tvCategory:
                if (chooseCategoryDialog == null) {
                    chooseCategoryDialog = new ChooseDialog(this);
                    categoryStrs.add("01类 化工原料");
                    categoryStrs.add("02类 油漆涂料");
                    categoryStrs.add("03类 洗护用品");
                    categoryStrs.add("04类 工业油脂");
                    categoryStrs.add("05类 药品制剂");
                    categoryStrs.add("06类 五金器具");
                    chooseCategoryDialog.setDatas(categoryStrs);
                    chooseCategoryDialog.setOnClickItemPosition(this);
                }
                chooseCategoryDialog.show();
                break;
            case R.id.tvBirthDate:
                if(timePickerView==null)
                {
                    timePickerView=new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
                    timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date) {
                            String dateStr= TimeUtil.getStringFromTime(date,TimeUtil.FORMAT_YEAR_MONTH_DAY);
                            tvBirthDate.setText(dateStr);
                        }
                    });
                }
                timePickerView.show();
                break;
        }
    }

    @Override
    public void onClickItem(int position, ChooseDialog dialog) {
        if (dialog == chooseTimeDialog)
            tvBirthTime.setText(timeStrs.get(position));
        else
            tvCategory.setText(categoryStrs.get(position));
    }
}
