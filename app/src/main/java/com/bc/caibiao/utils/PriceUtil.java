package com.bc.caibiao.utils;

import java.math.BigDecimal;

/**
 * @author wangkai
 * @Description: 价格计算工具类
 * create at 2015/11/30 13:33
 */
public class PriceUtil {

    /**
     * 元 转 分
     */
    public static int YtoF(String price) {
        try {
            return new BigDecimal(price).multiply(new BigDecimal(100)).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public static int YtoF(int price) {
        try {
            return new BigDecimal(price).multiply(new BigDecimal(100)).intValue();
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 分 转 元
     *
     * @param price
     * @return
     */
    public static double FtoY(String price) {
        try {
            BigDecimal b1 = new BigDecimal(price);
            BigDecimal b2 = new BigDecimal(Double.toString(0.01));
            return b1.multiply(b2).doubleValue();
        } catch (Exception e) {
            return 0.00;
        }
    }

    public static String FtoY(int price) {
        try {
            BigDecimal b1 = new BigDecimal(price);
            BigDecimal b2 = new BigDecimal(Double.toString(0.01));
            return String.valueOf(b1.multiply(b2).doubleValue());
        } catch (Exception e) {
            return "0.00";
        }
    }


}
