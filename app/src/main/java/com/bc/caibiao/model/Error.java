package com.bc.caibiao.model;

/**
 * api 出错返回实体类
 * @author hehaiyang
 *
 */
public class Error {
	public static final String SUCCESS_CODE = "0";//无错误，成功
	public static final String ERROR_PARTNER_CODE = "-1";//参数partner错误
	public static final String ERROR_INPUTCHARSET_CODE = "-2";//字符编码错误
	public static final String ERROR_SIGN_CODE = "-3";//sign错误
	public static final String ERROR_UNKONWN = "-4";//未知类型/未知交易模块
	public static final String ERROR_OPERATION_NOTALLOWED = "-5"; //权限错误
	public static final String ERROR_NO_DATA = "-6"; //无数据
	public static final String ERROR_CALLING_FAIL = "-7"; //请求第三方接口失败
	public static final String ERROR_FILE_TOO_LARGE = "-8"; //文件过大
	public static final String ERROR_EXCEPTION_CODE = "-99";//服务器处理异常
	public static final String ERROR_CAIBIAO_API_FAIL = "-100";//才标API请求失败
	
	/* ------ 会员模块错误 -----------------------------*/
	public static final String ERROR_MEMBER_SENDSMSFAIL = "-11";  //发送短信失败
	public static final String ERROR_MEMBER_LOGINFAIL = "-12";  //登录失败
	public static final String ERROR_MEMBER_OLDPWDERROR = "-13";  //旧密码错误
	public static final String ERROR_TELEPHONE_EXIST = "-14";//手机号已存在
	public static final String ERROR_TELEPHONE_NOT_EXIST = "-15";//手机号不存在
	public static final String ERROR_MEMBER_LOGIN_INVALID = "-16";//账号已禁用
	public static final String ERROR_IDNUMBER_EXIST = "-17";//新手机号与旧手机号一样了
	public static final String ERROR_MEMBER_NO_TOUGAOREN = "-18";//不是投稿人
	
	
	/* ------ 账户模块错误 -----------------------------*/
	public static final String ERROR_ACCOUNT_ACCOUNTPWDERROR = "-41"; // 账户密码错误
	public static final String ERROR_ACCOUNT_BALANCENOTENOUGH = "-42"; // 账户余额不足
	public static final String ERROR_ACCOUNT_INTEGRAL_NOTENOUGH = "-43"; // 积分余额不足
	public static final String ERROR_ACCOUNT_RED_PWD_WRONG = "-44"; // 红包口令不正确
	public static final String ERROR_ACCOUNT_RED_ENVELOPE_OVER = "-45"; //红包已抢光
	public static final String ERROR_ACCOUNT_RED_ENVELOPE_EXPIRE = "-46"; //红包已过期
	public static final String ERROR_ACCOUNT_RED_ENVELOPE_MIN = "-47"; //红包牛币数小于最小数量
	public static final String ERROR_ACCOUNT_RED_ENVELOPE_MAX = "-48"; //红包牛币数大于最大数量
	public static final String ERROR_ACCOUNT_INTEGRAL_BALANCENOTENOUGH = "-49"; //积分余额不足
	public static final String ERROR_ACCOUNT_PAYMETHOD_UNKNOWN = "-411"; //未知支付方式
	public static final String ERROR_ACCOUNT_ALREADY_PAID= "-413"; //订单已支付过
	public static final String ERROR_ACCOUNT_WXPAY_FAIL= "-414"; //微信支付失败
	public static final String ERROR_ACCOUNT_NOT_EXIST= "-415"; //账户不存在
	public static final String ERROR_ACCOUNT_ALIPAY_FAIL= "-417"; //支付宝支付失败
	public static final String ERROR_ACCOUNT_PAIDAMOUNT_ERROR = "-418"; // 付款金额不正确
	public static final String ERROR_ACCOUNT_WITHDRAWCASHAPPLY_OVERLIMIT = "-419"; // 提现超出限额
	
	
	/* ------ 收藏错误 -----------------------------*/
	public static final String ERROR_COLLECT_COLLECTED = "-61";//该会员已收藏过
	
	/* ------ message错误 -----------------------------*/
	public static final String ERROR_MESSAGE_DEVICEID_NULL = "-81";//设备号是空或空字符串
	
	/* ------ impeach错误 -----------------------------*/
	public static final String ERROR_IMPEACH_IMPEACHED = "-91";//该会员已举报过此主题

		
	private int errorId; //错误代码
	private String url;//服务器接收的url参数
	private String resultCode;//第三方返回的错误代码
	
	public int getErrorId() {
		return errorId;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
}
