package com.bc.caibiao.request.subscribe;

import android.content.Context;

import com.bc.caibiao.model.FieldError;


/**
 * @author wangkai
 * @Description :
 * create at 16/8/15 下午5:51
 */
public abstract class ResolveFailSubscribe<T> extends ProgressSubscribe<T> {
    public ResolveFailSubscribe(Context mContext) {
        super(mContext);
    }

    public ResolveFailSubscribe(Context mContext, String defaultMsg) {
        super(mContext, defaultMsg);
    }

    public ResolveFailSubscribe(Context mContext, boolean isShow) {
        super(mContext, isShow);
    }

    @Override
    public void onNext(T t) {
        if (t instanceof FieldError) {
            _onFail((FieldError) t);
            return;
        }
        super.onNext(t);
    }

    protected abstract void _onFail(FieldError fieldError);
}
