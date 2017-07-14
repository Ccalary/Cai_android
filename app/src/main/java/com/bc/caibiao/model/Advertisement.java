package com.bc.caibiao.model;

import java.io.Serializable;

/**
 * 广告 实体类
 *
 * @author 董雪梅
 * @version 1.0
 * @create 2014-3-6  上午10:05:06
 */
public class Advertisement implements Serializable {
    /* 广告所在位置-APP-首页 */
    public static final int POSITION_APP_HOME = 1;
    /* 广告所在位置-APP-商品分类 */
    public static final int POSITION_APP_PRODUCT_CATEGORY = 2;


    /* 状态-正常 */
    public static final short STATE_NORMAL = 1;
    /* 状态-删除 */
    public static final short STATE_DELETED = 9;

    /* 广告标识 */
    protected int advertisementId;
    /* 状态，取值见常量 */
    protected short state;
    /* 城市标志 */
    protected Integer cityId;
    /* 广告所在位置，取值见常量 */
    protected int adPosition;
    /* 广告所在分类标识，商城的广告是放在每个顶级产品分类中的 */
    protected Integer adCatId;
    /* 广告指向模块，取值见常量，就是entityType，参考EntityType实体类中的模块常量 */
    protected short adType;
    /* 广告指向实体标识，与广告指向模块相关。例如，如广告指向模块为课程，则 refEntityId则存储courseId */
    protected Integer refEntityId;
    /* 广告名称 */
    protected String adName;
    /* 广告图片 */
    protected String adImg;
    /* 广告指向链接或实体名称 , 与广告指向模块相关。例如，如广告指向模块为课程，则 adLink存储courseName*/
    protected String adLink;
    /* 序号 */
    protected int orderNo;
    /* 开始时间 */
    protected int beginTimestamp;
    /* 结束时间 */
    protected int endTimestamp;
    /* 创建时戳 */
    protected int createdTimestamp;
    /* 创建人标识 */
    protected int creatorId;
    /* 创建人姓名 */
    protected String creatorName;
    /* 最后修改时戳 */
    protected int lastModified;
    /* 最后修改人标识 */
    protected int lastModifierId;
    /* 最后修改人姓名 */
    protected String lastModifierName;

    //冗余
    /* 分站名称 */
    protected String cityName;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public int getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(int advertisementId) {
        this.advertisementId = advertisementId;
    }

    public int getAdPosition() {
        return adPosition;
    }

    public void setAdPosition(int adPosition) {
        this.adPosition = adPosition;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public short getAdType() {
        return adType;
    }

    public void setAdType(short adType) {
        this.adType = adType;
    }

    public Integer getRefEntityId() {
        return refEntityId;
    }

    public void setRefEntityId(Integer refEntityId) {
        this.refEntityId = refEntityId;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdImg() {
        return adImg;
    }

    public void setAdImg(String adImg) {
        this.adImg = adImg;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getBeginTimestamp() {
        return beginTimestamp;
    }

    public void setBeginTimestamp(int beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    public int getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(int endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public int getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(int createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
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

    public int getLastModified() {
        return lastModified;
    }

    public void setLastModified(int lastModified) {
        this.lastModified = lastModified;
    }

    public int getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(int lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    public String getLastModifierName() {
        return lastModifierName;
    }

    public void setLastModifierName(String lastModifierName) {
        this.lastModifierName = lastModifierName;
    }

    public String getAdLink() {
        return adLink;
    }

    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getAdCatId() {
        return adCatId;
    }

    public void setAdCatId(Integer adCatId) {
        this.adCatId = adCatId;
    }
}
