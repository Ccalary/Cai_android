package com.bc.caibiao.model;

import java.io.Serializable;

/**
 * 会员认证申请
 *
 * @author zhaochunjiao
 * @version 1.0
 * @create 2017年2月15日 下午2:27:25
 */
public class MemberAuthApply implements Serializable {
    /* 认证类型-投稿人认证 */
    public static final short AUTHTYPE_IDENTITY = 0;

    /* 认证类型-审核结果-审核中 */
    public static final short CHECKRESULT_APPROVING = 1;
    /* 认证类型-审核结果-通过 */
    public static final short CHECKRESULT_APPROVED = 2;
    /* 认证类型-审核结果-不通过 */
    public static final short CHECKRESULT_DISAPPROVED = 3;

    /* 申请标识 */
    protected int applyId;
    /* 会员标识 */
    protected int memberId;
    /* 认证类型，取值见常量 */
    protected short authType;
    /* 申请次数 */
    protected int applyTimes;
    /* 申请原件1(身份证图片) */
    protected String original1;
    /* 申请原件2，预留 */
    protected String original2;
    /* 申请原件3，预留 */
    protected String original3;
    /* 申请时戳 */
    protected int applyTime;
    /* 真实姓名 */
    protected String trueName;
    /* 身份证号码，预留 */
    protected String cardNumber;
    /* 审核结果，取值见常量 */
    protected Short checkResult;
    /* 审核人标识 */
    protected Integer checkerId;
    /* 审核人姓名 */
    protected String checkerName;
    /* 审核时戳 */
    protected Integer checkTime;
    /* 驳回原因/审核说明 */
    protected String reason;
    /* 擅长 */
    protected String attr1;
    /* 所在省标识 */
    protected int provinceId;
    /* 所在省名 */
    protected String provinceName;
    /* 所在市标识 */
    protected int cityId;
    /* 所在市名 */
    protected String cityName;
    /* 申请人姓名 */
    protected String memberName;


    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public short getAuthType() {
        return authType;
    }

    public void setAuthType(short authType) {
        this.authType = authType;
    }

    public int getApplyTimes() {
        return applyTimes;
    }

    public void setApplyTimes(int applyTimes) {
        this.applyTimes = applyTimes;
    }

    public String getOriginal1() {
        return original1;
    }

    public void setOriginal1(String original1) {
        this.original1 = original1;
    }

    public String getOriginal2() {
        return original2;
    }

    public void setOriginal2(String original2) {
        this.original2 = original2;
    }

    public String getOriginal3() {
        return original3;
    }

    public void setOriginal3(String original3) {
        this.original3 = original3;
    }

    public int getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(int applyTime) {
        this.applyTime = applyTime;
    }

    public Short getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Short checkResult) {
        this.checkResult = checkResult;
    }

    public Integer getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(Integer checkerId) {
        this.checkerId = checkerId;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public Integer getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Integer checkTime) {
        this.checkTime = checkTime;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
