package com.bc.caibiao.model;

/**
 * 订单费用清单实体类--根据对方返回的数据建立的实体类
 * @author zhaochunjiao
 * @create 2017年2月22日 下午2:49:35
 * @version 1.0
 */
public class OrderTotals {
	private String title;
	private String text;
	
//	"title": "商品总额",
//	"text": "￥0.01"
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
