package com.bc.caibiao.utils;

import android.text.TextUtils;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangkai
 * @Description: String工具类
 * create at 16/4/25 下午6:00
 */
public class StringUtil {

    private StringUtil() {
        throw new AssertionError();
    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length())
                .append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    public static String htmlEscapeCharsToString(String source) {
        return StringUtil.isEmpty(source) ? source : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 是否是合法的手机号 *
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        if (isEmpty(mobiles))
            return false;

        Pattern p = Pattern.compile("^(13|15|17|18|14)\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 获取Textview或者Edittext中的文字
     *
     * @param tv
     * @return
     */
    public static String getTextViewString(TextView tv) {
        try {
            if (tv != null) {
                if (!isEmpty(tv.getText().toString().trim())) {
                    return tv.getText().toString().trim();
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;

    }

    /** 获取标准两位小数  Float*/
    public static String getFormatedFloatString(String originalString) {
        try {
            float amount = Float.parseFloat(originalString);
            DecimalFormat df = new DecimalFormat("##0.00");
            return df.format(amount);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "0.00";
    }


    public static ArrayList<String> getTedianList(String tagStr){

        ArrayList<String> tedians = new ArrayList<>();

        if(TextUtils.isEmpty(tagStr)){
            return tedians;
        }

        String[] tedian = tagStr.split("#");

        for(String tag : tedian){
            tedians.add(tag);
        }

        return tedians;
    }
}
