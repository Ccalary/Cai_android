package com.bc.caibiao.model.HomePageModel;

import com.bc.caibiao.utils.StringUtil;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/20.
 *
 * "priceYuan" : 980,
 "state" : 0,
 "picList" : [
 {
 "picPath" : "\/2a53f05cf462a0.jpg",
 "id" : 1,
 "picTitle" : "998",
 "daShiPackageId" : 1
 },
 {
 "picPath" : "\/2a53f05cf462a0.jpg",
 "id" : 2,
 "picTitle" : "998",
 "daShiPackageId" : 1
 }
 ],
 "daShiPackageId" : 1,
 "price" : 98000
 }
 */

public class SignPrice {

    public String priceYuan;
    public String state;
    public String daShiPackageId;
    public int price;

    public boolean isSelected = false;

    public ArrayList<SignPriceChild> picList = new ArrayList<>();

    public String getPrice() {
        return price +"";
    }

    public String getPriceFen() {
        return (price * 100) +"";
    }


    public String getYuanPrice(){
        double yuan = (float)price / 100.0;
        return StringUtil.getFormatedFloatString(String.valueOf(yuan));
    }

}
