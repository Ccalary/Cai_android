package com.bc.caibiao.model.MarkModel;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class TaskPrice {
    public String id;
    public String groupCode;
    public String itemContent;

    public String getPublishPrice(){
        float price = 0f;

        try {
            price = Float.parseFloat(itemContent);
            price = price * 100;
        }catch (Exception e){}

        int intprice = (int)price;

        return intprice+"";
    }

}
