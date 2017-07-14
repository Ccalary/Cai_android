package com.bc.caibiao.model;

import com.google.gson.Gson;

/**
 * 会员实体类--为了将才标客户方的customer映射过来
 *
 * @author zhaochunjiao
 * @version 1.0
 * @create 2017年2月15日 下午2:31:33
 */
public class Member {
    /* 状态-正常 */
    public static final short STATE_NORMAL = 1;
    /* 状态-禁用*/
    public static final short STATE_DISABLED = 0;

    /* 会员标识 */
    private int memberId;
    /* 状态 */
    private short state;
    /* 姓名 */
    private String memberName;
    /* 手机号码 */
    protected String mobilePhone;
    /* 头像 */
    protected String portrait;
    /*是否大师，取值见ChangLiang中的常量 */
    protected short isDaShi;
    /*是否投稿人，取值见ChangLiang中的常量 */
    protected short isTouGaoRen;

    /*bc_member_dashi_extend 大师扩展信息*/
    /* 起名特点 */
    protected String teDian;
    /* 大师简介 */
    protected String introduce;

    /* 冗余--投稿人认证对象 */
    protected MemberAuthApply memberAuthApply;

    /*是否需要绑定手机号*/
    private int isBandPhone;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPortrait() {
        if(portrait==null){
            portrait="";
        }
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public short getIsDaShi() {
        return isDaShi;
    }

    public void setIsDaShi(short isDaShi) {
        this.isDaShi = isDaShi;
    }

    public short getIsTouGaoRen() {
        return isTouGaoRen;
    }

    public void setIsTouGaoRen(short isTouGaoRen) {
        this.isTouGaoRen = isTouGaoRen;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public MemberAuthApply getMemberAuthApply() {
        return memberAuthApply;
    }

    public void setMemberAuthApply(MemberAuthApply memberAuthApply) {
        this.memberAuthApply = memberAuthApply;
    }

    public String getTeDian() {
        return teDian;
    }

    public void setTeDian(String teDian) {
        this.teDian = teDian;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getIsBandPhone() {
        return isBandPhone;
    }

    public void setIsBandPhone(int isBandPhone) {
        this.isBandPhone = isBandPhone;
    }
}
