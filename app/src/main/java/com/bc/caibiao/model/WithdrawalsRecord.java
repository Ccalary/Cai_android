package com.bc.caibiao.model;

import java.util.ArrayList;
import java.util.List;


public class WithdrawalsRecord extends BaseTestModel {
    public String account;
    public int state;
    public String time;
    public String money;
    public String reason;

    public static List<WithdrawalsRecord> generateData() {
        List<WithdrawalsRecord> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            WithdrawalsRecord model = new WithdrawalsRecord();
            if (i == 0) {
                model.account = "80**@qq.com";
                model.time = "2017-03-03 16:09";
                model.state = 0;
                model.money = "20元";


            } else if (i == 1){
                model.account = "80**@qq.com";
                model.time = "2017-03-01 16:09";
                model.state = 1;
                model.money = "50元";
            }else{
                model.account = "80**@qq.com";
                model.time = "2017-03-03 16:09";
                model.state = 2;
                model.money = "50元";
                model.reason = "原因：支付宝账户与您的真实姓名不符，请重新输入";
            }
            list.add(model);
        }
        return list;
    }

}
