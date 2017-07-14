package com.bc.caibiao.model;

import java.util.ArrayList;
import java.util.List;


public class HomeXuanShangTestModel extends BaseTestModel {
    public String title;
    public int labelType;
    public String endTime;
    public String money;
    public static List<HomeXuanShangTestModel> generateData(){
        List<HomeXuanShangTestModel> list=new ArrayList<>();
        for(int i=0;i<5;i++){
            HomeXuanShangTestModel model=new HomeXuanShangTestModel();
            model.title="帮忙取名";
            model.endTime="7天后截至";
            model.labelType=1;
            model.money="￥20";
            list.add(model);
        }
        return list;
    }

}
