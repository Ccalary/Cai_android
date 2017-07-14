package com.bc.caibiao.model;

import com.bc.caibiao.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ShangbiaoSuggestModel extends BaseTestModel {
    public String name;
    public int res;
    public String endTime;
    public String money;
    public boolean isShow;
    private static final int RES[] = {R.drawable.icon_songbanquan, R.drawable.icon_songwaiguan,
            R.drawable.icon_songtuxing, R.drawable.icon_guonei, R.drawable.icon_guoji, R.drawable.icon_zhuzuoquan,
            R.drawable.icon_zhengshu, R.drawable.icon_zhuanli, R.drawable.icon_xuzhan , R.drawable.icon_biangeng};
    private static final String NAMES[] = {"商标注册送版权", "商标注册送外观专利",
            "商标注册送图形设计", "国内商标注册", "国际商标注册", "软件著作权",
            "补发商标证书", "实用新型专利", "商标续展" , "商标变更"};

    public static List<ShangbiaoSuggestModel> generateData(boolean isShow) {
        java.util.Random r=new java.util.Random();
        List<ShangbiaoSuggestModel> list = new ArrayList<>();
        for (int i = 0; i < RES.length; i++) {
            ShangbiaoSuggestModel model = new ShangbiaoSuggestModel();
            model.name = NAMES[i];
            model.res = RES[i];
            model.isShow=isShow;
            model.money="¥"+ (r.nextInt(300)+1500);
            list.add(model);
        }
        return list;
    }

}
