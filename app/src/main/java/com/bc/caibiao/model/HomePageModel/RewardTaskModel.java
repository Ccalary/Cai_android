package com.bc.caibiao.model.HomePageModel;

/**
 * Created by chengyanfang on 2017/4/17.
 *
 * "categoryId": 2,
 "requireDetail": "这里都能",
 "creatorPortrait": "http://imgcdn.58caibiao.com/filetest/316350bfd4f35e.png",
 "createdTimestamp": 1492070278,
 "": 1,
 "": "颜料油漆",
 "": 1101,
 "payMode": 2,
 "touGaoNum": 1,
 "state": 2,
 "itemContents": "中文名#寓意深厚",
 "taskMode": 1,
 "taskCode": "2017041300049",
 "priceYuan": 0,
 "taskTitle": "不想uu",
 "creatorName": "李浩",
 "expireTime": 1492502278,
 "price": 1,
 "paidTime": 1492074136,
 "namedCategory": 1,
 "taskId": 140
 */

public class RewardTaskModel {

    public String  categoryId;
    public String  requireDetail;
    public String  creatorPortrait;
    public String  createdTimestamp;
    public String  isXuanShangFenQian;
    public String  categoryName;

    public String  creatorId;
    public String  payMode;
    public String  touGaoNum;
    public String  state;
    public String  itemContents;


    public String  taskMode;
    public String  taskCode;
    public String  priceYuan;
    public String  taskTitle;
    public String  creatorName;


    public long  expireTime;
    public float  price;
    public String  paidTime;
    public String  namedCategory;
    public String  taskId;

    public String getPrice() {
        return price/100.0 +"";
    }
}
