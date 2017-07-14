package com.bc.caibiao.model.MarkModel;

/**
 * Created by chengyanfang on 2017/4/16.
 */

import java.util.ArrayList;

/**
 *
 * "intcls": "1",
 "appaddr": "西班牙马德以阿尔多斯德托雷洪28850NO.gc／立科哈",
 "follow": "false",
 "appname": "西班牙康达实验有限公司",
 "agentname": "广州市环信企业管理服务有限公司",
 "csdate": "2015-08-20",
 "image": "http://uat.58caibiao.com/image/catalog/trademark/15249264A.jpg",
 "cxkey": "15249264A",
 "tmname": "CONDA ESTABLISHED 1960",
 "appdate": "2014-08-28",
 "regdate": "2015-11-21",
 "regIssue": "1480",
 "gjzcrq": "",
 * */

public class MarkDetail {
    public String intcls;
    public String appaddr;
    public String follow;
    public String appname;
    public String agentname;
    public String csdate;
    public String image;
    public String cxkey;
    public String tmname;
    public String appdate;
    public String regdate;
    public String regIssue;
    public String gjzcrq;

    public ArrayList<MarkDetailGoods> goods = new ArrayList<>();
    public ArrayList<MarkDetailFlow> flow = new ArrayList<>();
}
