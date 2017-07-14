package com.bc.caibiao.model;

/**
 * 商标收藏实体类--根据对方返回的商标列表数据建立的实体类
 * @author zhaochunjiao
 * @create 2017年2月22日 上午11:14:04
 * @version 1.0
 */
public class TrademarkFollow {
	private String follow_id;//收藏id
	private String trademark_name;//商标名称
	private String trademark_intcls;
	private String trademark_image;
	private String trademark_tmlaw;
	private String trademark_tmname;
	private String trademark_regno;
	public String getFollow_id() {
		return follow_id;
	}
	public void setFollow_id(String follow_id) {
		this.follow_id = follow_id;
	}
	public String getTrademark_name() {
		return trademark_name;
	}
	public void setTrademark_name(String trademark_name) {
		this.trademark_name = trademark_name;
	}
	public String getTrademark_intcls() {
		return trademark_intcls;
	}
	public void setTrademark_intcls(String trademark_intcls) {
		this.trademark_intcls = trademark_intcls;
	}
	public String getTrademark_image() {
		return trademark_image;
	}
	public void setTrademark_image(String trademark_image) {
		this.trademark_image = trademark_image;
	}
	public String getTrademark_tmlaw() {
		return trademark_tmlaw;
	}
	public void setTrademark_tmlaw(String trademark_tmlaw) {
		this.trademark_tmlaw = trademark_tmlaw;
	}
	public String getTrademark_tmname() {
		return trademark_tmname;
	}
	public void setTrademark_tmname(String trademark_tmname) {
		this.trademark_tmname = trademark_tmname;
	}
	public String getTrademark_regno() {
		return trademark_regno;
	}
	public void setTrademark_regno(String trademark_regno) {
		this.trademark_regno = trademark_regno;
	}
	
	
}
