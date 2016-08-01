package com.dream.android.sample.lib.executor;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public abstract class BaseCase {

    protected final JobExecutor threadExecutor;
    protected final UIThread postExecutionThread;

    private Subscription subscription = Subscriptions.empty();

    protected BaseCase(JobExecutor threadExecutor,
                       UIThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    /**
     * Executes the current use case.
     */
    @SuppressWarnings("unchecked")
    protected void execute(Observable observable, Observer observer) {
        unsubscribe();
        this.subscription = observable
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(observer);
    }

    @SuppressWarnings("unchecked")
    protected void execute(Observable observable, Subscriber subscriber) {
        unsubscribe();
        this.subscription = observable
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(subscriber);
    }

    @SuppressWarnings("unchecked")
    protected void execute(Observable[] observables, Observer observer) {
        unsubscribe();
        if(observables.length == 1) {
            execute(observables[0], observer);
        } else if(observables.length > 1) {
            if(observables.length == 2) {
                Observable.mergeDelayError(schedule(observables[0]), schedule(observables[1])).subscribe(observer);
            }
        }
    }

    protected Observable schedule(Observable observable) {
        return observable.subscribeOn(Schedulers.from(threadExecutor)).observeOn(postExecutionThread.getScheduler());
    }

    /**
     * Unsubscribes from current {@link Subscription}.
     */
    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
