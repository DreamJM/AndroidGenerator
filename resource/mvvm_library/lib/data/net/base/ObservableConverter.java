package com.wafa.android.pei.lib.data.net.base;

import com.wafa.android.pei.lib.BaseApplication;
import com.wafa.android.pei.lib.model.ServerResult;
import com.wafa.android.pei.lib.utils.ErrorUtil;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Description: Default Handler of Server Error
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class ObservableConverter<T> implements Func1<ServerResult<T>, Observable<T>> {

    @Override
    public Observable<T> call(ServerResult<T> userServerResult) {
        return Observable.create(new NetObservable(userServerResult));
    }

    class NetObservable implements Observable.OnSubscribe<T> {

        private ServerResult<T> serverResult;

        public NetObservable(ServerResult<T> serverResult) {
            this.serverResult = serverResult;
        }

        @Override
        public void call(Subscriber<? super T> subscriber) {
            try {
                if(ServerResult.SUCCESS != serverResult.getCode()) {
                    subscriber.onError(new ServerException(serverResult.getCode(), ErrorUtil.getServerErrorMsg(BaseApplication.getInstance(), serverResult.getCode(),serverResult.getMsg())));
                }
                subscriber.onNext(serverResult.getResult());
                subscriber.onCompleted();
            } catch (Exception ex) {
                subscriber.onError(ex);
            }
        }
    }
}
