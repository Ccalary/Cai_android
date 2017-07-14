package com.bc.caibiao.model;

/**
 * 系统参数实体类
 * @author zcj
 * @create 2012-7-18 下午08:49:53
 * @version 1.0
 */
public class SystemOption {
	/* 会员订单：起送金币限额 */
	public static final String EN_MO_COIN_DONATE_QUOTA 		= "memberorder_coin_donate_quota";
	/* 会员订单：赠送商家金币数 */
	public static final String EN_MO_COIN_SHOP 				= "memberorder_coin_shop";
	/* 会员订单：赠送用户金币数 */
	public static final String EN_MO_COIN_MEMBER 			= "memberorder_coin_member";
	/* 会员获得金币上限 */
	public static final String EN_MEMBER_COIN_LIMIT_DAILY 	= "member_coin_limit_daily";
	/* 会员每日签到赠送金币数 */
	public static final String EN_MEMBER_DONATE_COIN_DAILY 	= "member_donate_coin_daily";
	/* 商家获得金币上限 */
	public static final String EN_SHOP_COIN_LIMIT_DAILY 	= "shop_coin_limit_daily";
	/* 商家每日签到获得金币数 */
	public static final String EN_SHOP_SIGN_DAILY	 		= "shop_sign_daily";
	/* 商家采购单：起送金币限额 */
	public static final String EN_SO_COIN_DONATE_QUOTA 		= "shoporder_coin_donate_quota";
	/* 商家采购单：赠送商家金币数 */
	public static final String EN_SO_COIN_SHOP 				= "shoporder_coin_shop";
	
	protected int systemOptionId;//系统参数标识
	protected String cnName;//中文名
	protected String enName;//英文标识
	protected String content;//内容
	protected long createdTimestamp;//创建时戳
	protected long creatorId;//创建人标识
	protected String creatorName;//创建人姓名
	protected long lastModified;//修改时戳
	protected long lastModifierId;//修改人标识
	protected String lastModifierName;//修改人姓名
	
	public int getSystemOptionId() {
		return systemOptionId;
	}
	public void setSystemOptionId(int systemOptionId) {
		this.systemOptionId = systemOptionId;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public long getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public long getLastModifierId() {
		return lastModifierId;
	}
	public void setLastModifierId(long lastModifierId) {
		this.lastModifierId = lastModifierId;
	}
	public String getLastModifierName() {
		return lastModifierName;
	}
	public void setLastModifierName(String lastModifierName) {
		this.lastModifierName = lastModifierName;
	}
	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
}
