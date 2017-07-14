package com.bc.caibiao.model;

import com.bc.caibiao.model.HomePageModel.PlaygroundTaskList;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class CollectMarkItemModel {
    public int pageSize;
    public int start;
    public int totalCount;
    public ArrayList<TrademarkFollow> data = new  ArrayList<>();
}
