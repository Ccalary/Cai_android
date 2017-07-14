package com.bc.caibiao.model;

/**
 * 订单状态历史实体类----根据对方返回的数据建立的实体类
 * @author zhaochunjiao
 * @create 2017年2月22日 下午2:51:44
 * @version 1.0
 */
public class OrderStateHistory {
	private String date_added;
	private String status;
	private String comment;
	
//	"date_added": "2016/10/18",
//	"status": "未支付",
//	"comment": ""
	
	public String getDate_added() {
		return date_added;
	}
	public void setDate_added(String date_added) {
		this.date_added = date_added;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
