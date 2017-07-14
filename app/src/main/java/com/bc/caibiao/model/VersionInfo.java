package com.bc.caibiao.model;

/**
 * 客户端版本信息实体类
 *
 * @author dingwei
 * @version 1.0
 * @create 2012-11-30 下午08:49:53
 */
public class VersionInfo {
    protected int versionInfoId;   //客户端版本信息标识
    protected String apkVersion;   //最新版本号
    protected String downLoadPath;  //客户端下载地址
    protected String versionDesc;   //版本描述
    protected short isForce;        //是否强制更新；
    protected String updateTips;    //更新提示信息
    protected String remark;//说明备注

    public int getVersionInfoId() {
        return versionInfoId;
    }

    public void setVersionInfoId(int versionInfoId) {
        this.versionInfoId = versionInfoId;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getDownLoadPath() {
        return downLoadPath;
    }

    public void setDownLoadPath(String downLoadPath) {
        this.downLoadPath = downLoadPath;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public short getIsForce() {
        return isForce;
    }

    public void setIsForce(short isForce) {
        this.isForce = isForce;
    }

    public String getUpdateTips() {
        return updateTips;
    }

    public void setUpdateTips(String updateTips) {
        this.updateTips = updateTips;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
