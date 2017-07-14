package com.bc.caibiao.model;

import java.io.Serializable;
import java.util.List;

public class ProvinceBean implements Serializable {
	protected int provinceId;
	protected String provinceName;
	
	/*冗余*/
	protected List<CityBean> cities;

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

	public List<CityBean> getCities() {
		return cities;
	}

	public void setCities(List<CityBean> cities) {
		this.cities = cities;
	}
}
