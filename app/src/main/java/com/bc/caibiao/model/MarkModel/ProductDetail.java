package com.bc.caibiao.model.MarkModel;

/**
 * Created by chengyanfang on 2017/4/21.
 * "thumb" : "http:\/\/www.58caibiao.com\/image\/cache\/catalog\/caibiao_mall\/product_picture\/sb-fs-450x450.png",
 "rating" : "0",
 "points" : "0",
 "stock" : "有现货",
 "special" : "false",
 "price" : "2500.0000",
 "reward" : "0",
 "product_id" : "61",
 "product_quantity" : "9999",
 "price_format" : "￥2,500.00",
 "minimum" : "1",


 "popup" : "http:\/\/www.58caibiao.com\/image\/cache\/catalog\/caibiao_mall\/product_picture\/sb-fs-500x500.png",
 "model" : "商标驳回复审",
 "name" : "商标驳回复审",

 */

public class ProductDetail {

    public String description;
    public String thumb;

    public String rating;
    public String points;
    public String stock;
    public String special;
    public String price;

    public String reward;
    public String product_id;
    public String product_quantity;
    public String price_format;
    public String minimum;

    public String popup;
    public String model;
    public String name;

    public int getPriceFen(){
        float priceFen = 0;
        try{
            priceFen = Float.parseFloat(price)*100;
        }catch (Exception e){

        }
        return (int)priceFen;
    }
}
