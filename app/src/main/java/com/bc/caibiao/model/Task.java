package com.bc.caibiao.model;

import java.util.List;

/**
 * 任务实体类
 * @author zhaochunjiao
 * @create 2017年2月16日 下午5:49:08
 * @version 1.0
 */
public class Task {
	/* 状态-未支付 */
	public static final short STATE_UNPAID  = 1;
	/* 状态-已支付 */
	public static final short STATE_PAID  = 2;
	/* 状态 -已删除 */
	public static final short STATE_DELETED = 9;
	
	/* 起名分类 -商标起名 */
	public static final short NAMED_CATEGORY_BRAMD = 1;
	/* 起名分类 -公司起名 */
	public static final short NAMED_CATEGORY_COMPANY = 2;
	
	/* 任务所属模块 -悬赏起名 */
	public static final short TASK_MODE_XUANSHANG = 1;
	/* 任务所属模块 -大师起名 */
	public static final short TASK_MODE_DASHI = 2;
	
	/* 任务标识 */
	protected int taskId;
	/* 任务流水编号*/
	protected String taskCode;
	/* 状态，取值见本类的常量 */
	protected short state;
	/* 起名分类，取值见本类的常量 */
	protected short namedCategory;
	/* 任务标题*/
	protected String taskTitle;
	/* 任务所属模块，取值见本类的常量 */
	protected short taskMode;
	/* 所属行业分类标识 */
	protected int categoryId;
	/* 所属行业分类名称 */
	protected String categoryName;
	/* 任务要求数据项内容串 */
	protected String itemContents;
	/* 详细要求描述 */
	protected String requireDetail;
	/* 悬赏任务的投稿数量 */
	protected int touGaoNum;
	/* 悬赏到期时戳 */
	protected Integer expireTime;
	/* 悬赏金额(分)/大师套餐金额 */
	protected int price;
	/* 大师标识 */
	protected Integer dashiMemberId;
	/* 大师姓名 */
	protected String dashiMemberName;
	/* 老板名字 */
	protected String bossName;
	/* 起名字数 */
	protected String wordsNum;
	/* 生日 */
	protected Integer birthday;
	/* 出生时刻*/
	protected Integer birthhour;
	/* 支付方式，取值见ChangLiang中的定义 */
	protected short payMode;
	/* 付款时戳 */
	protected Integer paidTime;
	/* 发布人标识 */
	protected int creatorId;
	/* 发布人姓名 */
	protected String creatorName;
	/* 发布人头像 */
	protected String creatorPortrait;
	/* 发布时戳 */
	protected int createdTimestamp;
	/* 举报处理时戳 */
	protected Integer handledTime;
	/* 举报处理人标识 */
	protected Integer handlerId;
	/* 举报处理人姓名 */
	protected String handlerName;
	/* 悬赏任务是否已经分钱/已退款，取值见ChangLiang中的常量 */
	protected short isXuanShangFenQian;
	
	/*============= 冗余 ====================*/
	/* 投稿(人)集合*/
	private List<TaskTouGao> taskTouGaoList;
	
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public short getState() {
		return state;
	}
	public void setState(short state) {
		this.state = state;
	}
	public short getNamedCategory() {
		return namedCategory;
	}
	public void setNamedCategory(short namedCategory) {
		this.namedCategory = namedCategory;
	}
	public String getTaskTitle() {
		return taskTitle;
	}
	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
	public short getTaskMode() {
		return taskMode;
	}
	public void setTaskMode(short taskMode) {
		this.taskMode = taskMode;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getItemContents() {
		return itemContents;
	}
	public void setItemContents(String itemContents) {
		this.itemContents = itemContents;
	}
	public String getRequireDetail() {
		return requireDetail;
	}
	public void setRequireDetail(String requireDetail) {
		this.requireDetail = requireDetail;
	}
	public int getTouGaoNum() {
		return touGaoNum;
	}
	public void setTouGaoNum(int touGaoNum) {
		this.touGaoNum = touGaoNum;
	}
	public Integer getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getBossName() {
		return bossName;
	}
	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	public String getWordsNum() {
		return wordsNum;
	}
	public void setWordsNum(String wordsNum) {
		this.wordsNum = wordsNum;
	}
	public Integer getBirthday() {
		return birthday;
	}
	public void setBirthday(Integer birthday) {
		this.birthday = birthday;
	}
	public Integer getBirthhour() {
		return birthhour;
	}
	public void setBirthhour(Integer birthhour) {
		this.birthhour = birthhour;
	}
	public short getPayMode() {
		return payMode;
	}
	public void setPayMode(short payMode) {
		this.payMode = payMode;
	}
	public Integer getPaidTime() {
		return paidTime;
	}
	public void setPaidTime(Integer paidTime) {
		this.paidTime = paidTime;
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
	public List<TaskTouGao> getTaskTouGaoList() {
		return taskTouGaoList;
	}
	public void setTaskTouGaoList(List<TaskTouGao> taskTouGaoList) {
		this.taskTouGaoList = taskTouGaoList;
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
	public Integer getDashiMemberId() {
		return dashiMemberId;
	}
	public void setDashiMemberId(Integer dashiMemberId) {
		this.dashiMemberId = dashiMemberId;
	}
	public String getDashiMemberName() {
		return dashiMemberName;
	}
	public void setDashiMemberName(String dashiMemberName) {
		this.dashiMemberName = dashiMemberName;
	}
	public short getIsXuanShangFenQian() {
		return isXuanShangFenQian;
	}
	public void setIsXuanShangFenQian(short isXuanShangFenQian) {
		this.isXuanShangFenQian = isXuanShangFenQian;
	}
}
