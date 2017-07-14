package com.bc.caibiao.model;

/**
 * 订单商品实体类--根据对方返回的数据建立的实体类
 * @author zhaochunjiao
 * @create 2017年2月22日 下午2:46:08
 * @version 1.0
 */
public class OrderProduct {
	private String name;//商品名称
	private String price;//商品金额"￥0.01"
	private String total;//商品总价
	private String quantity;//商品购买数据
	private String distribute_amount;
	private String order_product_id;
	private String order_id;
	private String product_id;
	private String tax;//税率 默认0
	private String reward;//奖励积分
	private String model;
	private String thumb;
	
	
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getOrder_product_id() {
		return order_product_id;
	}
	public void setOrder_product_id(String order_product_id) {
		this.order_product_id = order_product_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDistribute_amount() {
		return distribute_amount;
	}
	public void setDistribute_amount(String distribute_amount) {
		this.distribute_amount = distribute_amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
