package com.bc.caibiao.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单实体类
 */

public class OrderBean {
    public int totalCount;
    public ArrayList<OrderItemBean> data = new ArrayList<>();;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<OrderItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<OrderItemBean> data) {
        this.data = data;
    }
}
