package com.bc.caibiao.model.HomePageModel;

/**
 * Created by chengyanfang on 2017/4/18.
 *
 *
 "categoryId" : 1,
 "requireDetail" : "1",
 "wordsNum" : "1",
 "birthday" : 1,
 "paidTime" : 1489650136,

 "creatorPortrait" : "http:\/\/wx.qlogo.cn\/mmopen\/PiajxSqBRaEIcViby2e4ZCC8wwmNbPYAjNTrANX6AplHkprnuZYUITUmWssv62psBPPW369f05fDd7lJVncDMBug\/0",
 "createdTimestamp" : 1489722956,
 "isXuanShangFenQian" : 1,
 "categoryName" : "化学燃料",
 "creatorId" : 1057,
 "payMode" : 2,

 "touGaoNum" : 4,
 "state" : 2,
 "dashiMemberId" : 1,
 "birthhour" : 1,
 "itemContents" : "可注册#中文名#寓意深刻#小清新",

 "taskMode" : 1,
 "taskCode" : "2017022000002",
 "priceYuan" : 10,
 "taskTitle" : "任务1",
 "creatorName" : "朱燕雯",

 "expireTime" : 1492401356,
 "price" : 1000,
 "bossName" : "1",
 "namedCategory" : 1,
 "taskId" : 1
 *
 */

public class PlaygroundTask {
    public String categoryId;
    public String requireDetail;
    public String wordsNum;
    public String birthday;
    public String paidTime;

    public String creatorPortrait;
    public String createdTimestamp;
    public String isXuanShangFenQian;
    public String categoryName;
    public String creatorId;
    public String payMode;

    public String touGaoNum;
    public String state;
    public String dashiMemberId;
    public String dashiMemberName;
    public String birthhour;
    public String itemContents;

    public String taskMode;
    public String taskCode;
    public String priceYuan;
    public String taskTitle;
    public String creatorName;

    public long expireTime;
    public float price;
    public String bossName;
    public String namedCategory;
    public String taskId;


    public String getPrice() {
        return price/100.0 +"";
    }
}
