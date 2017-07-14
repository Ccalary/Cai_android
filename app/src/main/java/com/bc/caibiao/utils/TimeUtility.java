package com.bc.caibiao.utils;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;

import java.util.Calendar;

/**
 * Created by chengyanfang on 2017/4/17.
 */

public class TimeUtility {

    private static int MILL_MIN = 1000 * 60;
    private static int MILL_HOUR = MILL_MIN * 60;
    private static int MILL_DAY = MILL_HOUR * 24;

    private static String MIN = BaseApplication.getInstance().getString(R.string.min);
    private static String HOUR = BaseApplication.getInstance().getString(R.string.hour);
    private static String DAY = BaseApplication.getInstance().getString(R.string.day_time);
    private static String MONTH = BaseApplication.getInstance().getString(R.string.month);
    private static String YEAR = BaseApplication.getInstance().getString(R.string.year);

    private static Calendar msgCalendar = null;
    private static java.text.SimpleDateFormat dayFormat = null;
    private static java.text.SimpleDateFormat dateFormat = null;
    private static java.text.SimpleDateFormat yearFormat = null;


    private TimeUtility() {

    }

    public static String getListTime(long time) {
        long now = System.currentTimeMillis()/1000;
        long msg = time;

        Calendar nowCalendar = Calendar.getInstance();

        if (msgCalendar == null)
            msgCalendar = Calendar.getInstance();

        msgCalendar.setTimeInMillis(time);

        long calcMills = msg - now;

        if(calcMills<0){
            return "已经";
        }

        if (calcMills < 60) {
            return calcMills+"秒后";
        }

        long calMins = calcMills / 60;
        if (calMins < 60) {
            return new StringBuilder().append(calMins).append(MIN).toString();
        }

        long calHours = calMins / 60;
        if (calHours < 24) {
            return new StringBuilder().append(calHours).append(HOUR).toString();
        }


        long calDay = calHours / 24;
        if (calDay < 31) {
            return new StringBuilder().append(calDay).append(DAY).toString();
        }

        long calMonth = calDay / 31;
        if (calMonth < 12 && isSameYear(nowCalendar, msgCalendar)) {
            return new StringBuilder().append(calMonth).append(MONTH).toString();
        }

        long calYear = calMonth / 12;
        return new StringBuilder().append(calYear).append(YEAR).toString();
    }

    private static boolean isSameDay(Calendar now, Calendar msg) {
        int nowDay = now.get(Calendar.DAY_OF_YEAR);
        int msgDay = msg.get(Calendar.DAY_OF_YEAR);

        return nowDay == msgDay;
    }

    private static boolean isSameYear(Calendar now, Calendar msg) {
        int nowYear = now.get(Calendar.YEAR);
        int msgYear = msg.get(Calendar.YEAR);

        return nowYear == msgYear;
    }


}
