package com.bc.caibiao.model;

/**
 * 会员提现申请 实体类.
 * @author zhaochunjiao
 * @create 2015年11月9日 下午4:13:04
 * @version 1.0
 */
public class MemberWithdrawCashApply {
	/* 状态-处理中 */
	public static final short STATE_DEALING = 1;
	/* 状态-处理完毕(提现成功) */
	public static final short STATE_SUCCESS = 2;
	/* 状态-已关闭*/
	public static final short STATE_FAILED = 9;
	
	/* 申请标识 */
	protected int applyId;
	/* 提现流水号 */
	protected String applyCode;
	/* 提现金额(分) */
	protected int applyAmount;
	/* 支付宝账户 */
	protected String alipayAccount;
	/* 提现者姓名 */
	protected String ownerName;
	/* 状态，取值见常量 */
	protected short state;
	/* 申请会员标识 */
	protected int memberId;
	/* 申请会员昵称 */
	protected String nickName;
	/* 申请时戳 */
	protected int createdTime;
	/* 处理人标识 */
	protected Integer handlerId;
	/* 处理人姓名 */
	protected String handlerName;
	/* 处理时戳 */
	protected Integer handledTime;
	/* 备注/原因 */
	protected String remark;
	
	/*=========== 冗余 ===============*/
	/* 提现金额(元) */
	protected Double applyAmountYuan;
	
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	
	public int getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(int applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getAlipayAccount() {
		return alipayAccount;
	}
	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}
	public short getState() {
		return state;
	}
	public void setState(short state) {
		this.state = state;
	}
	public int getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(int createdTime) {
		this.createdTime = createdTime;
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
	public Integer getHandledTime() {
		return handledTime;
	}
	public void setHandledTime(Integer handledTime) {
		this.handledTime = handledTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Double getApplyAmountYuan() {
		return applyAmountYuan;
	}
	public void setApplyAmountYuan(Double applyAmountYuan) {
		this.applyAmountYuan = applyAmountYuan;
	}
}
