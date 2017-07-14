package com.bc.caibiao.model;

import java.util.ArrayList;
import java.util.List;


public class DashiPriceModel extends BaseTestModel {
    public String title;
    public int labelType;
    public String endTime;
    public String price;
    public boolean isCheck;
    public int picCount;
    public static List<DashiPriceModel> generateData(){
        List<DashiPriceModel> list=new ArrayList<>();
        for(int i=0;i<3;i++){
            DashiPriceModel model=new DashiPriceModel();
            model.title="帮忙取名";
            model.endTime="7天后截至";
            model.labelType=1;
            model.price="998元";
            model.picCount=i+1;
            list.add(model);
        }
        return list;
    }

}
