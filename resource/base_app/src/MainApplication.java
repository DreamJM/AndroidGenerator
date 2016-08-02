package com.dream.android.sample;

import com.dream.android.sample.di.component.ApplicationComponent;
import com.dream.android.sample.di.component.DaggerApplicationComponent;
import com.dream.android.sample.di.module.ApplicationModule;
import com.dream.android.sample.lib.BaseApplication;

/**
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public class MainApplication extends BaseApplication {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}
