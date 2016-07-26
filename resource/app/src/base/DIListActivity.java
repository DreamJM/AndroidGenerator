package com.wafa.android.pei.base;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 * <p>
 * Company:中配联电子商务南京有限公司
 *
 * @author snovajiang
 * @date 16/5/30
 */

import android.os.Bundle;
import com.wafa.android.pei.MainApplication;
import com.wafa.android.pei.di.component.ActivityComponent;
import com.wafa.android.pei.di.component.ApplicationComponent;
import com.wafa.android.pei.di.component.DaggerActivityComponent;
import com.wafa.android.pei.di.module.ActivityModule;
import com.wafa.android.pei.lib.base.BaseListActivity;
import com.wafa.android.pei.lib.base.HasComponent;
import com.wafa.android.pei.lib.widget.pullrefresh.recyclerview.PtrRecyclerViewAdapter;

/**
 * Description:已实现依赖注入的Activity，可自动导入Presenter对象
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public abstract class DIListActivity<D, RV extends PtrRecyclerViewAdapter<D>> extends BaseListActivity<D, RV> implements HasComponent<ActivityComponent> {

    protected ActivityComponent activityComponent;

    protected abstract void injectComponent(ActivityComponent activityComponent);
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        injectComponent(getComponent());
        super.onCreateView(savedInstanceState);
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
