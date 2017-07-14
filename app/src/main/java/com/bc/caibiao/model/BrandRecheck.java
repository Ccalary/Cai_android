package com.bc.caibiao.model;

/**
 * 商标复查实体类
 * @author zhaochunjiao
 * @create 2017年2月20日 下午2:55:55
 * @version 1.0
 */
public class BrandRecheck {
	/* 状态-新建(未支付) */
	public static final short STATE_NEW  = 0;
	/* 状态-待处理(已支付) */
	public static final short STATE_WAIT  = 1;
	/* 状态-已处理 */
	public static final short STATE_HANDLED  = 2;
	
	/* 商标复查标识 */
	protected int recheckId;
	/* 复查流水号*/
	protected String recheckCode;
	/* 状态，取值见本类的常量 */
	protected short state;
	/* 商标名称*/
	protected String brandName;
	/* 复查费用(分)*/
	protected int price;
	/* 支付方式，取值见ChangLiang中的定义 */
	protected short payMode;
	/* 提交人标识 */
	protected int creatorId;
	/* 提交人姓名 */
	protected String creatorName;
	/* 提交时戳 */
	protected int createdTimestamp;
	/* 处理人标识 */
	protected Integer handlerId;
	/* 处理人姓名 */
	protected String handlerName;
	/* 处理时戳 */
	protected Integer handledTime;
	/* 处理结果 */
	protected String resultText;
	/* 处理结果图片 */
	protected String resultPic;
	
	public int getRecheckId() {
		return recheckId;
	}
	public void setRecheckId(int recheckId) {
		this.recheckId = recheckId;
	}
	public String getRecheckCode() {
		return recheckCode;
	}
	public void setRecheckCode(String recheckCode) {
		this.recheckCode = recheckCode;
	}
	public short getState() {
		return state;
	}
	public void setState(short state) {
		this.state = state;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public short getPayMode() {
		return payMode;
	}
	public void setPayMode(short payMode) {
		this.payMode = payMode;
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
	public int getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(int createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public String getHandlerName() {
		return handlerName;
	}
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
	public Integer getHandledTime() {
		return handledTime;
	}
	public void setHandledTime(Integer handledTime) {
		this.handledTime = handledTime;
	}
	public String getResultText() {
		return resultText;
	}
	public void setResultText(String resultText) {
		this.resultText = resultText;
	}
	public String getResultPic() {
		return resultPic;
	}
	public void setResultPic(String resultPic) {
		this.resultPic = resultPic;
	}
	public Integer getHandlerId() {
		return handlerId;
	}
	public void setHandlerId(Integer handlerId) {
		this.handlerId = handlerId;
	}
}
