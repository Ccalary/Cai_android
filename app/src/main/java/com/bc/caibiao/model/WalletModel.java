package com.bc.caibiao.model;

import java.util.ArrayList;
import java.util.List;


public class WalletModel extends BaseTestModel {
    public String title;
    public int type;
    public String time;
    public String money;
    public String balance;

    public static List<WalletModel> generateData() {
        List<WalletModel> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            WalletModel model = new WalletModel();
            if (i == 0) {
                model.title = "取现";
                model.time = "2017-03-03 16:09";
                model.type = 1;
                model.money = "20.00";
                model.balance = "300.00";

            } else {
                model.title = "投稿被采纳";
                model.time = "2017-03-01 16:09";
                model.type = 2;
                model.money = "50.00";
                model.balance = "200.00";
            }
            list.add(model);
        }
        return list;
    }

}
