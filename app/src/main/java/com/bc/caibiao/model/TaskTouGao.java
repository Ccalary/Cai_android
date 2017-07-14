package com.bc.caibiao.model;

import java.util.List;


/**
 * 任务投稿起名
 * @author zhaochunjiao
 * @create 2017年2月16日 下午6:16:32
 * @version 1.0
 */
public class TaskTouGao {
	/* 状态-正常  */
	public static final short STATE_NEW  = 0;
	/* 状态-已删除 */
	public static final short STATE_DELETED  = 9;
	
	/* 任务投稿起名标识 */
	protected int touGaoId;
	/* 状态，取值见本类的常量 */
	protected short state;
	/* 任务标识 */
	protected int taskId;
	/* 投稿名称*/
	protected String brandName;
	/* 是否被采纳，取值见ChangLiang中的常量 */
	protected short isUsed;
	/* 是否查询，取值见ChangLiang中的常量 */
	protected short isCheck;
	/* 是否可提问，取值见ChangLiang中的常量 */
	protected short isCanAsk;
	/* 累计提问次数 */
	protected int totalAskNum;
	/* 释义说明*/
	protected String reason;
	/* 投稿人标识 */
	protected int creatorId;
	/* 投稿人姓名 */
	protected String creatorName;
	/* 投稿人头像 */
	protected String creatorPortrait;
	/* 投稿人手机号 */
	protected String creatorPhone;
	/* 投稿时戳 */
	protected int createdTimestamp;
	/* 举报处理时戳 */
	protected Integer handledTime;
	/* 举报处理人标识 */
	protected Integer handlerId;
	/* 举报处理人姓名 */
	protected String handlerName;
	/* 最后修改时戳 */
	protected Integer lastModified;
	
	
	/*============= 冗余 ====================*/
	/*投稿释义图片路径集合*/
	private List<String> picPaths;
	/* 投稿的提问或回复集合*/
	private List<TaskAskAnswer> taskAskAnswerList;
	/* 任务编号*/
	private String taskCode;
	/* 任务金额-分*/
	private int price;
	/* 任务金额-元*/
	private double priceYuan;
	
	public double getPriceYuan() {
		return priceYuan;
	}
	public void setPriceYuan(double priceYuan) {
		this.priceYuan = priceYuan;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getTouGaoId() {
		return touGaoId;
	}
	public void setTouGaoId(int touGaoId) {
		this.touGaoId = touGaoId;
	}
	public short getState() {
		return state;
	}
	public void setState(short state) {
		this.state = state;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public short getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(short isUsed) {
		this.isUsed = isUsed;
	}
	public short getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(short isCheck) {
		this.isCheck = isCheck;
	}
	public short getIsCanAsk() {
		return isCanAsk;
	}
	public void setIsCanAsk(short isCanAsk) {
		this.isCanAsk = isCanAsk;
	}
	public int getTotalAskNum() {
		return totalAskNum;
	}
	public void setTotalAskNum(int totalAskNum) {
		this.totalAskNum = totalAskNum;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getCreatorPortrait() {
		return creatorPortrait;
	}
	public void setCreatorPortrait(String creatorPortrait) {
		this.creatorPortrait = creatorPortrait;
	}
	public int getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(int createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public List<String> getPicPaths() {
		return picPaths;
	}
	public void setPicPaths(List<String> picPaths) {
		this.picPaths = picPaths;
	}
	public List<TaskAskAnswer> getTaskAskAnswerList() {
		return taskAskAnswerList;
	}
	public void setTaskAskAnswerList(List<TaskAskAnswer> taskAskAnswerList) {
		this.taskAskAnswerList = taskAskAnswerList;
	}
	public String getCreatorPhone() {
		return creatorPhone;
	}
	public void setCreatorPhone(String creatorPhone) {
		this.creatorPhone = creatorPhone;
	}
	public Integer getHandledTime() {
		return handledTime;
	}
	public void setHandledTime(Integer handledTime) {
		this.handledTime = handledTime;
	}
	public Integer getHandlerId() {
		return handlerId;
	}
	public void setHandlerId(Integer handlerId) {
		this.handlerId = handlerId;
	}
	public String getHandlerName() {
		return handlerName;
	}
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
	public Integer getLastModified() {
		return lastModified;
	}
	public void setLastModified(Integer lastModified) {
		this.lastModified = lastModified;
	}
}
