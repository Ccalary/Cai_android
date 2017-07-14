package com.bc.caibiao.model;

/**
 * Created by Administrator on 2017/5/4.
 */

public class CornerMarkModel {
    /* 未读推送消息数 */
    private int messageNum;
    /* 未读悬赏任务消息数 */
    private int xuanShangTaskMsgNum;
    /* 未读大师任务消息数 */
    private int daShiTaskMsgNum;
    /* 未读我的投稿消息数 */
    private int myTouGaoMsgNum;
    /* 未读我是大师消息数 */
    private int iAmDaShiMsgNum;
    /* 未读复查结果消息数 */
    private int recheckMsgNum;

    public CornerMarkModel(int messageNum, int xuanShangTaskMsgNum, int daShiTaskMsgNum, int myTouGaoMsgNum, int iAmDaShiMsgNum, int recheckMsgNum) {
        this.messageNum = messageNum;
        this.xuanShangTaskMsgNum = xuanShangTaskMsgNum;
        this.daShiTaskMsgNum = daShiTaskMsgNum;
        this.myTouGaoMsgNum = myTouGaoMsgNum;
        this.iAmDaShiMsgNum = iAmDaShiMsgNum;
        this.recheckMsgNum = recheckMsgNum;
    }

    public int getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }

    public int getXuanShangTaskMsgNum() {
        return xuanShangTaskMsgNum;
    }

    public void setXuanShangTaskMsgNum(int xuanShangTaskMsgNum) {
        this.xuanShangTaskMsgNum = xuanShangTaskMsgNum;
    }

    public int getDaShiTaskMsgNum() {
        return daShiTaskMsgNum;
    }

    public void setDaShiTaskMsgNum(int daShiTaskMsgNum) {
        this.daShiTaskMsgNum = daShiTaskMsgNum;
    }

    public int getMyTouGaoMsgNum() {
        return myTouGaoMsgNum;
    }

    public void setMyTouGaoMsgNum(int myTouGaoMsgNum) {
        this.myTouGaoMsgNum = myTouGaoMsgNum;
    }

    public int getiAmDaShiMsgNum() {
        return iAmDaShiMsgNum;
    }

    public void setiAmDaShiMsgNum(int iAmDaShiMsgNum) {
        this.iAmDaShiMsgNum = iAmDaShiMsgNum;
    }

    public int getRecheckMsgNum() {
        return recheckMsgNum;
    }

    public void setRecheckMsgNum(int recheckMsgNum) {
        this.recheckMsgNum = recheckMsgNum;
    }
}
