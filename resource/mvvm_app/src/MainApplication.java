package com.wafa.android.pei;

import com.wafa.android.pei.di.component.ApplicationComponent;
import com.wafa.android.pei.di.component.DaggerApplicationComponent;
import com.wafa.android.pei.di.module.ApplicationModule;
import com.wafa.android.pei.lib.BaseApplication;

/**
 * Description:
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class MainApplication extends BaseApplication {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    /**
     * 依赖注入框架的Application生命周期初始化
     */
    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /**
     * @return Application生命周期的DI组件
     */
    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}
