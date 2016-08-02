package com.dream.android.sample.base;

import android.os.Bundle;
import com.dream.android.sample.MainApplication;
import com.dream.android.sample.di.component.ActivityComponent;
import com.dream.android.sample.di.component.ApplicationComponent;
import com.dream.android.sample.di.component.DaggerActivityComponent;
import com.dream.android.sample.di.module.ActivityModule;
import com.dream.android.sample.lib.base.BaseActivity;
import com.dream.android.sample.lib.base.HasComponent;

/**
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public abstract class DIActivity extends BaseActivity implements HasComponent<ActivityComponent> {

    protected ActivityComponent activityComponent;

    protected abstract void injectComponent(ActivityComponent activityComponent);

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
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
}
