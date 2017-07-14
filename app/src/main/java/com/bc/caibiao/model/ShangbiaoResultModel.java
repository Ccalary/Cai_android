package com.bc.caibiao.model;

import com.bc.caibiao.R;

import java.util.ArrayList;
import java.util.List;


public class ShangbiaoResultModel extends BaseTestModel {
    public String name;
    public int res;
    public String num;
    public String money;
    private static final int RES[] = {R.drawable.pic_result_a, R.drawable.pic_result_b,
            R.drawable.pic_result_c, R.drawable.pic_result_d};
    private static final String NAMES[] = {"11406405歌帝尔森", "11406405瑞德科技",
            "11406405歌帝尔森", "11406405瑞德科技"};
    private static final String NUM[] = {"16", "22",
            "16", "22"};

    public static List<ShangbiaoResultModel> generateData() {
        List<ShangbiaoResultModel> list = new ArrayList<>();
        for (int i = 0; i < RES.length; i++) {
            ShangbiaoResultModel model = new ShangbiaoResultModel();
            model.name = NAMES[i];
            model.res = RES[i];
            model.num = NUM[i];
            list.add(model);
        }
        return list;
    }

}
