package com.dream.android.sample.lib.executor;

import com.dream.android.sample.lib.data.net.base.ServerException;

/**
 * Default subscriber base class to be used whenever you want default error handling.
 */
public class DefaultSubscriber<T> extends rx.Subscriber<T> {

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
        } else {
            onInternalError(e);
        }
    }

    public void onServerError(ServerException ex) {

    }

    public void onInternalError(Throwable e) {

    }
}
