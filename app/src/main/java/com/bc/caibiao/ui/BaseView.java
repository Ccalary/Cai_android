package com.bc.caibiao.ui;

/**
 * @author wangkai
 * @create 16/9/6 上午9:47
 * @Description :
 */
public interface BaseView {
    void showLoading(String tipMsg);

    void dismissLoading();

    void showToast(String tipMsg);

    void delayFinish(int time);

}
