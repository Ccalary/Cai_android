package com.bc.caibiao.model;

import java.util.List;


/**
 * 消息 实体类.
 * @author DongXuemei
 * @create 2015年3月4日 下午2:46:58
 * @version 1.0
 */
public class Message {
	public static final short RECEIVERTYPE_MEMBER = 1;
	
	/* 状态-未推送 */
	public static final short STATE_NEW = 1;
	/* 状态-已推送 */
	public static final short STATE_SENT = 2;
	/* 状态-删除 */
	public static final short STATE_DELETED = 9;
	
	
	/* 消息所属分组-任务消息 */
	public static final short GROUP_TYPE_TASK = 1;
	/* 消息所属分组-后台推送的消息 */
	public static final short GROUP_TYPE_DISCOUNT = 2;
	/* 消息所属分组-系统消息 */
	public static final short GROUP_TYPE_SYSTEM = 3;
	/* 消息所属分组-商标复查消息 */
	public static final short GROUP_TYPE_BRAND_RECHECK = 4;
	/* 消息所属分组-暂时无用 */
	public static final short GROUP_TYPE_CMD = 99;
	
	/* 消息指向实体-悬赏任务*/
	public static final short LINK_TYPE_XUANSHAGN_TASK = 1;
	/* 消息指向实体-大师任务*/
	public static final short LINK_TYPE_DASHI_TASK = 2;
	/* 消息指向实体-商标复查*/
	public static final short LINK_TYPE_BRAND_RECHECK = 3;
	/* 消息指向实体-外网链接*/
	public static final short LINK_TYPE_HTTP = 11;
	/* 消息指向实体模块-无指向*/
	public static final short LINK_TYPE_NONE = 99;
	
	/* 内容模板-任务消息--参与悬赏任务(投稿了) */
	public static final String CONTENTTEMPLATE_XUANSHAGN_TASK_JOIN = "{memberName}参与了你的悬赏任务";
	/* 内容模板-商标复查消息--商标复查完成 */
	public static final String CONTENTTEMPLATE_BRAND_RECHECK_OK = "{brandName}商标复查完成";
	/* 内容模板-提现成功 */
	public static final String CONTENTTEMPLATE_WITHDRAWCASH_SUCCESS = "您本次申请提现{applyAmount/100}元已转入您的支付宝账户，请注意查收";
	/* 内容模板-提现失败 */
	public static final String CONTENTTEMPLATE_WITHDRAWCASH_FAILED = "你提交的{applyAmount/100}元提现申请失败。";
	
	
	/* 业务类型-会员-提现失败 */
	public static final short BUSINESSTYPE_MEMBER_WITHDRAWCASH_FAILED = 6;
	
	/* 消息标识 */
	protected int messageId;
	/* 所属分组，取值见常量 */
	protected short groupType;
	/* 状态，取值见常量 */
	protected short state;
	/* 消息指向实体模块，取值见常量 */
	protected short linkType;
	/* 消息指向实体参数 */
	protected String linkParams;
	/* 消息指向实体参数的名称 */
	protected String linkParamsName;
	/* 消息标题(预留) */
	protected String messageTitle;
	/* 消息内容，内容参照模板，替换参数 */
	protected String messageContent;
	/* 创建时戳 */
	protected int createdTimestamp;
	/* 创建人标识 */
	protected Integer creatorId;
	/* 创建人姓名 */
	protected String creatorName;
	/* 推送时戳 */
	protected Integer sentTimestamp;
	/* 删除时戳 */
	protected Integer deletedTime;
	
	/*========== 冗余 ===========*/
	/*多个消息接收者标识 */
	protected List<Integer> receverIds;
	/*linkType的说明文字 */
	protected String linkTypeText;
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public short getGroupType() {
		return groupType;
	}
	public void setGroupType(short groupType) {
		this.groupType = groupType;
	}
	public short getState() {
		return state;
	}
	public void setState(short state) {
		this.state = state;
	}
	public short getLinkType() {
		return linkType;
	}
	public void setLinkType(short linkType) {
		this.linkType = linkType;
	}
	public String getLinkParams() {
		return linkParams;
	}
	public void setLinkParams(String linkParams) {
		this.linkParams = linkParams;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public int getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(int createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public Integer getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public Integer getSentTimestamp() {
		return sentTimestamp;
	}
	public void setSentTimestamp(Integer sentTimestamp) {
		this.sentTimestamp = sentTimestamp;
	}
	public Integer getDeletedTime() {
		return deletedTime;
	}
	public void setDeletedTime(Integer deletedTime) {
		this.deletedTime = deletedTime;
	}
	public List<Integer> getReceverIds() {
		return receverIds;
	}
	public void setReceverIds(List<Integer> receverIds) {
		this.receverIds = receverIds;
	}
	public String getLinkParamsName() {
		return linkParamsName;
	}
	public void setLinkParamsName(String linkParamsName) {
		this.linkParamsName = linkParamsName;
	}
	public String getLinkTypeText() {
		return linkTypeText;
	}
	public void setLinkTypeText(String linkTypeText) {
		this.linkTypeText = linkTypeText;
	}
}
