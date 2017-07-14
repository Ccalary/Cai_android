package com.bc.caibiao.model.HomePageModel;

import android.text.TextUtils;

import com.bc.caibiao.utils.StringUtil;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/20.
 *
 *"itemContents": "时尚个性",
 "taskMode": 1,
 "taskCode": "2017041900003",
 "priceYuan": 0.01,
 "taskTitle": "理工",
 "creatorName": "渐醒AINY",
 "expireTime": 1493022008,
 "price": 1,
 "paidTime": 1492590019,
 "namedCategory": 1,
 "taskId": 155
 */

public class TaskDetailInnerModel {

    public String itemContents;
    public String taskMode;
    public String taskCode;
    public String wordsNum;
    public String birthhour;
    public String priceYuan;
    public String taskTitle;
    public String creatorName;
    public long expireTime;
    public float price;
    public String paidTime;
    public String namedCategory;
    public String taskId;
    public String dashiPortrait;
    public String dashiName;
    public String teDian;


    public String categoryId;
    public String requireDetail;
    public String categocreatorPortraitryId;
    public long createdTimestamp;
    public String isXuanShangFenQian;
    public String categoryName;
    public String creatorId;
    public String payMode;
    public String touGaoNum;
    public String state;


    public ArrayList<TouGaoer> taskTouGaoList = new ArrayList<>();



    public String getFenPrice(){

        return StringUtil.getFormatedFloatString((price/100)+"");

    }


    public ArrayList<String> getTedianList(){

        ArrayList<String> tedians = new ArrayList<>();

        if(TextUtils.isEmpty(itemContents)){
            return tedians;
        }

        String[] tedian = itemContents.split("#");

        for(String tag : tedian){
            tedians.add(tag);
        }

        return tedians;
    }




}
