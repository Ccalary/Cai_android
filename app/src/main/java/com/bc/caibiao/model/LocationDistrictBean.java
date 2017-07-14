package com.bc.caibiao.model;


import java.io.Serializable;

/**
 * 区
 * @author zhaochunjiao
 * @create 2015年11月19日 上午10:16:13
 * @version 1.0
 */
public class LocationDistrictBean implements Serializable {
	protected int locationDistrictId;
	protected String districtName;
	
	public int getLocationDistrictId() {
		return locationDistrictId;
	}
	public void setLocationDistrictId(int locationDistrictId) {
		this.locationDistrictId = locationDistrictId;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
}
