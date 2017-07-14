package com.bc.caibiao.model;

/**
 * 会员账户流水
 * @author zhaochunjiao
 * @create 2015年11月2日 下午2:24:57
 * @version 1.0
 */
public class MemberAccountFlow {
	/* 状态-普通 */
	public static final short STATE_NORMAL = 0;
	/* 状态-已删除 */
	public static final short STATE_DELETE = 9;
	
	/* 支出-变动类型-提现申请 */
	public static final short FLOWTYPE_WITHDRAWCASH_APPLY = 1;
	/* 收入-变动类型-提现失败，退还提现金额 */
	public static final short FLOWTYPE_WITHDRAWCASH_FAILED = 2;
	/* 收入-变动类型-投稿被采纳 */
	public static final short FLOWTYPE_TOUGAO_CAINA = 3;
	/* 收入-变动类型-悬赏到期，退还金额 */
	public static final short FLOWTYPE_REBACK_XUANSHANG_PRICE = 4;
	/* 收入-变动类型-平分赏金（悬赏起名） */
	public static final short FLOWTYPE_SHARE_XUANSHANG_PRICE = 5;
	/* 收入-变动类型-大师被邀请起名 */
	public static final short FLOWTYPE_DASHI_QIMING_PRICE = 6;
	
	
	/* 变动原因-会员申请提现*/
	public static final String FLOWTYPE_WITHDRAWCASH_APPLY_REASON = "申请提现";
	/* 变动原因详述-会员申请提现 */
	public static final String FLOWTYPE_WITHDRAWCASH_APPLY_REASON_DETAIL = "申请提现";
	
	/* 变动原因-会员提现失败*/
	public static final String FLOWTYPE_WITHDRAWCASH_APPLY_FAIL_REASON = "提现失败";
	/* 变动原因详述-会员提现失败 */
	public static final String FLOWTYPE_WITHDRAWCASH_APPLY_FAIL_REASON_DETAIL = "提现失败";
	
	/* 变动原因-投稿被采纳*/
	public static final String FLOWTYPE_TOUGAO_CAINA_REASON = "稿费";
	/* 变动原因详述-投稿被采纳 */
	public static final String FLOWTYPE_TOUGAO_CAINA_REASON_DETAIL = "投稿被采纳";
	
	/* 变动原因-退还悬赏金额*/
	public static final String FLOWTYPE_REBACK_XUANSHANG_PRICE_REASON = "退还赏金";
	/* 变动原因详述-退还悬赏金额 */
	public static final String FLOWTYPE_REBACK_XUANSHANG_PRICE_REASON_DETAIL = "悬赏到期，退还金额";
	
	/* 变动原因-平分赏金*/
	public static final String FLOWTYPE_DASHI_QIMING_PRICE_REASON = "稿费";
	/* 变动原因详述-平分赏金 */
	public static final String FLOWTYPE_DASHI_QIMING_PRICE_REASON_DETAIL = "悬赏到期，平分赏金";
	
	/* 变动原因-大师被邀请起名*/
	public static final String FLOWTYPE_SHARE_XUANSHANG_PRICE_REASON = "大师稿费";
	/* 变动原因详述-大师被邀请起名 */
	public static final String FLOWTYPE_SHARE_XUANSHANG_PRICE_REASON_DETAIL = "大师被邀请起名";
	
	
	/* 流水标识 */
	protected int flowId;
	/* 会员标识 */
	protected int memberId;
	/* 变动类型，取值见常量 */
	protected short flowType;
	/* 变动金额(分) */
	protected int flowAmount;
	/* 变动后余额 (分)*/
	protected int accountBalance;
	/* 任务模块，取值见EntityType中的常量 */
	protected Short taskMode;
	/* 任务标识 */
	protected Integer taskId;
	/* 投稿标识 */
	protected Integer touGaoId;
	/* 复查标识 */
	protected Integer recheckId;
	/* 提现申请标识 */
	protected Integer cashApplyId;
	/* 分成基数价(分) */
	protected Integer basePrice;
	/* 分成比例 */
	protected Double earnPrecent;
	/* 变动原因 */
	protected String reason;
	/* 变动原因描述 */
	protected String reasonDetail;
	
	/* 
	 * 冗余属性针对不同的变动类型，含义不同：
	 * 
	 */
	/* 冗余属性1 */
	protected String attr1;
	/* 冗余属性2 */
	protected String attr2;
	/* 冗余属性3 */
	protected String attr3;
	/* 冗余属性4 */
	protected String attr4;
	/* 冗余属性5 */
	protected String attr5;
	/* 创建时戳 */
	protected int createdTime;
	/* 日期，yyyyMMdd */
	protected int day;
	/* 日期所在月，yyyyMM */
	protected int month;
	/* 状态，预留 */
	protected short state;
	
	/*=========== 冗余 ===============*/
	/* 变动金额(元) */
	protected Double flowAmountYuan;
	/* 变动后余额 (元)*/
	protected Double accountBalanceYuan;
	
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	
	public short getFlowType() {
		return flowType;
	}
	public void setFlowType(short flowType) {
		this.flowType = flowType;
	}
	public int getFlowAmount() {
		return flowAmount;
	}
	public void setFlowAmount(int flowAmount) {
		this.flowAmount = flowAmount;
	}
	public int getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}
	public Integer getCashApplyId() {
		return cashApplyId;
	}
	public void setCashApplyId(Integer cashApplyId) {
		this.cashApplyId = cashApplyId;
	}
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public String getAttr3() {
		return attr3;
	}
	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
	public String getAttr4() {
		return attr4;
	}
	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}
	public int getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(int createdTime) {
		this.createdTime = createdTime;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public short getState() {
		return state;
	}
	public void setState(short state) {
		this.state = state;
	}
	public String getAttr5() {
		return attr5;
	}
	public void setAttr5(String attr5) {
		this.attr5 = attr5;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getReasonDetail() {
		return reasonDetail;
	}
	public void setReasonDetail(String reasonDetail) {
		this.reasonDetail = reasonDetail;
	}
	public Double getFlowAmountYuan() {
		return flowAmountYuan;
	}
	public void setFlowAmountYuan(Double flowAmountYuan) {
		this.flowAmountYuan = flowAmountYuan;
	}
	public Double getAccountBalanceYuan() {
		return accountBalanceYuan;
	}
	public void setAccountBalanceYuan(Double accountBalanceYuan) {
		this.accountBalanceYuan = accountBalanceYuan;
	}
	public Short getTaskMode() {
		return taskMode;
	}
	public void setTaskMode(Short taskMode) {
		this.taskMode = taskMode;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getRecheckId() {
		return recheckId;
	}
	public void setRecheckId(Integer recheckId) {
		this.recheckId = recheckId;
	}
	public Integer getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Integer basePrice) {
		this.basePrice = basePrice;
	}
	public Double getEarnPrecent() {
		return earnPrecent;
	}
	public void setEarnPrecent(Double earnPrecent) {
		this.earnPrecent = earnPrecent;
	}
	public Integer getTouGaoId() {
		return touGaoId;
	}
	public void setTouGaoId(Integer touGaoId) {
		this.touGaoId = touGaoId;
	}
}
