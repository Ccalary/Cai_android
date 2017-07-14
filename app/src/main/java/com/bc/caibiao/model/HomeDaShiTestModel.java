package com.bc.caibiao.model;

import java.util.ArrayList;
import java.util.List;

public class HomeDaShiTestModel extends BaseTestModel {
    public String imgLogo;
    public String dashiName;
    public String dashiIntroduce;
    public String dashiSucCount;

    public static List<HomeDaShiTestModel> generateData(){
        List<HomeDaShiTestModel> list=new ArrayList<>();
        for(int i=0;i<3;i++){
            HomeDaShiTestModel model=new HomeDaShiTestModel();
            model.dashiName="大师"+i;
            model.dashiIntroduce="从事起名25年高端房产风水制定商业宝宝起名";
            model.dashiSucCount="339例";
            list.add(model);
        }
        return list;
    }
}
