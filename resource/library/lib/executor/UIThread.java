package com.dream.android.sample.lib.executor;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * MainThread (UI Thread) implementation based on a {@link Scheduler}
 * which will execute actions on the Android UI thread
 */
@Singleton
public class UIThread {

  @Inject
  public UIThread() {}

  public Scheduler getScheduler() {
    return AndroidSchedulers.mainThread();
  }
}
