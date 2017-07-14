package com.bc.caibiao.model.MarkModel;


import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/16.
 *
 * 推荐产品外层数据类
 */

public class MarkModelData {
    int pageSize;
    int totalCount;
    int start;
    public ArrayList<MarkRecommandProduct> data = new ArrayList<>();
}
