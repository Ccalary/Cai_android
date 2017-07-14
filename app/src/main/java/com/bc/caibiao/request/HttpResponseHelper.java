package com.bc.caibiao.request;

import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.model.BaseResponse;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.utils.BCL;
import com.bc.caibiao.utils.ToastUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author wangkai
 * @Description : RxJava统一处理请求结果
 * create at 16/8/15 下午3:15
 */
public class HttpResponseHelper {
    /**
     * 预处理只需要成功结果的
     */
    public static <T> Observable.Transformer<BaseResponse<T>, T> getOnlySuccessData() {

        return new Observable.Transformer<BaseResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResponse<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseResponse<T> result) {
                        if (result.isSuccess()) {
                            return onSuccess(result.getData());
                        } else {
                            return onFail(result.getFailObj());
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 预处理成功失败两种结果
     */
    public static <T> Observable.Transformer<BaseResponse<T>, T> getAllData() {

        return new Observable.Transformer<BaseResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResponse<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseResponse<T>, Observable<T>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public Observable<T> call(BaseResponse<T> result) {
                        if (result.isSuccess()) {
                            return onSuccess(result.getData());
                        } else if (result.isFail()) {
                            return onFail(result.getFailObj());
                        } else {
                            BCL.e("=============");
                            return Observable.error(new ServerException(result.getFailTip()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 创建成功的数据
     */
    private static <T> Observable<T> onSuccess(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    BCL.e("=============" + e);
                    subscriber.onError(e);
                }
            }
        });

    }

    /**
     * 创建失败的数据
     */
    private static Observable onFail(final FieldError fieldError) {
        return onSuccess(fieldError);
    }



    public static <T> Observable.Transformer<T, T> getSingleData() {

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.flatMap(new Func1<T, Observable<T>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public Observable<T> call(T result) {
                        return onSuccess(result);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
