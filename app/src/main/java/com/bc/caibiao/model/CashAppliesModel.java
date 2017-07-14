package com.bc.caibiao.model;

import java.util.ArrayList;

/**
 * Created by Austriee on 2017/4/19.
 */

public class CashAppliesModel {
    public int pageSize;
    public int start;
    public int totalCount;
    public ArrayList<MemberWithdrawCashApply> data = new  ArrayList<>();
}
