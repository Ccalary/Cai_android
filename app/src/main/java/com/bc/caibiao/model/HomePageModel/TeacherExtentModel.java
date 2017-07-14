package com.bc.caibiao.model.HomePageModel;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/17.
 *
 * "earnPrecent": 0,
 "touGaoTotalNum": 31,
 "xuanShangTotalPrice": 1,
 "daShiTaskNum2": 1,
 "messageNum": 0,
 "memberId": 680,
 "xuanShangNum": 1,
 "usedTotalNum": 0,
 "totalIncomePrice": 0,
 "daShiTaskNum": 1,
 "teDian": "",
 "introduce": "中国《超级识人学》创始人，陈抟相法传承传播者，著名国学老师，国学易道专家、书法家，以易学、禅学哲学研究、相学研究以及心灵学为主要方向。现任易行文化研究院院长，广东企业文化创新促进会会长、广东中小企业文化创新促进会名誉、易行文化发展有限公司创始人。",
 "daShiTaskTotalPrice": 2
 *
 */

public class TeacherExtentModel {

    public String earnPrecent;
    public String touGaoTotalNum;
    public String xuanShangTotalPrice;
    public String daShiTaskNum2;
    public String messageNum;

    public String memberId;
    public String xuanShangNum;
    public String usedTotalNum;
    public String totalIncomePrice;

    public String daShiTaskNum;
    public String provinceName;
    public String teDian;
    public String introduce;
    public String daShiTaskTotalPrice;

    public ArrayList<String> getTedianList(){

        ArrayList<String> tedians = new ArrayList<>();

        if(TextUtils.isEmpty(teDian)){
            return tedians;
        }

        String[] tedian = teDian.split("#");

        for(String tag : tedian){
            tedians.add(tag);
        }

        return tedians;
    }

}
