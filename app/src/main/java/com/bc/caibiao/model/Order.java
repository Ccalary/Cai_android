package com.bc.caibiao.model;

import java.util.List;

/**
 * 订单实体类--根据对方返回的数据建立的实体类
 * @author zhaochunjiao
 * @create 2017年2月22日 下午2:42:04
 * @version 1.0
 */
public class Order {
	private String invoice_no;//发票
	private int order_id;//订单id
	private String order_number;//订单编号，显示用的
	private String name;//购买人姓名
	private String date_added;//添加日期
	private String payment_customer_name;//订单id
	private String payment_customer_telephone;//订单id
	private String payment_company;//订单id
	private String payment_method;//付款方式
	private String payment_country;//付款方式
	private String comment;//订单留言
	private String status;//订单状态
	private String order_status;//也是订单状态
	private String currency_code;//货币单位
	private String currency_value;//货币数值
	private String total;//总价
	private String payment_postcode;
	private String payment_city;
	private String payment_address;
	private List<OrderProduct> products;//订单商品集合
	private List<OrderTotals> totals;//订单费用清单集合
	private List<OrderStateHistory> histories;//订单状态列表集合
	
	
	public String getPayment_country() {
		return payment_country;
	}
	public void setPayment_country(String payment_country) {
		this.payment_country = payment_country;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getPayment_postcode() {
		return payment_postcode;
	}
	public void setPayment_postcode(String payment_postcode) {
		this.payment_postcode = payment_postcode;
	}
	public String getPayment_city() {
		return payment_city;
	}
	public void setPayment_city(String payment_city) {
		this.payment_city = payment_city;
	}
	public String getPayment_address() {
		return payment_address;
	}
	public void setPayment_address(String payment_address) {
		this.payment_address = payment_address;
	}
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	public String getCurrency_value() {
		return currency_value;
	}
	public void setCurrency_value(String currency_value) {
		this.currency_value = currency_value;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getDate_added() {
		return date_added;
	}
	public void setDate_added(String date_added) {
		this.date_added = date_added;
	}
	public String getPayment_customer_name() {
		return payment_customer_name;
	}
	public void setPayment_customer_name(String payment_customer_name) {
		this.payment_customer_name = payment_customer_name;
	}
	public String getPayment_customer_telephone() {
		return payment_customer_telephone;
	}
	public void setPayment_customer_telephone(String payment_customer_telephone) {
		this.payment_customer_telephone = payment_customer_telephone;
	}
	public String getPayment_company() {
		return payment_company;
	}
	public void setPayment_company(String payment_company) {
		this.payment_company = payment_company;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<OrderProduct> getProducts() {
		return products;
	}
	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}
	public List<OrderTotals> getTotals() {
		return totals;
	}
	public void setTotals(List<OrderTotals> totals) {
		this.totals = totals;
	}
	public List<OrderStateHistory> getHistories() {
		return histories;
	}
	public void setHistories(List<OrderStateHistory> histories) {
		this.histories = histories;
	}
}
