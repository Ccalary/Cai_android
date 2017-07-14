package com.bc.caibiao.utils;

import java.math.BigDecimal;

/**
 * 日淘专用的钱币格式化使用类
 */
public class RiTaoMoneyFormatter {

    public static String format(double d) {
        BigDecimal b = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
        return subZeroAndDot(String.valueOf(b.doubleValue()));
    }

    public static String format(String s) {
        double d = Double.parseDouble(s);
        BigDecimal b = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
        return subZeroAndDot(String.valueOf(b.doubleValue()));
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    private static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

}
