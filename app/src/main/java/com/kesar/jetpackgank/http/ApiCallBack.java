package com.kesar.jetpackgank.http;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * ApiCallBack
 *
 * @author andy <br/>
 * create time: 2019/2/20 15:22
 */
public abstract class ApiCallBack<T> extends Subscriber<T> {

    public abstract void onSuccess(T data);

    public abstract void onFailure(int code, String msg);

    public abstract void onFinish();


    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.message();
            if (code == 554) {
                msg = "网络不给力";
            } else if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onFailure(code, msg);
        } else {
            onFailure(0, e.toString());
        }
        onFinish();
    }
}
