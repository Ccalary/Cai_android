package com.bc.caibiao.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangkai
 * @Description: 堆栈管理
 * create at 2015/10/30 16:40
 */
public class ActivityStack {
    public static ActivityStack instance = null;
    public static List<Activity> mActivities = null;
    public static Map<String, List<Activity>> mActivityMap = new HashMap<>();

    public ActivityStack() {
        super();
    }

    /**
     * 实例化
     *
     * @return
     */
    public static ActivityStack getInstance() {
        if (instance == null) {
            instance = new ActivityStack();
        }
        return instance;
    }


    /**
     * 将当前Activity推入栈中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivities == null) {
            mActivities = new ArrayList<Activity>();
        }
        mActivities.add(activity);
    }

    /**
     * 结束掉当前堆栈
     *
     * @param
     */
    public void finishActivities() {
        if (mActivities != null) {
            try {
                for (Activity mActivity : mActivities) {
                    if (mActivity != null)
                        mActivity.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 将activity添加到指定的分组中
     *
     * @param key
     * @param activity
     */
    public void addToMap(String key, Activity activity) {
        if (mActivityMap != null && !mActivityMap.isEmpty()) {
            if (mActivityMap.containsKey(key)) {
                List<Activity> activities = mActivityMap.get(key);
                if (activities == null) {
                    activities = new ArrayList<>();
                }
                activities.add(activity);
                mActivityMap.put(key, activities);
            } else {
                initActivityMap(key, activity);
            }
        } else {
            initActivityMap(key, activity);
        }
    }

    /**
     * 初始化堆栈分组
     *
     * @param key
     * @param activity
     */
    private void initActivityMap(String key, Activity activity) {
        mActivityMap = new HashMap<>();
        List<Activity> list = new ArrayList<>();
        list.add(activity);
        mActivityMap.put(key, list);
    }

    /**
     * 清除map中某个key所对应的activity
     *
     * @param key
     */
    public void finishActivityMap(String key) {
        if (mActivityMap != null && !mActivityMap.isEmpty()) {
            for (Map.Entry<String, List<Activity>> entry : mActivityMap.entrySet()
                    ) {
                if (entry.getKey().equals(key)) {
                    List<Activity> activities = entry.getValue();
                    try {
                        for (Activity mActivity : mActivities) {
                            if (mActivity != null) {
                                mActivity.finish();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 结束某个activity
     *
     * @param retainActivities
     */
    public void finishActivity(String retainActivities) {
        if (mActivities != null) {
            try {
                for (Activity mActivity : mActivities) {
                    if (mActivity != null) {
                        if (!mActivity.toString().contains(retainActivities)) {
                            mActivity.finish();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 退出当前应用
     */
    public void exitApplication() {
        if (mActivities != null) {
            for (Activity mActivity : mActivities) {
                if (mActivity != null)
                    mActivity.finish();
            }
        }
        System.exit(0);
    }
}
