package com.bc.caibiao.model;

import java.io.Serializable;
import java.util.List;

public class CityBean implements Serializable {
	protected int cityId;
	protected String cityName;
	
	/* 冗余 */
	protected List<LocationDistrictBean> locationDistricts;

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

	public List<LocationDistrictBean> getLocationDistricts() {
		return locationDistricts;
	}

	public void setLocationDistricts(List<LocationDistrictBean> locationDistricts) {
		this.locationDistricts = locationDistricts;
	}
}
