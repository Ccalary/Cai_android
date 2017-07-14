package com.bc.caibiao.ui;

import android.content.Context;

import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.utils.SP;
import com.google.gson.Gson;

/**
 * @author wangkai
 * @create 16/9/6 上午9:37
 * @Description :
 */
public class BasePresenter<T> {
    public Context mContext;

    public BasePresenter(Context context) {
        this.mContext = context;
    }

    public T mView;

    public void bindView(T view) {
        this.mView = view;
    }

    public void destroy() {
        mView = null;
    }

    public Member getMember() {
        try {
            return SP.getInstance().getMemberSP();
        } catch (Exception e) {
            return null;
        }
    }

    public int getMemberId() {
        try {
            return SP.getInstance().getMemberSP().getMemberId();
        } catch (Exception e) {
            return -1;
        }
    }
}
