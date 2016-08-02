package com.dream.android.sample.base;

import android.os.Bundle;
import com.dream.android.sample.MainApplication;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.di.component.ApplicationComponent;
import com.dream.android.sample.di.component.DaggerActivityComponent;
import com.dream.android.sample.di.module.ActivityModule;
import com.dream.android.sample.lib.base.BaseListActivity;
import com.dream.android.sample.lib.base.CollectionPresenter;
import com.dream.android.sample.lib.base.HasComponent;
import com.dream.android.sample.lib.base.Presenter;
import com.dream.android.sample.lib.widget.pullrefresh.recyclerview.PtrRecyclerViewAdapter;

import javax.inject.Inject;

/**
 * Description:base presenter manager for list style activity(inject presenter automatically)
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public abstract class PresenterListActivity<T extends CollectionPresenter, D, RV extends PtrRecyclerViewAdapter<D>> extends BaseListActivity<D, RV> implements HasComponent<ActivityComponent> {

    @Inject
    protected T presenter;

    protected ActivityComponent activityComponent;

    protected abstract void injectComponent(ActivityComponent activityComponent);
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        injectComponent(getComponent());
    }

    @Override
    public ActivityComponent getComponent() {
        if(activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).build();
        }
        return activityComponent;
    }

    /**
     * Get the Main Application component for dependency injection.
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((MainApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(presenter != null) presenter.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null) presenter.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(presenter != null) presenter.pause();
    }
}
