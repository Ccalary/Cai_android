package com.bc.caibiao.model.HomePageModel;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/20.
 *
 *
 * "state": 0,
 "totalAskNum": 0,
 "touGaoId": 131,
 "isCheck": 0,
 "isUsed": 0,
 "isCanAsk": 1,
 "creatorPortrait": "http://imgcdn.58caibiao.com/file/2f94c7b955e5be.png",
 "price": 0,
 "taskAskAnswerList": [],
 "creatorName": "李浩",
 "taskId": 155,
 "reason": "不知道",
 "creatorId": 1101,
 "createdTimestamp": 1492590047,
 "brandName": "无所不能",
 "isCanAnswer": 0,
 "picPaths": [],
 "priceYuan": 0
 *
 *
 */

public class TouGaoer {

    public String creatorPortrait;
    public String creatorId;
    public String priceYuan;
    public long createdTimestamp;
    public String brandName;
    public String isCanAnswer;

    public String isCanAsk;
    public String price;
    public String creatorName;
    public String taskId;
    public String reason;

    public String state;
    public String totalAskNum;
    public String touGaoId;
    public String isCheck;
    public String isUsed;

    public ArrayList<TouGaoAnswer> taskAskAnswerList = new ArrayList<>();
    public ArrayList<String> picPaths = new ArrayList<>();
}
