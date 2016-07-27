package com.wafa.android.pei.lib.executor;

import com.wafa.android.pei.lib.data.net.base.ServerException;
import rx.Observer;

public abstract class DefaultObserver<T> implements Observer<T> {

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ServerException) {
            onServerError((ServerException) e);
        } else { //网络错误、JSON转换错误或者HTTP错误
            onInternalError(e);
        }
    }

    public void onServerError(ServerException ex) {

    }

    public void onInternalError(Throwable e) {

    }
}
