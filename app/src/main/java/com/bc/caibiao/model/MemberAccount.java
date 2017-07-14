package com.bc.caibiao.model;

import java.util.ArrayList;
import java.util.List;



/**
 * 会员账户
 * @author zhaochunjiao
 * @create 2015年11月2日 下午2:11:19
 * @version 1.0
 */
public class MemberAccount {
	public static final String PWD_ERROR_TIP = "您的支付密码已经输错5次，今天将不能使用支付密码功能。";
	public static final String PWD_ERROR_TIP2 = "支付密码错误，您还有{remainNum}次输入机会。";
	
	/* 状态-正常 */
	public static final short STATE_NORMAL = 1;
	/* 状态-密码输错禁用 */
	public static final short STATE_PWD_DISABLED = 10;
	
	/* 会员标识 */
	protected int memberId;
	/* 账户牛币数余额(分) */
	protected int accountBalance;
	/* 账户密码，MD5加密，初始为登录密码 */
	protected String accountPwd;
	/* 创建时戳 */
	protected int createdTime;
	/* 最后变动时戳 */
	protected int lastModified;
	/* 错误密码次数，预留 */
	protected int failNum;
	/* 账户状态 */
	protected short state;
	
	/* 冗余 */
	protected Double accountBalanceYuan;
	
	
	/**
	 * 支付密码错误原因
	 * @param memberAccount
	 * @param password
	 * @return
	 * @throws Exception
	 * @author zhaochunjiao
	 * @create 2016年4月12日 下午4:42:28
	 */
	public static JsonResultBean authMemberAccountPwd(MemberAccount memberAccount, String password) throws Exception{
		JsonResultBean result = new JsonResultBean();
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		if(memberAccount == null){
			result.setState(JsonResultBean.JSONRESULTBEAN_STATE_ERROR);
			FieldError fieldError = new FieldError();
          	fieldError.setMessage("账户不存在");
          	fieldError.setField(Error.ERROR_ACCOUNT_NOT_EXIST);
          	fieldErrors.add(fieldError);
          	result.setFieldErrors(fieldErrors);
          	return result;
		}else{
			result.setState(JsonResultBean.JSONRESULTBEAN_STATE_ERROR);
			FieldError fieldError = new FieldError();
          	fieldError.setMessage("未知错误。");
          	fieldError.setField(Error.ERROR_ACCOUNT_ACCOUNTPWDERROR);
          	fieldErrors.add(fieldError);
          	result.setFieldErrors(fieldErrors);
			return result;
		}
	}
	
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getAccountPwd() {
		return accountPwd;
	}
	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}
	public int getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(int createdTime) {
		this.createdTime = createdTime;
	}
	public int getLastModified() {
		return lastModified;
	}
	public void setLastModified(int lastModified) {
		this.lastModified = lastModified;
	}
	public int getFailNum() {
		return failNum;
	}
	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}
	public short getState() {
		return state;
	}
	public void setState(short state) {
		this.state = state;
	}
	public Double getAccountBalanceYuan() {
		return accountBalanceYuan;
	}
	public void setAccountBalanceYuan(Double accountBalanceYuan) {
		this.accountBalanceYuan = accountBalanceYuan;
	}
}
