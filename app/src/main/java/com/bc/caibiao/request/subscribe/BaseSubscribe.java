package com.bc.caibiao.request.subscribe;

import android.content.Context;

import com.bc.caibiao.request.ServerException;
import com.bc.caibiao.utils.BCL;
import com.bc.caibiao.utils.NetUtil;
import com.bc.caibiao.utils.ToastUtils;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * @author wangkai
 * @Description : 最普通观察者，只处理请求成功
 * create at 16/8/15 下午4:53
 */
public abstract class BaseSubscribe<T> extends Subscriber<T> {
    protected Context mContext;

    public BaseSubscribe(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        String sOut = "";
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        BCL.e("==========Exception Start==========\r\n" + sOut + "\r\n==========Exception End==========");
        String msg = "服务器未知异常";
        if (!NetUtil.checkNet(mContext)) { //这里自行替换判断网络的代码
            msg = "网络不可用";
        } else if (e instanceof ServerException) {
            msg = (e.getMessage());
        } else if (e instanceof SocketTimeoutException) {
            msg = "请求超时,无法连接服务器";
        } else if (e instanceof HttpException) {
            msg = "网络请求异常\n";
            int errCode = ((HttpException) e).code();
            msg += "异常返回码:" + errCode + "\n";
            msg += "异常原因:";
            switch (errCode) {
                case 400:
                    msg += "错误请求";
                    break;
                case 403:
                    msg += "服务器拒绝请求";
                    break;
                case 404:
                    msg += "找不到请求的网页";
                    break;
                case 408:
                    msg += "请求超时";
                    break;
                case 500:
                    msg += "服务器内部错误";
                    break;
                case 503:
                    msg += "服务不可用";
                    break;
                case 504:
                    msg += "网关超时";
                    break;
                default:
                    msg += "服务器未知异常";
                    break;
            }
        } else {
            msg = "未知异常，请稍后再试...";
        }
       // ToastUtils.showShort(mContext, msg);
    }


    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    protected abstract void _onNext(T t);
}
